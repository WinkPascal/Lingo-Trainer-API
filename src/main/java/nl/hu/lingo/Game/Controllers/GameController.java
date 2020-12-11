package nl.hu.lingo.Game.Controllers;

import nl.hu.lingo.Game.Domain.*;
import nl.hu.lingo.Game.Persistence.DataBasePostgress;
import nl.hu.lingo.Game.Persistence.Database;
import nl.hu.lingo.Game.Persistence.GamePostgressDaoImpl;
import nl.hu.lingo.Import.Application.WordService;
import nl.hu.lingo.Import.Application.WordServiceInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GameController {
    @GetMapping("/")
    public String getHelloWorld(){
        return "hello wosrld";
    }


    @GetMapping("/startGame")
    public int startGame(){
        Database database = new DataBasePostgress();
        GameDao gameRepository = new GamePostgressDaoImpl(database);
        WordServiceInterface wordService = new WordService();

        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepository, wordService);

        int id = gameFacade.startGame();

        return id;
    }

    @GetMapping("/nextMove")
    public Map<String, String> nextMove(int gameId, String word) {
        Database database = new DataBasePostgress();
        WordServiceInterface wordService = new WordService();
        GameDao gameRepository = new GamePostgressDaoImpl(database);
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepository, wordService);

        Map<String, String> feedback = gameFacade.nextMove(gameId, word);
        return feedback;
    }

    @GetMapping("/gameFinished")
    public String gameFinished(int id, String userName) {
        Database database = new DataBasePostgress();
        GameDao gameRepository = new GamePostgressDaoImpl(database);
        WordService wordService = new WordService();

        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepository, wordService);

        int score = gameFacade.gameFinished(id, userName);
        return null;
    }
}