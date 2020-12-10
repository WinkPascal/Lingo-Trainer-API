package nl.hu.lingo.Game.Domain;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> IsCorrect(Try currentTry) {
        if(tries.size() > 5){

        }
        List<String> feedback = isTryValid(currentTry);
        if(feedback.size() != 0){
            return feedback;
        }

        return currentTry.getFeedback(this.word);
    }

    private List<String> isTryValid(Try currentTry){
        //Het woord bestaat
        //Het woord is juist gespeld
        List<String> feedback = new ArrayList<>();
        if(!currentTry.WordExists()){
            feedback.add("Dit woord bestaat niet");
        }

        //Het woord bestaat uit het gegeven aantal letters
        if(!currentTry.wordIsLength(this.word.length())){
            feedback.add("Het word moet " + word.length() + " letters lang zijn.");
        }

        //Het woord schrijf je niet met een hoofdletter, zoals plaats- en eigennamen
        //Het woord bevat geen leestekens, zoals apostrofs, koppelstreepjes en punten
        if(!currentTry.IsCorrectlySpelled()){
            feedback.add("er mogen geen leestekens, zoals apostrofs, koppelstreepjes en punten in het woord staan.  " +
                    "het woord mag ook niet beginnen met een hoofdletter, zoals plaats- en eigennamen");
        }
        return feedback;
    }

}
