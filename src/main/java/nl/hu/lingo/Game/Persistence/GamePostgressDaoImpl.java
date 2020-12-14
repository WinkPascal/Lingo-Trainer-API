package nl.hu.lingo.Game.Persistence;

import nl.hu.lingo.Game.Domain.*;
import nl.hu.lingo.Import.Application.WordService;
import nl.hu.lingo.Import.Application.WordServiceInterface;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Scanner;

public class GamePostgressDaoImpl implements GameDao {
    private Connection conn = null;

    public GamePostgressDaoImpl(Database database) {
        conn = database.getConn();
    }

    @Override
    public Game getGameById(int id, WordServiceInterface wordService) {
        Game game = null;
        String query = "select * from game where id = "+id;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String username = rs.getString("username");
                List<Round> rounds = getRoundsByGameId(id);

                game = new GameLingo(id, username, rounds, this, wordService);
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
        TryDao tryDao = new TryDaoImpl(new DataBasePostgress());
        WordServiceInterface wordService = new WordService();
        String query = "select * from try where roundId = "+id;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String word = rs.getString("word");
                int tryId = rs.getInt("id");
                Try try_ = new Try(tryId, word, wordService, tryDao);
                tries.add(try_);
            }
        } catch (SQLException e ) {
            throw new Error(e);
        }
        return tries;
    }

    public int newGame(){
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

     public void saveRound(String word, int gameId){
        try{
             Statement  stmt = conn.createStatement();
             String sql = "INSERT INTO round(word, gameid) VALUES ('"+word+"', "+gameId+")";
             stmt.executeUpdate(sql);

         } catch (SQLException e ) {
             throw new Error(e);
         }
     }

    public void saveName(int id, String name){
        try{
            Statement  stmt = conn.createStatement();
            String sql = "UPDATE game set username = '"+name+"' WHERE id = "+ id;
            stmt.executeUpdate(sql);

        } catch (SQLException e ) {
            throw new Error(e);
        }
    }

    public int getScore(int id){
        String query = "select count(r.id) as score from game g join round r on g.id = r.gameid where g.id = "+id;
        int score = 0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                score = rs.getInt("score");
            }
        } catch (SQLException e ) {
            throw new Error(e);
        }
        return score;
    }

    public int getHighscore(String username) {
        String query = "select count(r.id) as score from game g join round r on g.id = r.gameid " +
                "where g.username = '"+username+"'  " +
                "GROUP BY g.Id " +
                "ORDER BY count(r.id) DESC " +
                "LIMIT 1";
        int score = 0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                score = rs.getInt("score");
            }
        } catch (SQLException e ) {
            throw new Error(e);
        }
        return score;
    }
}
