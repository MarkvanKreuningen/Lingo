package nl.hu.cisq1.lingo.trainer.application;

import lombok.RequiredArgsConstructor;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameOverException;
import nl.hu.cisq1.lingo.trainer.presentation.dto.GuessDto;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainerService {
    private final SpringGameRepository gameRepository;
    private final WordService wordService;

    public Progress startNewGame() {
        String randomWord = wordService.provideRandomWord(5);
        Game game = Game.startNewGame();
        game.startNewRound(randomWord);
        gameRepository.save(game);
        return game.showProgress();
    }

    public Progress guess(Game game, GuessDto guess) {
        this.legitAttempt(game);

        game.guess(guess.getGuess());
        gameRepository.save(game);
        return game.showProgress();
    }

    private void legitAttempt(Game game) {
        if (game.isPlayerEliminated()) {
            game.setStatus(GameStatus.ELIMINATED);
            throw new GameOverException();
        }
    }

    public void startNewRound() {

    }

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Game findById(Long id) throws GameNotFoundException {
        Optional<Game> optionalGame = gameRepository.findById(id);
        return optionalGame.orElseThrow(() -> new GameNotFoundException(id));
    }
}
