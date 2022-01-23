package nl.hu.cisq1.lingo.trainer.domain.exception;

public class InvalidGuessException extends Exception {
    public InvalidGuessException() {
        super("Round is over!");
    }
}
