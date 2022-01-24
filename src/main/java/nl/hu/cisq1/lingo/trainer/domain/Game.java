package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameOverException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@Entity
public class Game {
    @Id
    @GeneratedValue
    private Long id;

    private Integer score = 0;

    @Enumerated(EnumType.STRING)
    private GameStatus status = GameStatus.WAITING_FOR_ROUND;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private List<Round> rounds;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Progress progress;

    public Game(Integer score, GameStatus status, List<Round> rounds, Progress progress) {
        this.score = score;
        this.status = status;
        this.rounds = rounds;
        this.progress = progress;
    }

    public Game() {

    }

    public static Game startNewGame() {
        Progress progress = new Progress(0, null, 0, GameStatus.PLAYING);
        return new Game(0, GameStatus.WAITING_FOR_ROUND, null, progress);
    }

    public Round startNewRound(String wordToGuess) {
        Round round = new Round(wordToGuess.toUpperCase(), null, null);
        StringBuilder sb = new StringBuilder();
        for (char c : wordToGuess.toCharArray()) {
            sb.append(".");
        }
        sb.setCharAt(0, wordToGuess.toUpperCase().charAt(0));

        this.progress.setHints(List.of(sb.toString()));
        this.progress.setRoundNumber(this.progress.getRoundNumber() + 1);
        return round;
    }

    public boolean guess(String word) throws GameOverException, InvalidGuessException, InvalidFeedbackException {
        if (this.isPlayerEliminated()) {
            throw new GameOverException();
        }
        this.status = GameStatus.PLAYING;
        Round round = this.rounds.get(this.rounds.size() - 1);
        boolean isWordGuessed = round.guess(word.toUpperCase());
        List<String> hints = new ArrayList<>(this.progress.getHints());
        hints.add(round.giveHint());
        this.progress.setHints(hints);
        return isWordGuessed;
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

    public void wordIsGuessed() {
        Round round = this.rounds.get(this.rounds.size() - 1);
        Integer score = this.progress.getScore();
        score += (5 - round.getAttempts().size()) * 5 + 5;
        this.progress.setScore(score);
        this.setScore(score);
    }
}
