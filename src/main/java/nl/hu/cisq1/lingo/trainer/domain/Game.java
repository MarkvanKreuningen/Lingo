package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameOverException;

import javax.persistence.*;
import java.util.List;

@ToString
@Getter
@Setter
@Entity
public class Game {
    @Id
    @GeneratedValue
    private Long id;

    private Integer score;
    private GameStatus status;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
        Progress progress = new Progress(0, null, 0);
        return new Game(0, GameStatus.WAITING_FOR_ROUND, null, progress);
    }

    public void startNewRound(String wordToGuess) {
        Round round = new Round(wordToGuess.toUpperCase(), null, null);
        this.setRounds(List.of(round));
        String hint = wordToGuess.toUpperCase().charAt(0) + "....";
        this.progress.setHints(List.of(hint));
        this.progress.setRoundNumber(this.progress.getRoundNumber() + 1);
        this.status = GameStatus.PLAYING;
    }

    public void guess(String word) {
        if (this.status == GameStatus.ELIMINATED) {
            throw new GameOverException();
        }
        Round round = this.rounds.get(this.rounds.size() - 1);
        round.guess(word.toUpperCase());
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
