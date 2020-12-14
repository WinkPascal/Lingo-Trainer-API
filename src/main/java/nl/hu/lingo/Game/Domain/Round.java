package nl.hu.lingo.Game.Domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Round {
    private int id;
    private String word;
    private List<Try> tries;

    public Round(int id, String word, List<Try> tries){
        this.id = id;
        this.word = word;
        this.tries = tries;
    }

    public int getId(){
        return id;
    }

    public boolean isLastWord(){
        return false;
    }

    public Map<String, String> IsCorrect(Try currentTry) {
        Map<String, String> feedback = new HashMap<>();
        tries.add(currentTry);
        if(tries.size() > 5){
            feedback.put("invalid", "true");
            feedback.put("message", "Game over, call endGame method to save name.");
            return feedback;
        }
        currentTry.save(this.id);
        feedback = isTryValid(currentTry);
        // check spelling constraints
        if(feedback.size() > 0){
            feedback.put("message", "Spelling was not correct.");
            feedback.put("invalid", "true");
            return feedback;
        }

        // get feedback for letters
        feedback = currentTry.getFeedback(this.word);
        feedback.put("invalid", "false");
        String lettersInWord = Integer.toString(word.length());
        feedback.put("Letters in word", lettersInWord);
        feedback.put("Tries left", Integer.toString(5 - tries.size()));
        if(tries.size() == 5 && !feedback.get("lettersCorrect").equals(lettersInWord)){
            feedback.put("message", "Game over, call endGame method to save name.");
        }
        return feedback;
    }

    private Map<String, String> isTryValid(Try currentTry){
        Map<String, String> feedback = new HashMap<>();

        //Het woord bestaat uit het gegeven aantal letters
        if(!currentTry.wordIsLength(this.word.length())){
            feedback.put("feedback","Het word moet " + word.length() + " letters lang zijn.");
            return feedback;
        }

        //Het woord schrijf je niet met een hoofdletter, zoals plaats- en eigennamen
        //Het woord bevat geen leestekens, zoals apostrofs, koppelstreepjes en punten
        if(!currentTry.IsCorrectlySpelled()){
            feedback.put("feedback","er mogen geen leestekens, zoals apostrofs, koppelstreepjes en punten in het woord staan.  " +
                    "het woord mag ook niet beginnen met een hoofdletter, zoals plaats- en eigennamen");
            return feedback;
        }

        //Het woord bestaat
        //Het woord is juist gespeld
        if(!currentTry.WordExists()){
            feedback.put("feedback", "Dit woord bestaat niet");
        }

        return feedback;
    }

    public boolean isActive() {
        return tries.size() < 5;
    }
}
