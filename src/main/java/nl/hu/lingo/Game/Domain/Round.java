package nl.hu.lingo.Game.Domain;

import java.util.List;

public class Round {
    private int id;
    private String word;
    private List<String> bestFeedback;

    public boolean isLastWord(){
        return false;
    }
}
