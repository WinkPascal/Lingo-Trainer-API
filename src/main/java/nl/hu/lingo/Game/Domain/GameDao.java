package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Import.Application.WordServiceInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface GameDao {
    Game getGameById(int id, WordServiceInterface wordService);

    int saveGame();

    void saveTry(int roundId, String word);

    void saveRound(String word, int gameId);

    void saveName(int id, String name);

    int getScore(int id);

    int getHighscore(String username);
}
