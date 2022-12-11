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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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

    @GetMapping("/{universityName}/{facultyName}/{course}/{semester}/{subjectId}/decks")
    public ResponseEntity<List<Deck>> getDecks(
            @PathVariable String universityName,
            @PathVariable String facultyName,
            @PathVariable int course,
            @PathVariable int semester,
            @PathVariable Long subjectId
    )  {
        try {
            University university = universitiesService.findByName(universityName).getBody();
            if (university != null) {
                Faculty faculty = facultyService.findByUniversityIdAndName(university.getId(), facultyName).getBody();
                if (faculty != null) {
                    List<Subject> subjects = subjectsService
                            .findAllByFacultyIdAndCourseAndSemester(faculty.getId(), course, semester)
                            .getBody();
                    if (subjects != null && subjects.stream().anyMatch(s -> s.getId().equals(subjectId))) {
                        return decksService.findAllBySubjectId(subjectId);
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
