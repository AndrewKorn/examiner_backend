package examiner.examiner_backend.repositories;

import examiner.examiner_backend.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectsRepository extends JpaRepository<Subject, Long> {

    List<Subject> findAllByFacultyIdAndCourseAndSemester(Long facultyId, int course, int semester);

    Optional<Subject> findByFacultyIdAndCourseAndSemesterAndShortName(Long facultyId, int course, int semester, String shortName);

}
