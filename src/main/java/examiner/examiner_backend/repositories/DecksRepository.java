package examiner.examiner_backend.repositories;

import examiner.examiner_backend.models.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DecksRepository extends JpaRepository<Deck, Long> {

    List<Deck> findAllBySubjectId(Long subjectID);


}
