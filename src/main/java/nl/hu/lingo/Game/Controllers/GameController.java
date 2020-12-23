package nl.hu.lingo.Game.Controllers;

import nl.hu.lingo.Game.Domain.*;
import nl.hu.lingo.Game.Persistence.*;
import nl.hu.lingo.Import.Application.WordService;
import nl.hu.lingo.Import.Application.WordServiceInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GameController {

    @GetMapping("/startGame")
    public int startGame(){
        Database database = new DataBasePostgress();
        GameDao gameRepository = new GamePostgressDaoImpl(database);
        WordServiceInterface wordService = new WordService();
        TryDao tryDao = new TryPostgressDao(database);
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepository, wordService, tryDao);

        return gameFacade.startGame();
    }

    @GetMapping("/nextMove")
    public Map<String, String> nextMove(int gameId, String word) {
        Database database = new DataBasePostgress();
        WordServiceInterface wordService = new WordService();
        GameDao gameRepository = new GamePostgressDaoImpl(database);
        TryDao tryDao = new TryPostgressDao(database);
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepository, wordService, tryDao);

        Map<String, String> feedback = gameFacade.nextMove(gameId, word);
        return feedback;
    }

    @GetMapping("/gameFinished")
    public int gameFinished(int id, String userName) {
        Database database = new DataBasePostgress();
        GameDao gameRepository = new GamePostgressDaoImpl(database);
        WordService wordService = new WordService();
        TryDao tryDao = new TryPostgressDao(database);

        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepository, wordService, tryDao);

        int score = gameFacade.endGame(id, userName);
        return score;
    }
}