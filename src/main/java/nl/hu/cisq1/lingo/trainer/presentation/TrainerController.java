package nl.hu.cisq1.lingo.trainer.presentation;

import lombok.RequiredArgsConstructor;
import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameOverException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidGuessException;
import nl.hu.cisq1.lingo.trainer.presentation.dto.GuessDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainer/game")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;

    @GetMapping
    public List<Game> findAll() {
        return trainerService.findAll();
    }

    @PostMapping
    public Progress startGame() {
        return trainerService.startNewGame();
    }

    @PostMapping("/{id}")
    public Progress guess(@PathVariable Long id, @RequestBody GuessDto guessDto) throws GameNotFoundException, GameOverException, InvalidFeedbackException, InvalidGuessException {
        Game game = trainerService.findById(id);
        return trainerService.guess(game, guessDto);
    }

    @ExceptionHandler({ GameNotFoundException.class })
    public ResponseEntity<String> handleNotFoundExceptions(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler({InvalidGuessException.class, InvalidFeedbackException.class, GameOverException.class, })
    public ResponseEntity<String> handleBadRequestException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
