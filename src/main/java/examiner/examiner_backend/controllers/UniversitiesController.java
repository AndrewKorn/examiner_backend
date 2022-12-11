package examiner.examiner_backend.controllers;

import examiner.examiner_backend.models.University;
import examiner.examiner_backend.services.UniversitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
public class UniversitiesController {

    private final UniversitiesService universitiesService;

    @Autowired
    public UniversitiesController(UniversitiesService universitiesService) {
        this.universitiesService = universitiesService;
    }

    @GetMapping("/universities")
    public ResponseEntity<List<University>> getUniversities() {
        try {
            return universitiesService.findAll();
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
