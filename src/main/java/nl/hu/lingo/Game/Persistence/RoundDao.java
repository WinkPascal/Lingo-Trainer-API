package nl.hu.lingo.Game.Persistence;

public interface RoundDao {
    void save(String word, int gameId);
}
