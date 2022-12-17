package examiner.examiner_backend.controllers;

import examiner.examiner_backend.models.Subject;
import examiner.examiner_backend.models.Deck;
import examiner.examiner_backend.models.Faculty;
import examiner.examiner_backend.models.University;
import examiner.examiner_backend.services.DecksService;
import examiner.examiner_backend.services.FacultyService;
import examiner.examiner_backend.services.SubjectsService;
import examiner.examiner_backend.services.UniversitiesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class DecksController {

    private final UniversitiesService universitiesService;
    private final FacultyService facultyService;

    private final SubjectsService subjectsService;
    private final DecksService decksService;

    public DecksController(UniversitiesService universitiesService, FacultyService facultyService, SubjectsService subjectsService, DecksService decksService) {
        this.universitiesService = universitiesService;
        this.facultyService = facultyService;
        this.subjectsService = subjectsService;
        this.decksService = decksService;
    }

    @GetMapping("/{universityShortName}/{facultyShortName}/{course}/{semester}/{subjectShortName}/decks")
    public ResponseEntity<List<Deck>> getDecks(
            @PathVariable String universityShortName,
            @PathVariable String facultyShortName,
            @PathVariable int course,
            @PathVariable int semester,
            @PathVariable String subjectShortName
    )  {
        try {
            University university = universitiesService.findByShortName(universityShortName).getBody();
            if (university != null) {
                Faculty faculty = facultyService.findByUniversityIdAndShortName(university.getId(), facultyShortName).getBody();
                if (faculty != null) {
                    Long facultyId = faculty.getId();
                    Subject subject = subjectsService
                            .findByFacultyIdAndCourseAndSemesterAndShortName(facultyId, course, semester, subjectShortName)
                            .getBody();
                    if (subject != null) {
                        return decksService.findAllBySubjectId(subject.getId());
                    }
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
