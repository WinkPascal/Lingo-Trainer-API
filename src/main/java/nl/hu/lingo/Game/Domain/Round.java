package nl.hu.lingo.Game.Domain;

import java.util.Map;

public interface Round {
     Map<String, String> IsCorrect(Try currentTry);
     boolean isActive();
     void save(int gameid);
     int getId();
}
