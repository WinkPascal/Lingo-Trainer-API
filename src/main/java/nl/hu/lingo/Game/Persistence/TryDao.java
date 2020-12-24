package nl.hu.lingo.Game.Persistence;

import nl.hu.lingo.Game.Domain.Try;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TryDao {
    void save(int RoundId, String word);

    List<Try> getTriesByRoundId(int id);
}
