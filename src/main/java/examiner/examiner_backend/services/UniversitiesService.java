package examiner.examiner_backend.services;

import examiner.examiner_backend.models.University;
import examiner.examiner_backend.repositories.UniversitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniversitiesService {

    private final UniversitiesRepository universitiesRepository;
    @Autowired
    public UniversitiesService(UniversitiesRepository universitiesRepository) {
        this.universitiesRepository = universitiesRepository;
    }

    public ResponseEntity<List<University>> findAll() {
        try {
            List<University> universityList = universitiesRepository.findAll();
            if (universityList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(universityList, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<University> findByShortName(String shortName) {
        try {
            Optional<University> optionalUniversity = universitiesRepository.findByShortName(shortName);
            return optionalUniversity.map(university -> new ResponseEntity<>(university, HttpStatus.OK)).
                    orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
