package nl.hu.cisq1.lingo.trainer.domain;

import java.util.Collections;
import java.util.List;

public class Game {
    private Integer score;
    private GameStatus status;
    private List<Round> rounds;
    private Progress progress;

    public Game(Integer score, GameStatus status, List<Round> rounds, Progress progress) {
        this.score = score;
        this.status = status;
        this.rounds = rounds;
        this.progress = progress;
    }

    public void startNewRound(String wordToGuess) {
        new Round(wordToGuess, null, null);
        this.progress.setRoundNumber(this.progress.getRoundNumber() + 1);
    }

    public void guess(String word) {
        Round round = this.rounds.get(this.rounds.size() - 1);
        round.guess(word);
    }

    public Progress showProgress() {
        return this.progress;
    }

    public boolean isPlayerEliminated() {
        return this.status == GameStatus.ELIMINATED;
    }

    public boolean isPlaying() {
        return this.status == GameStatus.PLAYING;
    }

    public Integer provideNextWordLength() {

        Round round = this.rounds.get(this.rounds.size() - 1);
        switch (round.getCurrentWordLength()) {
            case 5:
                return 6;
            case 6:
                return 7;
            default:
                return 5;
        }
    }

}
