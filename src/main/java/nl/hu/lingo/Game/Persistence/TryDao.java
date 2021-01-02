package nl.hu.lingo.Game.Persistence;

import nl.hu.lingo.Game.Domain.Try;
import nl.hu.lingo.Game.Domain.TryLingo;

import java.util.List;

public interface TryDao {
    void save(int RoundId, String word);

    List<Try> getTriesByRoundId(int id);
}
