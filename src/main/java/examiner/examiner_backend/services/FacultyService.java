package examiner.examiner_backend.services;

import examiner.examiner_backend.models.Faculty;
import examiner.examiner_backend.models.University;
import examiner.examiner_backend.repositories.FacultiesRepository;
import examiner.examiner_backend.repositories.UniversitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {

    private final FacultiesRepository facultiesRepository;
    @Autowired
    public FacultyService(FacultiesRepository facultiesRepository) {
        this.facultiesRepository = facultiesRepository;
    }

    public ResponseEntity<Faculty> findByUniversityIdAndShortName(Long universityId, String shortName) {
        try {
            Optional<Faculty> optionalFaculty = facultiesRepository.findByUniversityIdAndShortName(universityId, shortName);
            return optionalFaculty.map(faculty -> new ResponseEntity<>(faculty, HttpStatus.OK)).
                    orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Faculty>> findAllByUniversityId(Long universityId) {
        try {
            List<Faculty> faculties = facultiesRepository.findAllByUniversityId(universityId);
            if (faculties.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(faculties, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
