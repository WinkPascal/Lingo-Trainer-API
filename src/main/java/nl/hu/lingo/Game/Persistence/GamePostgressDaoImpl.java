package nl.hu.lingo.Game.Persistence;

import nl.hu.lingo.Game.Domain.*;
import nl.hu.lingo.Import.Service.WordService;
import nl.hu.lingo.Import.Service.WordServiceInterface;

import java.sql.*;
import java.util.List;

public class GamePostgressDaoImpl implements GameDao {
    private Connection conn = null;

    public GamePostgressDaoImpl(Database database) {
        conn = database.getConn();
    }

    @Override
    public Game getGameById(int id) {
        Game game = null;
        WordServiceInterface wordService = new WordService();
        String query = "select * from game where id = "+id;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String username = rs.getString("username");
                RoundDao roundPostgressDao = new RoundPostgressDao(new DataBasePostgress());
                List<Round> rounds = roundPostgressDao.getRoundsByGameId(id);
                RoundDao roundDao = new RoundPostgressDao(new DataBasePostgress());
                game = new GameLingo(id, username, rounds, this, wordService, roundDao);
            }
        } catch (SQLException e ) {
            throw new Error(e);
        }
        return game;
    }

    @Override
    public int newGame(){
        int id = 0;
        try (Statement stmt = conn.createStatement()) {
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

    @Override
    public void update(int id, String name){
        try (Statement stmt = conn.createStatement()) {
            String sql = "UPDATE game set username = '"+name+"' WHERE id = "+ id;
            stmt.executeUpdate(sql);
        } catch (SQLException e ) {
            throw new Error(e);
        }
    }

    @Override
    public int getScore(int id){
        String query = "select count(r.id) as score from game g join round r on g.id = r.gameid where g.id = "+id;
        int score = 0;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                score = rs.getInt("score");
            }
        } catch (SQLException e ) {
            throw new Error(e);
        }
        return score;
    }

    @Override
    public int getHighscore(String username) {
        String query = "select count(r.id) as score from game g join round r on g.id = r.gameid " +
                "where g.username = '"+username+"'  " +
                "GROUP BY g.Id " +
                "ORDER BY count(r.id) DESC " +
                "LIMIT 1";
        int score = 0;
        try (Statement stmt = conn.createStatement()) {
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
