package nl.hu.lingo.Game.Domain;

import java.util.List;

public interface Game {

    List<Round> getRounds();

    List<String> nextMove(Try currentTry);

    int endGame();
}
