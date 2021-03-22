package nl.hu.lingo.Game.Persistence;

import nl.hu.lingo.Game.Domain.Game;

public interface GameDao {
    Game getGameById(int id);

    int newGame();

    void update(int id, String name);

    int getScore(int id);

    int getHighscore(String username);
}
