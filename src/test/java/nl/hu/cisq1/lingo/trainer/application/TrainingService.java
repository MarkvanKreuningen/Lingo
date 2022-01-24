package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameOverException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class TrainingService {

    @Test
    @DisplayName("starting a new game starts a new round")
    void startNewGame() {
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5))
                .thenReturn("baard");
        SpringGameRepository repository = mock(SpringGameRepository.class);
        TrainerService trainerService = new TrainerService(repository, wordService);
        Progress progress = trainerService.startNewGame();

        assertEquals("B....", progress.getCurrentHint());
        assertEquals(GameStatus.PLAYING, progress.getStatus());
    }

    @Test
    @DisplayName("starting a new round")
    void startNewRound() throws GameOverException, InvalidFeedbackException, InvalidGuessException {
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(6))
                .thenReturn("hoeden");

        Game game = Game.startNewGame();
        Round round = game.startNewRound("baard");
        round.setAttempts(List.of("b...."));
        Feedback feedback = new Feedback("boord", List.of(Mark.ABSENT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT));
        round.setFeedbacks(List.of(feedback));
        game.setRounds(List.of(round));
        game.guess("baard");

//        SpringGameRepository repository = mock(SpringGameRepository.class);
//        when(repository.findById(anyLong()))
//                .thenReturn(Optional.of(game));
//
//        TrainerService trainerService = new TrainerService(repository, wordService);
//        Progress progress = trainerService.startNextRound(game);
//
//        assertEquals(List.of("h", ".", ".", ".", ".", "."), progress.getCurrentHint());
//        assertEquals(GameStatus.PLAYING, progress.getStatus());
    }

    
}
