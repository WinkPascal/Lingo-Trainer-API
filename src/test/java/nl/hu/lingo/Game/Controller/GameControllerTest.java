package nl.hu.lingo.Game.Controller;

import nl.hu.lingo.Game.Controllers.GameController;
import nl.hu.lingo.Game.Domain.*;
import nl.hu.lingo.Game.Persistence.*;
import nl.hu.lingo.Import.Service.WordService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openjdk.jmh.annotations.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GameControllerTest {
    private static WordService wordServiceMock;
    private static GameDao gameDaoMock;
    private static TryDao tryDaoMock;
    private static RoundDao roundDaoMock;

    @BeforeEach
    void beforeEach() {
        wordServiceMock = mock(WordService.class);
        gameDaoMock = mock(GamePostgressDaoImpl.class);
        tryDaoMock = mock(TryPostgressDao.class);
        roundDaoMock = mock(RoundPostgressDao.class);
    }

    //=====================================================================================================================
    //=============================================== startGameTest =======================================================
    //=====================================================================================================================

    @Test
    void startGameTest(){
        doNothing().when(tryDaoMock).save(anyInt(), anyString());
        when(gameDaoMock.newGame())
                .thenReturn(100);
        when(wordServiceMock.pickwordForGame(5))
                .thenReturn("fiets");
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameDaoMock, wordServiceMock, tryDaoMock, roundDaoMock);

        GameController gameController = new GameController(gameFacade);
        int newGameId = gameController.startGame();
        assertTrue(newGameId > 0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 1)
    public void startGameTest_benchmark() {
        doNothing().when(tryDaoMock).save(anyInt(), anyString());
        when(gameDaoMock.newGame())
                .thenReturn(100);
        when(wordServiceMock.pickwordForGame(5))
                .thenReturn("fiets");
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameDaoMock, wordServiceMock, tryDaoMock, roundDaoMock);
        GameController gameController = new GameController(gameFacade);

        gameController.startGame();
    }

    //=====================================================================================================================
    //================================================ getHighScore =======================================================
    //=====================================================================================================================

    @Test
    void getHighScore_existing_player(){
        when(gameDaoMock.getHighscore(anyString()))
                .thenReturn(5);
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameDaoMock, wordServiceMock, tryDaoMock, roundDaoMock);
        GameController gameController = new GameController(gameFacade);

        int newGameId = gameController.getHighscore("Pascal");
        assertEquals(5, newGameId);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 1)
    public void getHighScore_existing_player_benchmark() {
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameDaoMock, wordServiceMock, tryDaoMock, roundDaoMock);

        GameController gameController = new GameController(gameFacade);
        gameController.getHighscore("Pascal");
    }

    @Test
    void getHighScore_non_existing_player(){
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameDaoMock, wordServiceMock, tryDaoMock, roundDaoMock);

        GameController gameController = new GameController(gameFacade);
        int newGameId = gameController.getHighscore("Klaas");
        assertEquals(0, newGameId);
    }

    //=====================================================================================================================
    //==================================================== nextMove =======================================================
    //=====================================================================================================================

    @Test
    void nextMove_newgame(){
        Game game = new GameLingo(1, null, new ArrayList<>(), gameDaoMock, wordServiceMock, roundDaoMock);
        when(gameDaoMock.getGameById(anyInt()))
                .thenReturn(game);
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameDaoMock, wordServiceMock, tryDaoMock, roundDaoMock);

        GameController gameController = new GameController(gameFacade);
        int newGameId = gameController.startGame();
        gameController.nextMove(newGameId,"fiets");
        gameController.nextMove(newGameId,"fiets");
        gameController.nextMove(newGameId,"fiets");
        gameController.nextMove(newGameId,"fiets");
        String response = gameController.nextMove(newGameId,"fiets");

        JSONObject jsonObject = new JSONObject(response);
        assertEquals("0", jsonObject.get("triesleft"));
    }

    //=====================================================================================================================
    //======================================================= getId =======================================================
    //=====================================================================================================================

    @Test
    void gameFinished_game_isDone(){
        List<Round> rounds = new ArrayList<Round>();
        rounds.add(new RoundLingo(1, "fiets", new ArrayList<>(), roundDaoMock));
        rounds.add(new RoundLingo(2, "fiets", new ArrayList<>(), roundDaoMock));
        rounds.add(new RoundLingo(3, "fiets", new ArrayList<>(), roundDaoMock));
        rounds.add(new RoundLingo(4, "fiets", new ArrayList<>(), roundDaoMock));
        rounds.add(new RoundLingo(5, "fiets", new ArrayList<>(), roundDaoMock));
        Game game = new GameLingo(1, null, rounds, gameDaoMock, wordServiceMock, roundDaoMock);
        when(gameDaoMock.getGameById(anyInt()))
                .thenReturn(game);
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameDaoMock, wordServiceMock, tryDaoMock, roundDaoMock);

        GameController gameController = new GameController(gameFacade);
        int newGameId = gameController.startGame();

        int score = gameController.gameFinished(Integer.toString(newGameId), "pascal");
        assertEquals(0, score);
    }

    @Test
    void gameFinished_game_isNotDone(){
        List<Round> rounds = new ArrayList<Round>();
        rounds.add(new RoundLingo(1, "fiets", new ArrayList<>(), roundDaoMock));
        rounds.add(new RoundLingo(2, "fiets", new ArrayList<>(), roundDaoMock));
        rounds.add(new RoundLingo(3, "fiets", new ArrayList<>(), roundDaoMock));
        rounds.add(new RoundLingo(4, "fiets", new ArrayList<>(), roundDaoMock));
        Game game = new GameLingo(1, null, rounds, gameDaoMock, wordServiceMock, roundDaoMock);
        when(gameDaoMock.getGameById(anyInt()))
                .thenReturn(game);
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameDaoMock, wordServiceMock, tryDaoMock, roundDaoMock);

        GameController gameController = new GameController(gameFacade);
        int newGameId = gameController.startGame();

        // stil has one try left

        int score = gameController.gameFinished(Integer.toString(newGameId), "pascal");
        assertEquals(score, 0);
    }
}
