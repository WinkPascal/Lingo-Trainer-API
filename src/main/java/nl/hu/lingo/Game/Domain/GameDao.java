package nl.hu.lingo.Game.Domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface GameDao {
    Game getGameById(int id);

    int saveGame();

    void saveTry(int roundId, String word);

    void saveRound(String word, int gameId);
}
