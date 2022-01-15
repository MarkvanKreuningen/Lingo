package nl.hu.cisq1.lingo.trainer.domain;


import lombok.EqualsAndHashCode;
import lombok.ToString;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode
@ToString
public class Feedback {
    private String attempt;
    private List<Mark> marks;

    public static void correct(String attempt) {
        Mark[] emptyMarks = new Mark[attempt.length()];
        Arrays.fill(emptyMarks, Mark.CORRECT);
        List<Mark> marks = List.of(emptyMarks);
        new Feedback(attempt, marks);
    }

    public Feedback(String attempt, List<Mark> marks) {
        if (attempt.length() != marks.size()) {
            throw new InvalidFeedbackException(attempt, marks);
        }
        this.attempt = attempt;
        this.marks = marks;
    }

    public static void invalid(String attempt) {
        throw new InvalidFeedbackException(attempt);
    }

    public boolean isWordGuessed() {
        return this.marks.stream().allMatch(m -> m == Mark.CORRECT);
    }

    public boolean isGuessInvalid() {
        return this.marks.stream().anyMatch(m -> m == Mark.INVALID);
    }
}
