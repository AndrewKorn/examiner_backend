package examiner.examiner_backend.services;

import examiner.examiner_backend.models.Subject;
import examiner.examiner_backend.repositories.SubjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectsService {

    private final SubjectsRepository subjectsRepository;
    @Autowired
    public SubjectsService(SubjectsRepository subjectsRepository) {
        this.subjectsRepository = subjectsRepository;
    }

    public ResponseEntity<List<Subject>> findAllByFacultyIdAndCourseAndSemester(
            Long facultyId,
            int course,
            int semester
    ) {
        try {
            List<Subject> subjects = subjectsRepository.findAllByFacultyIdAndCourseAndSemester(facultyId, course, semester);
            if (subjects.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(subjects, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
