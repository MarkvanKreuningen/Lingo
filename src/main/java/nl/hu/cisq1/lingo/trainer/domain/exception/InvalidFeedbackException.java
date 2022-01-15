package nl.hu.cisq1.lingo.trainer.domain.exception;

import nl.hu.cisq1.lingo.trainer.domain.Mark;

import java.util.List;

public class InvalidFeedbackException extends RuntimeException {
    public InvalidFeedbackException(String attempt, List<Mark> marks) {
        super("Feedback does not contain enough marks (" + marks + ") for attempt: " + attempt);
    }

    public InvalidFeedbackException(String attempt) {
        super("Feedback is not valid for attempt: " + attempt);
    }
}
