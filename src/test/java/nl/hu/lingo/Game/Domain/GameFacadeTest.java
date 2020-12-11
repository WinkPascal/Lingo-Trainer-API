package nl.hu.lingo.Game.Domain;


import nl.hu.lingo.Game.Persistence.DataBasePostgress;
import nl.hu.lingo.Game.Persistence.Database;
import nl.hu.lingo.Game.Persistence.GamePostgressDaoImpl;
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
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepo, wordService);

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
        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepo, wordService);

        Map<String, String> resp = gameFacade.nextMove(14, "balse");
        String tries = resp.get("Tries left");
        Assert.isTrue(tries == "5");
    }

    @Test
    void WrongGameIdnextMove(){
        Database database = new DataBasePostgress();
        GameDao gameRepo = new GamePostgressDaoImpl(database);
        WordServiceInterface wordService = new WordService();

        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepo, wordService);

        gameFacade.nextMove(0, "test");
    }

}
