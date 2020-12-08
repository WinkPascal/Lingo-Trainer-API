package nl.hu.lingo.Game.Domain;


import nl.hu.lingo.Game.Persistence.DataBasePostgress;
import nl.hu.lingo.Game.Persistence.Database;
import nl.hu.lingo.Game.Persistence.GameRepositoryPostgress;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@SpringBootTest
class GameFacadeTest {

    @Test
    void startGame(){
        Database database = new DataBasePostgress();

        GameRepository gameRepo = new GameRepositoryPostgress(database);

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



}
