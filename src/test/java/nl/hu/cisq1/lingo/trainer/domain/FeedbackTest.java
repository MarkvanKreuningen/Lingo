package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource({"provideHintExamples"})
    @DisplayName("Hint based on previous hint and marks")
    void giveHints(String attempt, List<Character> previousHint, String wordToGuess, List<Character> hintGettingBack) {
        // Given
        Feedback feedback = new Feedback(attempt, List.of(Mark.ABSENT));

        // When
        List<Character> hint = feedback.giveHint(previousHint, wordToGuess);

        // Then
        assertEquals(hintGettingBack, hint);
    }

    public static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of("BERGEN", List.of('B', '.', '.', '.', '.'), "BAARD", List.of('B', '.', '.', '.', '.')),
                Arguments.of("BONRE", List.of('B', '.', '.', '.', '.'), "BAARD", List.of('B', '.', '.', 'R', '.')),
                Arguments.of("BARST", List.of('B', '.', '.', 'R', '.'), "BAARD", List.of('B', 'A', '.', 'R', '.')),
                Arguments.of("BEDDE", List.of('B', 'A', '.', 'R', '.'), "BAARD", List.of('B', 'A', '.', 'R', '.')),
                Arguments.of("BAARD", List.of('B', 'A', '.', 'R', '.'), "BAARD", List.of('B', 'A', 'A', 'R', 'D'))
        );
    }
}
