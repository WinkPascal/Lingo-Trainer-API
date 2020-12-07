package nl.hu.lingo.Game.Domain;

public interface GameRepository {
    Game getGameById(int id);
    void saveGame(Game game);
}
