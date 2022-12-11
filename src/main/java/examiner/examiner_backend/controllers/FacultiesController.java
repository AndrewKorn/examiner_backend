package examiner.examiner_backend.controllers;

import examiner.examiner_backend.models.Faculty;
import examiner.examiner_backend.models.University;
import examiner.examiner_backend.services.FacultyService;
import examiner.examiner_backend.services.UniversitiesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FacultiesController {

    private final UniversitiesService universitiesService;
    private final FacultyService facultyService;

    public FacultiesController(UniversitiesService universitiesService, FacultyService facultyService) {
        this.universitiesService = universitiesService;
        this.facultyService = facultyService;
    }

    @GetMapping("/{universityShortName}/faculties")
    public ResponseEntity<List<Faculty>> getFacultiesByUniversityName(@PathVariable String universityShortName) {
        try {
            University university = universitiesService.findByShortName(universityShortName).getBody();
            if (university == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return facultyService.findAllByUniversityId(university.getId());
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
