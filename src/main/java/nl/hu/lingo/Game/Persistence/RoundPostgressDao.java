package nl.hu.lingo.Game.Persistence;

import nl.hu.lingo.Game.Domain.Round;
import nl.hu.lingo.Game.Domain.RoundLingo;
import nl.hu.lingo.Game.Domain.Try;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoundPostgressDao implements RoundDao {
    private Connection conn = null;

    public RoundPostgressDao(Database database) {
        conn = database.getConn();
    }

    public void save(String word, int gameId) {
        try (Statement stmt = conn.createStatement()) {
            String sql = "INSERT INTO round(word, gameid) VALUES ('"+word+"', "+gameId+")";
            stmt.executeUpdate(sql);

        } catch (SQLException e ) {
            throw new Error(e);
        }
    }

    public List<Round> getRoundsByGameId(int id){
        List<Round> rounds = new ArrayList<>();
        String query = "select * from round where gameId = "+id;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int roundId = rs.getInt("id");
                String word = rs.getString("word");

                TryDao tryPostgressDao = new TryPostgressDao(new DataBasePostgress());
                List<Try> tries = tryPostgressDao.getTriesByRoundId(roundId);
                RoundDao roundDao = new RoundPostgressDao(new DataBasePostgress());
                RoundLingo round = new RoundLingo(roundId, word,tries, roundDao);
                rounds.add(round);
            }
        } catch (SQLException e ) {
            throw new Error(e);
        }
        return rounds;
    }

}
