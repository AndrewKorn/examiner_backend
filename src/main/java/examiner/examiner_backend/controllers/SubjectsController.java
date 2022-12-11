package examiner.examiner_backend.controllers;

import examiner.examiner_backend.models.Subject;
import examiner.examiner_backend.models.Faculty;
import examiner.examiner_backend.models.University;
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
public class SubjectsController {

    private final UniversitiesService universitiesService;
    private final FacultyService facultyService;
    private final SubjectsService subjectsService;

    public SubjectsController(UniversitiesService universitiesService, FacultyService facultyService, SubjectsService subjectsService) {
        this.universitiesService = universitiesService;
        this.facultyService = facultyService;
        this.subjectsService = subjectsService;
    }

    @GetMapping("/{universityName}/{facultyName}/{course}/{semester}/subjects")
    public ResponseEntity<List<Subject>> getSubjects(
            @PathVariable String universityName,
            @PathVariable String facultyName,
            @PathVariable int course,
            @PathVariable int semester
    ) {
        University university = universitiesService.findByName(universityName).getBody();
        if (university != null) {
            Faculty faculty = facultyService.findByUniversityIdAndName(university.getId(), facultyName).getBody();
            if (faculty != null) {
                return subjectsService.findAllByFacultyIdAndCourseAndSemester(faculty.getId(), course, semester);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
