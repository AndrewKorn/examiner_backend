package examiner.examiner_backend.repositories;

import examiner.examiner_backend.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectsRepository extends JpaRepository<Subject, Long> {

    List<Subject> findAllByFacultyIdAndCourseAndSemester(Long facultyId, int course, int semester);

}
