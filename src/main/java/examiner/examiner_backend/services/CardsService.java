package examiner.examiner_backend.services;

import examiner.examiner_backend.models.Card;
import examiner.examiner_backend.repositories.CardsRepository;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardsService {
    private final CardsRepository cardsRepository;

    public CardsService(CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;
    }

    public ResponseEntity<List<Card>> findAllByDeckId(Long deckId) {
        try {
            List<Card> cards = cardsRepository.findAllByDeckId(deckId);
            if (cards.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cards, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
