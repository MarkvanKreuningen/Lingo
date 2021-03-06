package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ToString
@Getter
@Setter
@Entity
public class Round {
    @Id
    @GeneratedValue
    private Long id;

    private String wordToGuess;

    @ElementCollection
    @CollectionTable
    private List<String> attempts;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private List<Feedback> feedbacks;


    public Round(String wordToGuess, List<String> attempts, List<Feedback> feedbacks) {
        this.wordToGuess = wordToGuess;
        this.attempts = attempts;
        this.feedbacks = feedbacks;
    }

    public Round() {

    }

    public boolean guess(String attempt) throws InvalidGuessException, InvalidFeedbackException {
        if (this.getAttempts().size() >= 5) {
            throw new InvalidGuessException();
        }
        if (attempt.length() != this.getCurrentWordLength() ) {
            this.saveAttempt(attempt);
            Feedback.invalid(attempt);
            return false;
        } else {
            Feedback feedback = this.createFeedback(attempt);
            this.saveAttempt(attempt);
            this.saveFeedback(feedback);
            return feedback.isWordGuessed();
        }
    }

    public void saveAttempt(String attempt) {
        List<String> attempsList = new ArrayList<>(this.attempts);
        attempsList.add(attempt);
        this.setAttempts(attempsList);
    }

    public void saveFeedback(Feedback feedback) {
        List<Feedback> feedbackList = new ArrayList<>(this.feedbacks);
        feedbackList.add(feedback);
        this.setFeedbacks(feedbackList);
    }

    public Feedback createFeedback(String attempt) {
        Mark[] marks = new Mark[attempt.length()];
        Arrays.fill(marks, Mark.ABSENT);
        char[] wordToGuessChar = this.wordToGuess.toCharArray();
        char[] attemptChar = attempt.toCharArray();

        if (attempt.length() > wordToGuess.length()) {
            Arrays.fill(marks, Mark.INVALID);
        }

        for (int i = 0; i < wordToGuess.length(); i++) {
            if (attemptChar[i] == wordToGuessChar[i]) {
                marks[i] = Mark.CORRECT;
                wordToGuessChar[i] = '!';
            }
        }

        for (int i = 0; i < wordToGuess.length(); i++) {
            for (int x = 0; x < wordToGuess.length(); x++) {
                if (attemptChar[i] == wordToGuessChar[x] && marks[i] == Mark.ABSENT) {
                    marks[i] = Mark.PRESENT;
                    wordToGuessChar[x] = '!';
                }
            }
        }
        return new Feedback(attempt, List.of(marks));
    }

    public String giveHint() {
        char[] word = this.wordToGuess.toUpperCase().toCharArray();
        StringBuilder sb = new StringBuilder(this.getCurrentWordLength());
        sb.append(word[0]);
        sb.append(".".repeat(Math.max(0, this.getCurrentWordLength() - 1)));
        String previousHint = sb.toString();

        for (Feedback feedback : this.feedbacks) {
            previousHint = feedback.giveHint(previousHint, this.wordToGuess);
        }

        return previousHint;
    }

    public Integer getCurrentWordLength() {
        return this.wordToGuess.length();
    }
}
