package nl.hu.cisq1.lingo.trainer.domain;


import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode
@ToString
public class Feedback {
    private String attempt;
    private List<Mark> marks;

    public Feedback(String attempt, List<Mark> marks) {
        this.attempt = attempt;
        this.marks = marks;
    }

    public boolean isWordGuessed() {
        return this.marks.stream().allMatch(m -> m == Mark.CORRECT);
    }

    public boolean isGuessInvalid() {
        return this.marks.stream().anyMatch(m -> m == Mark.INVALID);
    }
}
