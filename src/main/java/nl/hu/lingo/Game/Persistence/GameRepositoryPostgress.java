package nl.hu.lingo.Game.Persistence;

import nl.hu.lingo.Game.Domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameRepositoryPostgress implements GameRepository {
    private Connection conn = null;

    public GameRepositoryPostgress(Database database) {
        conn = database.getConn();
    }

    @Override
    public Game getGameById(int id) {
        Game game = null;
        String query = "select * from game where id = "+id;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String username = rs.getString("username");
                List<Round> rounds = getRoundsByGameId(id);

                game = new GameLingo(id, username, rounds);
            }
        } catch (SQLException e ) {
            throw new Error(e);
        }
        return game;
    }
    private List<Round> getRoundsByGameId(int id){
        List<Round> rounds = new ArrayList<>();
        String query = "select * from round where gameId = "+id;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int roundId = rs.getInt("id");
                String word = rs.getString("word");
                List<Try> tries = getTriesByRoundId(roundId);

                Round round = new Round(roundId, word,tries);
                rounds.add(round);
            }
        } catch (SQLException e ) {
            throw new Error(e);
        }
        return rounds;
    }
    private List<Try> getTriesByRoundId(int id){
        List<Try> tries = new ArrayList<>();
        String query = "select * from try where roundId = "+id;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String word = rs.getString("word");
                int tryId = rs.getInt("id");

                Try try_ = new Try(tryId , word);
                tries.add(try_);
            }
        } catch (SQLException e ) {
            throw new Error(e);
        }
        return tries;
    }

    public int saveGame(){
        int id = 0;
        try{
            Statement  stmt = conn.createStatement();

            String sql = "INSERT INTO Game(username) VALUES (null)";
            stmt.executeUpdate(sql);


            String query = "select max(id)  from game";
            Statement stmt1 = conn.createStatement();
            ResultSet rs = stmt1.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("max");
            }

        } catch (SQLException e ) {
            throw new Error("Problem", e);
        }
        return id;
    }

     public void saveTry(int roundId, String word){
         try{
             Statement  stmt = conn.createStatement();

             String sql = "INSERT INTO try(word, roundid) VALUES ('"+word+"', "+roundId+")";
             stmt.executeUpdate(sql);
         } catch (SQLException e ) {
             throw new Error(e);
         }
     }

     public int saveRound(String word, int gameId){
        int id;
        try{
             Statement  stmt = conn.createStatement();

             String sql = "INSERT INTO round(word, gameid) VALUES ('"+word+"', "+gameId+")";
             id = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

         } catch (SQLException e ) {
             throw new Error(e);
         }
        return id;
     }
}
