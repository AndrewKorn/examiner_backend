package examiner.examiner_backend.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="universities")
@Getter
@Setter
@NoArgsConstructor
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id")
    private Long id;

    @Column(name = "university_full_name")
    private String fullName;

    @Column(name = "university_short_name")
    private String shortName;

}
