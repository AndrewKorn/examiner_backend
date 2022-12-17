package examiner.examiner_backend.controllers;

import examiner.examiner_backend.models.Subject;
import examiner.examiner_backend.models.Faculty;
import examiner.examiner_backend.models.University;
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
public class SubjectsController {
    private final UniversitiesService universitiesService;
    private final FacultyService facultyService;
    private final SubjectsService subjectsService;

    public SubjectsController(UniversitiesService universitiesService, FacultyService facultyService, SubjectsService subjectsService) {
        this.universitiesService = universitiesService;
        this.facultyService = facultyService;
        this.subjectsService = subjectsService;
    }

    @GetMapping("/{universityShortName}/{facultyShortName}/{course}/{semester}/subjects")
    public ResponseEntity<List<Subject>> getSubjects(
            @PathVariable String universityShortName,
            @PathVariable String facultyShortName,
            @PathVariable int course,
            @PathVariable int semester
    ) {
        University university = universitiesService.findByShortName(universityShortName).getBody();
        if (university != null) {
            Faculty faculty = facultyService.findByUniversityIdAndShortName(university.getId(), facultyShortName).getBody();
            if (faculty != null) {
                return subjectsService.findAllByFacultyIdAndCourseAndSemester(faculty.getId(), course, semester);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
