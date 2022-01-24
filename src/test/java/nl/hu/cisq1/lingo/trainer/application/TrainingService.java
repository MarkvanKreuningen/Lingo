package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    
}
