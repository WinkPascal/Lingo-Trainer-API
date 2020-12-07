package nl.hu.lingo.Game.Controllers;

import nl.hu.lingo.Game.Application.GameService;
import nl.hu.lingo.Game.Application.GameServiceI;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    @GetMapping("/startGame")
    public String startGame(){
        GameServiceI gameServiceI = new GameService();
        gameServiceI.startGame();

        return "hello world";
    }

    @GetMapping("/nextMove")
    public String nextMove(int id, String word) {
        GameServiceI gameServiceI = new GameService();
        gameServiceI.startGame();
        return null;
    }

    @GetMapping("/gameFinished")
    public String gameFinished(int id, String word) {
        GameServiceI gameServiceI = new GameService();
        gameServiceI.startGame();
        return null;
    }
}