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
//        Feedback lastFeedback = this.feedbacks.get(this.feedbacks.size() - 1);
//        Character[] characters = new Character[this.wordToGuess.length()];
//        String returena = lastFeedback.giveHint(lastFeedbac, this.wordToGuess);
        return "";
    }

    public List<Feedback> getFeedbackHistory() {
        return this.feedbacks;
    }

    public int getAttempts() {
        return attempts.size();
    }

    public int getCurrentWordLength() {
        return this.wordToGuess.length();
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
