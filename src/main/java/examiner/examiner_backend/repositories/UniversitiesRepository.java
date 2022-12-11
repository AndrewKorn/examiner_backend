package examiner.examiner_backend.repositories;

import examiner.examiner_backend.models.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UniversitiesRepository extends JpaRepository<University, Long> {
    Optional<University> findByShortName(String shortName);
}
