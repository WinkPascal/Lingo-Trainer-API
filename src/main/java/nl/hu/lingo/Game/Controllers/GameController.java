package nl.hu.lingo.Game.Controllers;

import nl.hu.lingo.Game.Domain.*;
import nl.hu.lingo.Game.Persistence.DataBasePostgress;
import nl.hu.lingo.Game.Persistence.Database;
import nl.hu.lingo.Game.Persistence.GameRepositoryPostgress;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameController {
    @GetMapping("/")
    public String getHelloWorld(){
        return "hello wosrld";
    }


    @GetMapping("/startGame")
    public int startGame(){
        Database database = new DataBasePostgress();
        GameRepository gameRepository = new GameRepositoryPostgress(database);
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepository);

        int id = gameFacade.startGame();

        return id;
    }

    @GetMapping("/nextMove")
    public String nextMove(int gameId, String word) {
        Database database = new DataBasePostgress();
        GameRepository gameRepository = new GameRepositoryPostgress(database);
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepository);

        List<String> feedback = gameFacade.nextMove(gameId, word);
        return null;
    }

    @GetMapping("/gameFinished")
    public String gameFinished(int id, String userName) {
        Database database = new DataBasePostgress();
        GameRepository gameRepository = new GameRepositoryPostgress(database);
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepository);

        int score = gameFacade.gameFinished(id, userName);
        return null;
    }
}