package examiner.examiner_backend.controllers;

import examiner.examiner_backend.models.*;
import examiner.examiner_backend.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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

    @GetMapping("/{universityName}/{facultyName}/{course}/{semester}/{subjectId}/{deckId}/cards")
    public ResponseEntity<List<Card>> getDecks(
            @PathVariable String universityName,
            @PathVariable String facultyName,
            @PathVariable int course,
            @PathVariable int semester,
            @PathVariable Long subjectId,
            @PathVariable Long deckId
    ) {
        try {
            University university = universitiesService.findByName(universityName).getBody();
            if (university != null) {
                Faculty faculty = facultyService.findByUniversityIdAndName(university.getId(), facultyName).getBody();
                if (faculty != null) {
                    List<Subject> subjects = subjectsService
                            .findAllByFacultyIdAndCourseAndSemester(faculty.getId(), course, semester)
                            .getBody();
                    if (subjects != null && subjects.stream().anyMatch(s -> s.getId().equals(subjectId))) {
                        List<Deck> decks = decksService.findAllBySubjectId(subjectId).getBody();
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
