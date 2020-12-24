package nl.hu.lingo.Game.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.lingo.Game.Domain.*;
import nl.hu.lingo.Game.Persistence.*;
import nl.hu.lingo.Import.Application.WordService;
import nl.hu.lingo.Import.Application.WordServiceInterface;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/nextMove")
    public String nextMove(@RequestParam("gameId") String gameId, @RequestParam("word") String word) {
        Database database = new DataBasePostgress();
        WordServiceInterface wordService = new WordService();
        GameDao gameRepository = new GamePostgressDaoImpl(database);
        TryDao tryDao = new TryPostgressDao(database);
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepository, wordService, tryDao);

        Map<String, String> feedback = gameFacade.nextMove(Integer.parseInt(gameId), word);

        //convert to json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(feedback);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @PostMapping("/gameFinished")
    public int gameFinished(@RequestParam("id") String id, @RequestParam("username") String username) {
        if(id == "" || username == "") return 0;

        Database database = new DataBasePostgress();
        GameDao gameRepository = new GamePostgressDaoImpl(database);
        WordService wordService = new WordService();
        TryDao tryDao = new TryPostgressDao(database);

        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepository, wordService, tryDao);

        int score = gameFacade.endGame(Integer.parseInt(id), username);
        return score;
    }

    @GetMapping("/getHighscore/{username}")
    public int getHighscore(@PathVariable("username") String username) {
        Database database = new DataBasePostgress();
        GameDao gameRepository = new GamePostgressDaoImpl(database);
        WordService wordService = new WordService();
        TryDao tryDao = new TryPostgressDao(database);

        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepository, wordService, tryDao);

        int score = gameFacade.getHighscore(username);
        return score;
    }
}