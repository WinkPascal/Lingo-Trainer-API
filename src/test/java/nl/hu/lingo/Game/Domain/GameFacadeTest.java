package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.DataBasePostgress;
import nl.hu.lingo.Game.Persistence.Database;
import nl.hu.lingo.Game.Persistence.GamePostgressDaoImpl;
import nl.hu.lingo.Game.Persistence.TryDaoImpl;
import nl.hu.lingo.Import.Application.WordService;
import nl.hu.lingo.Import.Application.WordServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GameFacadeTest {
    private Database database;
    private GameDao gameRepo;
    private WordServiceInterface wordService;
    private TryDao tryDao;
    private GameFacadeLingo gameFacade;
    private Connection conn;

    @BeforeEach
    void beforeEach(){
        database = new DataBasePostgress();
        gameRepo= new GamePostgressDaoImpl(database);
        wordService= new WordService();
        tryDao= new TryDaoImpl(database);
        gameFacade = new GameFacadeLingo(gameRepo, wordService, tryDao);
        conn = database.getConn();
    }

    //==================================================================================================================
    //==================================================================================================================
    //==================================================================================================================

    @Test
    void startGame(){
        int id = gameFacade.startGame(); // Act
        int lastId = getLastGameId();
        assertTrue(lastId == id); // Assert
    }

    int getLastGameId(){
        int maxIdOfGames = 0;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select max(id) from game");
            while (rs.next()) {
                maxIdOfGames = rs.getInt("max");
            }
        } catch (SQLException e ) {
            throw new Error(e);
        }
        return maxIdOfGames;
    }

    //==================================================================================================================
    //==================================================================================================================
    //==================================================================================================================

    @Test
    void nextMoveTest(){
        Map<String, String> resp = gameFacade.nextMove(18, "fiets"); // Act

        assertTrue(resp.get("invalid").equals("false")); // Assert
    }

    //==================================================================================================================
    //==================================================================================================================
    //==================================================================================================================

    @Test
    void endGame(){
        int score = gameFacade.endGame(18, "Pascal");

        assertTrue(score == 1);
    }

    //==================================================================================================================
    //==================================================================================================================
    //==================================================================================================================

    @Test
    void getHighscore(){
        int score = gameFacade.getHighscore("Pascal");

        assertTrue(score == 5);
    }
}
