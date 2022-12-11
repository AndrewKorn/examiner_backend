package examiner.examiner_backend.services;

import examiner.examiner_backend.models.Deck;
import examiner.examiner_backend.repositories.DecksRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DecksService {
    private final DecksRepository decksRepository;

    public DecksService(DecksRepository decksRepository) {
        this.decksRepository = decksRepository;
    }

    public ResponseEntity<List<Deck>> findAllBySubjectId(Long subjectId) {
        try {
            List<Deck> decks = decksRepository.findAllBySubjectId(subjectId);
            if (decks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(decks, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
