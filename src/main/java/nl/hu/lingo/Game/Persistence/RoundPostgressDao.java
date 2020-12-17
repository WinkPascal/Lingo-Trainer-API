package nl.hu.lingo.Game.Persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RoundPostgressDao implements RoundDao {
    private Connection conn = null;

    public RoundPostgressDao(Database database) {
        conn = database.getConn();
    }

    public void save(String word, int gameId) {
        try{
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO round(word, gameid) VALUES ('"+word+"', "+gameId+")";
            stmt.executeUpdate(sql);

        } catch (SQLException e ) {
            throw new Error(e);
        }
    }

}
