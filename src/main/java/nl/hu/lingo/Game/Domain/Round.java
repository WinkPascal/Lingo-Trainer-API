package nl.hu.lingo.Game.Domain;

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

    public boolean isLastWord(){
        return false;
    }
}
