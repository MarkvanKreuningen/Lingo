package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {
    @Test
    @DisplayName("Provide next word length")
    void provideNextWordLength() {
        // Given
        Progress progress = new Progress(5, null, 2);
        Round round = new Round("BAARD", null, null);
        Game game = new Game(5, GameStatus.WAITING_FOR_ROUND, List.of(round), progress);

        // When
        Integer nextRoundLength = game.provideNextWordLength();

        // Then
        assertEquals(6, nextRoundLength);
    }
}
