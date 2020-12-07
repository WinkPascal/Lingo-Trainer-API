package nl.hu.lingo.Game.Domain;

import java.util.List;
import nl.hu.lingo.Game.Domain.Try;

public interface Game {
    int startGame();
    List<String> nextMove(Try try);
    int endGame();
}
