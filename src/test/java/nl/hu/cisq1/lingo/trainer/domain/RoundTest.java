package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    @Test
    @DisplayName("Guess is valid")
    void guessIsValid() throws InvalidGuessException, InvalidFeedbackException {
        // Given
        Round round = new Round("baard", List.of(), List.of());

        // When
        round.guess("baard");

        // Then
        assertEquals(1, round.getFeedbacks().size());
    }

    @Test
    @DisplayName("Guess is invalid if there are already 5 attempts")
    void roundGuessIsInvalid() {
        // When
        Round round = new Round("baard", List.of("breed","breed","breed","breed","breed"), List.of());

        // Then
        assertThrows(
                InvalidGuessException.class,
                () -> round.guess("baard")
        );
    }

    @Test
    @DisplayName("Guess is invalid if any of the letters is invalid")
    void feedbackGuessIsNotInvalid() {
        // When
        Round round = new Round("baard", List.of("breed","breed","breed","breed"), List.of());

        // Then
        assertThrows(
                InvalidFeedbackException.class,
                () -> round.guess("baaard")
        );
    }

    @Test
    @DisplayName("Guess is valid and there is asked for hint")
    void giveHintAfterGuess() throws InvalidGuessException, InvalidFeedbackException {
        // Given
        Round round = new Round("baard", List.of(), List.of());

        // When
        round.guess("breed");

        // Then
        assertEquals(1, round.getFeedbacks().size());
        assertEquals("B...D", round.giveHint());
    }
}
