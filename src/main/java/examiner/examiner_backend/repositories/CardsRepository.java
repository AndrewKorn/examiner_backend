package examiner.examiner_backend.repositories;

import examiner.examiner_backend.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardsRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByDeckId(Long deckId);
}
