package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    @Test
    @DisplayName("Word is guessed if all letters are correct")
    void wordIsGuessed() {
        // When
        Feedback feedback = new Feedback("woord", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT));

        // Then
        assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("Word is not guessed if one letters is wrong")
    void wordIsNotGuessed() {
        // When
        Feedback feedback = new Feedback("boord", List.of(Mark.ABSENT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT));

        // Then
        assertFalse(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("Guess is invalid if any of the letters is invalid")
    void guessIsInvalid() {
        // When
        Feedback feedback = new Feedback("woords", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.INVALID));

        // Then
        assertTrue(feedback.isGuessInvalid());
    }

    @Test
    @DisplayName("Guess is not invalid if all the letters are valid")
    void guessIsNotInvalid() {
        // When
        Feedback feedback = new Feedback("breed", List.of(Mark.ABSENT, Mark.PRESENT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT));

        // Then
        assertFalse(feedback.isGuessInvalid());
    }
}
