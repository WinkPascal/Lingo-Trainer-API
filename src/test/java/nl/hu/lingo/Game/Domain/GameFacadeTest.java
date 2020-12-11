package nl.hu.lingo.Game.Domain;


import nl.hu.lingo.Game.Persistence.DataBasePostgress;
import nl.hu.lingo.Game.Persistence.Database;
import nl.hu.lingo.Game.Persistence.GamePostgressDaoImpl;
import nl.hu.lingo.Game.Persistence.TryDaoImpl;
import nl.hu.lingo.Import.Application.WordService;
import nl.hu.lingo.Import.Application.WordServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@SpringBootTest
class GameFacadeTest {

    @Test
    void startGame(){
        Database database = new DataBasePostgress();
        GameDao gameRepo = new GamePostgressDaoImpl(database);
        WordServiceInterface wordService = new WordService();
        TryDao tryDao = new TryDaoImpl(database);

        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepo, wordService, tryDao);

        int id = gameFacade.startGame();

        // controle
        int maxIdOfGames = 0;
        Connection conn = database.getConn();

        String query = "select max(id)  from game";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                maxIdOfGames = rs.getInt("max");
            }
        } catch (SQLException e ) {
            throw new Error(e);
        }

        Assert.isTrue(maxIdOfGames == id);
    }
    @Test
    void nextMoveTest(){
        Database database = new DataBasePostgress();
        GameDao gameRepo = new GamePostgressDaoImpl(database);
        WordServiceInterface wordService = new WordService();
        TryDao tryDao = new TryDaoImpl(database);
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepo, wordService, tryDao);

        Map<String, String> resp = gameFacade.nextMove(16, "aarde");
        String tries = resp.get("Tries left");
        Assert.isTrue(tries == "3");
    }

    @Test
    void endGame(){
        Database database = new DataBasePostgress();
        GameDao gameRepo = new GamePostgressDaoImpl(database);
        WordServiceInterface wordService = new WordService();
        TryDao tryDao = new TryDaoImpl(database);
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepo, wordService, tryDao);

        int score = gameFacade.endGame(16, "Pascal");
        Assert.isTrue(score == 1);
    }

    @Test
    void WrongGameIdnextMove(){
        Database database = new DataBasePostgress();
        GameDao gameRepo = new GamePostgressDaoImpl(database);
        WordServiceInterface wordService = new WordService();
        TryDao tryDao = new TryDaoImpl(database);

        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepo, wordService,tryDao);

        gameFacade.nextMove(0, "test");
    }

}
