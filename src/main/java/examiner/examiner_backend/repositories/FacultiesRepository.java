package examiner.examiner_backend.repositories;


import examiner.examiner_backend.models.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FacultiesRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findAllByUniversityId(Long universityId);
    Optional<Faculty> findByUniversityIdAndName(Long universityId, String name);
}
