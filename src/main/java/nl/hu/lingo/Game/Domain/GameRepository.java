package nl.hu.lingo.Game.Domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface GameRepository {
    Game getGameById(int id);

    int saveGame();

    void saveTry(int roundId, String word);

    int saveRound(String word, int gameId);
}
