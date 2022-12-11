package examiner.examiner_backend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "faculties")
@Getter
@Setter
@NoArgsConstructor
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faculty_id")
    private Long id;

    @Column(name = "faculty_full_name")
    private String fullName;
    @Column(name = "faculty_short_name")
    private String shortName;

    @Column(name = "university_id")
    private Long universityId;
}
