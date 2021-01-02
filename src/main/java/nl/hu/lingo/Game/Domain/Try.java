package nl.hu.lingo.Game.Domain;

import java.util.Map;

public interface Try {

     Map<String, String> getFeedback(String correctWord);
     Map<String, String> CheckSpellingContraints();
     void save(int RoundId);

}
