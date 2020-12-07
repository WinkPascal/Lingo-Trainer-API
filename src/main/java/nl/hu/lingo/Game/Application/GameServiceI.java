package nl.hu.lingo.Game.Application;

import org.json.JSONObject;

public interface GameServiceI {
    JSONObject startGame();
    JSONObject nextMove(int id, String word);
    JSONObject gameFinished(int id, String name);
}
