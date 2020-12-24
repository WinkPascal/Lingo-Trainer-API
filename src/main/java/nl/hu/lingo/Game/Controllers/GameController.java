package nl.hu.lingo.Game.Controllers;

import nl.hu.lingo.Game.Domain.*;
import nl.hu.lingo.Game.Persistence.*;
import nl.hu.lingo.Import.Application.WordService;
import nl.hu.lingo.Import.Application.WordServiceInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GameController {

    @GetMapping("/startGame")
    public int startGame(){
        GameFacadeLingo gameFacade = new GameFacadeLingo();

        return gameFacade.startGame();
    }
    @GetMapping("/highscore")
    public int getHighScore(String username){
        return 1;
    }

    @PostMapping("/nextMove")
    public Map<String, String> nextMove(int gameId, String word) {
        GameFacadeLingo gameFacade = new GameFacadeLingo();

        Map<String, String> feedback = gameFacade.nextMove(gameId, word);

        return feedback;
    }

    @PostMapping("/gameFinished")
    public int gameFinished(int id, String userName) {
        GameFacadeLingo gameFacade = new GameFacadeLingo();

        int score = gameFacade.endGame(id, userName);
        return score;
    }
}