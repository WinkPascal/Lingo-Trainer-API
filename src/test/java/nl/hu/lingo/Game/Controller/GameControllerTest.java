package nl.hu.lingo.Game.Controller;
import nl.hu.lingo.Game.Controllers.GameController;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GameControllerTest {

    //=====================================================================================================================
    //=============================================== startGameTest =======================================================
    //=====================================================================================================================

    @Test
    void startGameTest(){
        GameController gameController = new GameController();
        int newGameId = gameController.startGame();
        assertTrue(newGameId > 0);
    }

    //=====================================================================================================================
    //================================================ getHighScore =======================================================
    //=====================================================================================================================

    @Test
    void getHighScore_existing_player(){
        GameController gameController = new GameController();
        int newGameId = gameController.getHighscore("Pascal");
        assertTrue(newGameId == 5);
    }

    @Test
    void getHighScore_non_existing_player(){
        GameController gameController = new GameController();
        int newGameId = gameController.getHighscore("Klaas");
        assertTrue(newGameId == 0);
    }

    //=====================================================================================================================
    //==================================================== nextMove =======================================================
    //=====================================================================================================================
    static Stream<Arguments> nextMove_newgame_set() {
        return Stream.of(
                // five letter words
                Arguments.of("4", 1),
                Arguments.of("3", 2),
                Arguments.of("2", 3),
                Arguments.of("1", 4),
                Arguments.of("0", 5)
                );
    };
    @ParameterizedTest
    @MethodSource("nextMove_newgame_set")
    void nextMove_newgame(String triesLeft, int tries){
        GameController gameController = new GameController();
        int newGameId = gameController.startGame();
        String response = null;
        for (int i = 0; i < tries; i++) {
            response = gameController.nextMove(Integer.toString(newGameId),"fiets");
        }
        JSONObject jsonObject = new JSONObject(response);
        assertEquals(triesLeft, jsonObject.get("Tries left"));
    }

    //=====================================================================================================================
    //======================================================= getId =======================================================
    //=====================================================================================================================

    @Test
    void gameFinished_game_isDone(){
        GameController gameController = new GameController();
        int newGameId = gameController.startGame();
        gameController.nextMove(Integer.toString(newGameId),"fiets");
        gameController.nextMove(Integer.toString(newGameId),"fiets");
        gameController.nextMove(Integer.toString(newGameId),"fiets");
        gameController.nextMove(Integer.toString(newGameId),"fiets");
        gameController.nextMove(Integer.toString(newGameId),"fiets");

        int score = gameController.gameFinished(Integer.toString(newGameId), "pascal");
        assertTrue(score == 1);
    }
    @Test
    void gameFinished_game_isNotDone(){
        GameController gameController = new GameController();
        int newGameId = gameController.startGame();
        gameController.nextMove(Integer.toString(newGameId),"fiets");
        gameController.nextMove(Integer.toString(newGameId),"fiets");
        gameController.nextMove(Integer.toString(newGameId),"fiets");
        gameController.nextMove(Integer.toString(newGameId),"fiets");
        // stil has one try left

        int score = gameController.gameFinished(Integer.toString(newGameId), "pascal");
        assertTrue(score == 0);
    }
}