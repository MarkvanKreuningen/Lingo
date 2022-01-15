package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    @Test
    @DisplayName("Word is guessed if all letters are correct")
    void wordIsGuessed() {
        // When
        Feedback feedback = Feedback.correct("woord");

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

    @Test
    @DisplayName("Feedback is invalid if it does not contain enough marks")
    void feedbackIsNotValid() {
        // Then
        assertThrows(
                InvalidFeedbackException.class,
                () -> Feedback.invalid("woord")
        );
    }

    @Test
    @DisplayName("Use Named static constructor for feedback correct")
    void staticNamedConstructorFeedback() {
        // Then
        assertThrows(
                InvalidFeedbackException.class,
                () -> Feedback.invalid("woord")
        );
    }

//    @ParameterizedTest
//    @MethodSource({})
//    @DisplayName("")
//    void

    @Test
    @DisplayName("Hint based on previous hint and marks")
    void giveHint() {
        // When
        Feedback feedback = new Feedback("breed", List.of(Mark.ABSENT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT, Mark.CORRECT));
        List<Character> hint = feedback.giveHint(List.of('W', '.', '.', '.', '.'), "woord");

        // Then
        assertEquals(List.of('W', '.', '.', '.','D'), hint);
    }
}
