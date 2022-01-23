package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

public class Progress {
    private Integer score;
    private List<String> hints;
    private Integer roundNumber;

    public Progress(Integer score, List<String> hints, Integer roundNumber) {
        this.score = score;
        this.hints = hints;
        this.roundNumber = roundNumber;
    }

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }
}
