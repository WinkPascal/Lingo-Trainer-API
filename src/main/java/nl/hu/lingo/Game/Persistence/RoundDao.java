package nl.hu.lingo.Game.Persistence;

import nl.hu.lingo.Game.Domain.Round;
import nl.hu.lingo.Game.Domain.RoundLingo;

import java.util.List;

public interface RoundDao {
    void save(String word, int gameId);

    List<Round> getRoundsByGameId(int id);
}
