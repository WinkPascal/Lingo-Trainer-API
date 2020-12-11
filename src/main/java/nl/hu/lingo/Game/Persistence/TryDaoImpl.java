package nl.hu.lingo.Game.Persistence;

import nl.hu.lingo.Game.Domain.TryDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TryDaoImpl implements TryDao {
    private Connection conn;
    public TryDaoImpl(Database database){
        conn = database.getConn();
    }

    public void save(int roundId, String word){
        try{
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO try(word, roundid) VALUES ('"+word+"', "+roundId+")";
            stmt.executeUpdate(sql);
        } catch (SQLException e ) {
            throw new Error(e);
        }
    }
}

