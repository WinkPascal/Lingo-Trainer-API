package nl.hu.lingo.Game.Service;

import nl.hu.lingo.Game.Controllers.GameController;
import nl.hu.lingo.Game.Domain.GameFacadeLingo;
import nl.hu.lingo.Game.Persistence.*;
import nl.hu.lingo.Import.Application.WordService;
import nl.hu.lingo.Import.Application.WordServiceInterface;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameService {
    @GetMapping("/startGame")
    public int startGame() {
        Database database = new DataBasePostgress();
        GameDao gameRepository = new GamePostgressDaoImpl(database);
        WordServiceInterface wordService = new WordService();
        TryDao tryDao = new TryPostgressDao(database);
        RoundDao roundDoa = new RoundPostgressDao(database);
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepository, wordService, tryDao, roundDoa);

        return (new GameController(gameFacade)).startGame();
    }

    @PostMapping("/nextMove")
    public String nextMove(@RequestParam("gameId") String gameId, @RequestParam("word") String word) {
        Database database = new DataBasePostgress();
        WordServiceInterface wordService = new WordService();
        GameDao gameRepository = new GamePostgressDaoImpl(database);
        TryDao tryDao = new TryPostgressDao(database);
        RoundDao roundDoa = new RoundPostgressDao(database);
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepository, wordService, tryDao, roundDoa);
        return (new GameController(gameFacade)).nextMove(Integer.parseInt(gameId), word);
    }

    @PostMapping("/gameFinished")
    public int gameFinished(@RequestParam("id") String id, @RequestParam("username") String username) {
        Database database = new DataBasePostgress();
        GameDao gameRepository = new GamePostgressDaoImpl(database);
        WordService wordService = new WordService();
        TryDao tryDao = new TryPostgressDao(database);
        RoundDao roundDoa = new RoundPostgressDao(database);
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepository, wordService, tryDao, roundDoa);
        return (new GameController(gameFacade)).gameFinished(id, username);
    }
    @GetMapping("/getHighscore/{username}")
    public int getHighscore(@PathVariable("username") String username) {
        Database database = new DataBasePostgress();
        GameDao gameRepository = new GamePostgressDaoImpl(database);
        WordService wordService = new WordService();
        TryDao tryDao = new TryPostgressDao(database);
        RoundDao roundDoa = new RoundPostgressDao(database);
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepository, wordService, tryDao, roundDoa);
        return (new GameController(gameFacade)).getHighscore(username);

    }
}
