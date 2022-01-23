package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

public class Game {
    private Integer score;
    private GameStatus status;
    private List<Round> rounds;
    private Progress progress;


    public void startNewRound(String wordToGuess) {

    }

    public void guess(String word) {

    }

    public Progress showProgress() {
        return null;
    }

    public boolean isPlayerEliminated() {
        return false;
    }

    public boolean isPlaying() {
        return false;
    }

    public Integer provideNextWordLength() {
        return 1;
    }

}
