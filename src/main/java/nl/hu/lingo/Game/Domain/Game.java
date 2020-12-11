package nl.hu.lingo.Game.Domain;

import java.util.List;
import java.util.Map;

public interface Game {

    static int getHighscore(String username, GameDao gameDao) {
        return gameDao.getHighscore(username);
    }

    List<Round> getRounds();

    Map<String, String> nextMove(Try currentTry);

    int endGame(String name);

    int startGame();
}