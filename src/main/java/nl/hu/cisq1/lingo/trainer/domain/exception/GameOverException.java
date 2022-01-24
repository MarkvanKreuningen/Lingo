package nl.hu.cisq1.lingo.trainer.domain.exception;

public class GameOverException extends Exception {
    public GameOverException() {
        super("Game is already over!");
    }
}
