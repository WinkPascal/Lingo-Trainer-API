package nl.hu.lingo.Game.Domain;


import nl.hu.lingo.Game.Persistence.DataBasePostgress;
import nl.hu.lingo.Game.Persistence.Database;
import nl.hu.lingo.Game.Persistence.GamePostgressDaoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootTest
class GameFacadeTest {

    @Test
    void startGame(){
        Database database = new DataBasePostgress();

        GameDao gameRepo = new GamePostgressDaoImpl(database);

        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepo);

        int id = gameFacade.startGame();
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
    void WrongGameIdnextMove(){
        Database database = new DataBasePostgress();

        GameDao gameRepo = new GamePostgressDaoImpl(database);

        GameFacadeLingo gameFacade = new GameFacadeLingo(gameRepo);

        gameFacade.nextMove(0, "test");
    }

}
