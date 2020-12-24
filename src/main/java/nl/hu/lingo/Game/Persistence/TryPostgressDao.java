package nl.hu.lingo.Game.Persistence;

import nl.hu.lingo.Game.Domain.Try;
import nl.hu.lingo.Import.Application.WordService;
import nl.hu.lingo.Import.Application.WordServiceInterface;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TryPostgressDao implements TryDao {
    private Connection conn;
    public TryPostgressDao(Database database){
        conn = database.getConn();
    }

    public void save(int roundId, String word){
        try{
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO try(word, roundid, datetime) VALUES ('"+word+"', "+roundId+", current_timestamp)";
            stmt.executeUpdate(sql);
        } catch (SQLException e ) {
            throw new Error(e);
        }
    }

    public List<Try> getTriesByRoundId(int id){
        List<Try> tries = new ArrayList<>();
        TryDao tryDao = new TryPostgressDao(new DataBasePostgress());
        WordServiceInterface wordService = new WordService();
        String query = "select * from try where roundId = "+id;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String word = rs.getString("word");
                int tryId = rs.getInt("id");
                Timestamp datetime = rs.getTimestamp("datetime");
                Try try_ = new Try(tryId, word, datetime);
                tries.add(try_);
            }
        } catch (SQLException e ) {
            throw new Error(e);
        }
        return tries;
    }
}

