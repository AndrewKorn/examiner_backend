package examiner.examiner_backend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_actions")
@Getter
@Setter
@NoArgsConstructor
public class UserAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_action_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "last_time")
    private LocalDateTime lastTime;
    private int repetitions;
    private int interval;
    private int easiness;
}
