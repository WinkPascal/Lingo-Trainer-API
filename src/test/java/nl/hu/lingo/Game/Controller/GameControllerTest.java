package nl.hu.lingo.Game.Controller;

import nl.hu.lingo.Game.Controllers.GameController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameControllerTest {
    @Test
    void startGameTest(){
        GameController gameController = new GameController();
        int newGameId = gameController.startGame();

    }
    @Test
    void getHighScore(){
        GameController gameController = new GameController();
        int newGameId = gameController.getHighScore("pascal");

    }
    @Test
    void nextMove(){
        GameController gameController = new GameController();
    //    int newGameId = gameController.nextMove();

    }
    @Test
    void gameFinished(){
        GameController gameController = new GameController();
      //  int newGameId = gameController.gameFinished();

    }

}
