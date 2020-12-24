package nl.hu.lingo.Game.Persistence;

import nl.hu.lingo.Game.Domain.Round;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundDao {
    void save(String word, int gameId);

    List<Round> getRoundsByGameId(int id);
}
