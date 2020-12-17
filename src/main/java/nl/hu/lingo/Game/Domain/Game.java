package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.GameDao;

import java.util.Map;

public interface Game {

    static int getHighscore(String username, GameDao gameDao) {
        return gameDao.getHighscore(username);
    }

    Map<String, String> nextMove(Try currentTry);

    int endGame(String name);

    int startGame();
}