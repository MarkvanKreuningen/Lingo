package nl.hu.cisq1.lingo.trainer.domain.exception;

public class InvalidFeedbackException extends RuntimeException {
    public InvalidFeedbackException(String attempt) {
        super("Feedback is not valid for attempt: " + attempt);
    }
}
