package examiner.examiner_backend.controllers;

import examiner.examiner_backend.models.*;
import examiner.examiner_backend.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CardsController {
    private final UniversitiesService universitiesService;
    private final FacultyService facultyService;
    private final SubjectsService subjectsService;
    private final DecksService decksService;
    private final CardsService cardsService;

    public CardsController(UniversitiesService universitiesService, FacultyService facultyService, SubjectsService subjectsService, DecksService decksService, CardsService cardsService) {
        this.universitiesService = universitiesService;
        this.facultyService = facultyService;
        this.subjectsService = subjectsService;
        this.decksService = decksService;
        this.cardsService = cardsService;
    }

    @GetMapping("/{universityShortName}/{facultyShortName}/{course}/{semester}/{subjectShortName}/{deckId}/cards")
    public ResponseEntity<List<Card>> getDecks(
            @PathVariable String universityShortName,
            @PathVariable String facultyShortName,
            @PathVariable int course,
            @PathVariable int semester,
            @PathVariable String subjectShortName,
            @PathVariable Long deckId
    ) {
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
                        List<Deck> decks = decksService.findAllBySubjectId(subject.getId()).getBody();
                        if (decks != null && decks.stream().anyMatch(d -> d.getId().equals(deckId))) {
                            return cardsService.findAllByDeckId(deckId);
                        }
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
