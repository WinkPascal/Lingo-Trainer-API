package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.GameDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public interface Game {

    int getHighscore();

    Map<String, String> nextMove(Try currentTry);

    int endGame(String name);

    int startGame();
}