package nl.hu.lingo.Game.Domain;

import java.util.List;
import java.util.Map;

public interface Game {

    List<Round> getRounds();

    Map<String, String> nextMove(Try currentTry);

    int endGame();

    int startGame();
}
