package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Round {
    private String wordToGuess;
    private List<String> attempts;
    private List<Feedback> feedbacks;


    public Round(String wordToGuess, List<String> attempts, List<Feedback> feedbacks) {
        this.wordToGuess = wordToGuess;
        this.attempts = attempts;
        this.feedbacks = feedbacks;
    }

    public void guess(String attempt) {
        if (this.getAttempts() >= 5) {
            throw new InvalidGuessException();
        }

        Feedback feedback = this.createFeedback(attempt);
        if (attempt.length() > this.getCurrentWordLength()) {
            Feedback.invalid(attempt);
        }
        List<Feedback> feedbackList = new ArrayList<>(this.feedbacks);
        feedbackList.add(feedback);
        this.setFeedbacks(feedbackList);
    }

    public Feedback createFeedback(String attempt) {
        Mark[] marks = new Mark[attempt.length()];
        Arrays.fill(marks, Mark.ABSENT);
        char[] wordToGuessChar = this.wordToGuess.toCharArray();
        char[] attemptChar = attempt.toCharArray();

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

    public List<Feedback> getFeedbackHistory() {
        return this.feedbacks;
    }

    public Integer getAttempts() {
        return attempts.size();
    }

    public Integer getCurrentWordLength() {
        return this.wordToGuess.length();
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
