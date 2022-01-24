package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ToString
@Getter
@Setter
@Entity
public class Progress {
    @Id
    @GeneratedValue
    private Long id;
    private Integer score;

    @ElementCollection
    @CollectionTable
    private List<String> hints;
    private Integer roundNumber;

    @Enumerated(EnumType.STRING)
    private GameStatus status = GameStatus.WAITING_FOR_ROUND;

    public Progress(Integer score, List<String> hints, Integer roundNumber, GameStatus status) {
        this.score = score;
        this.hints = hints;
        this.roundNumber = roundNumber;
        this.status = status;
    }

    public Progress() {

    }

    public String getCurrentHint() {
        return hints.get(hints.size() - 1);
    }
}
