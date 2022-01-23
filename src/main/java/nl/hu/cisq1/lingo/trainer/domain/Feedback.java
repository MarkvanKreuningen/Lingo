package nl.hu.cisq1.lingo.trainer.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@Setter
@Entity
public class Feedback {
    @Id
    @GeneratedValue
    private Long id;

    private String attempt;

    @Enumerated
    @ElementCollection(targetClass = Mark.class)
    private List<Mark> marks;

    public Feedback(String attempt, List<Mark> marks) {
        this.attempt = attempt;
        this.marks = marks;
    }

    public Feedback() {

    }

    public static Feedback correct(String attempt) {
        Mark[] emptyMarks = new Mark[attempt.length()];
        Arrays.fill(emptyMarks, Mark.CORRECT);
        List<Mark> marks = List.of(emptyMarks);
        return new Feedback(attempt, marks);
    }

    public String giveHint(String previousHint, String wordToGuess) {
        List<Character> hint = new ArrayList<>(previousHint.chars()
                .mapToObj(e->(char)e).collect(Collectors.toList()));

        char[] word = wordToGuess.toUpperCase().toCharArray();
        char[] attempt = this.attempt.toUpperCase().toCharArray();

        for (int i = 1; i < hint.size(); i++) {
            if (word[i] == attempt[i]) {
                hint.set(i, word[i]);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Character ch: hint) {
            sb.append(ch);
        }
        return sb.toString();
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
