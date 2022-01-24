package nl.hu.cisq1.lingo.trainer.application;

import lombok.RequiredArgsConstructor;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameOverException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;
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
        Round round = game.startNewRound(randomWord);
        game.setRounds(List.of(round));
        gameRepository.save(game);
        return game.showProgress();
    }

    public Progress guess(Game game, String guess) throws GameOverException, InvalidGuessException, InvalidFeedbackException, GameNotFoundException {
        boolean isWordGuessed = game.guess(guess);
        if (isWordGuessed) {
            game.wordIsGuessed();
            return startNextRound(game);
        }
        gameRepository.save(game);
        return game.showProgress();
    }

    public Progress startNextRound(Game game) {
        System.out.println(game);
        Integer nextLength = game.provideNextWordLength();
        String wordToGuess = this.wordService.provideRandomWord(nextLength);
        Round round = game.startNewRound(wordToGuess);
        List<Round> rounds = game.getRounds();
        rounds.add(round);
        game.setRounds(rounds);
        return game.showProgress();
    }

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Game findById(Long id) throws GameNotFoundException {
        Optional<Game> optionalGame = gameRepository.findById(id);
        return optionalGame.orElseThrow(() -> new GameNotFoundException(id));
    }
}
