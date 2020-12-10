package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.DataBasePostgress;
import nl.hu.lingo.Game.Persistence.Database;
import nl.hu.lingo.Game.Persistence.TryDaoImpl;

import java.util.ArrayList;
import java.util.List;

public class Try {
    private int id;
    private String word;

    public Try(int id, String word){
        this.id = id;
        this.word = word;
    }

    public boolean WordExists(){
        Database database = new DataBasePostgress();
        TryDao tryDao = new TryDaoImpl(database);

        List<String> words = tryDao.GetAllWords();
        for (String word : words) {
            if(word == this.word){
                return true;
            }
        }
        return false;
    }

    public boolean wordIsLength(int length) {
        return this.word.length() == length;
    }

    public boolean IsCorrectlySpelled() {
        return (!word.substring(0, 1).equals(word.substring(0, 1).toUpperCase())
                && word.indexOf(".") == -1
                && word.indexOf(",") == -1
                && word.indexOf("-") == -1
                && word.indexOf("=") == -1
                && word.indexOf("'") == -1
                && word.indexOf("?") == -1
                && word.indexOf("!") == -1
                && word.indexOf(":") == -1
                && word.indexOf("\"") == -1
                && word.indexOf(";") == -1);
    }

    public List<String> getFeedback(String correctWord) {
        List<String> feedback = new ArrayList<>();

        for (int i = 0;i < this.word.length(); i++){
            char currentLetter = this.word.charAt(i);

            if(correctWord.charAt(i) == currentLetter){
                feedback.add(currentLetter + " correct");
            } else if(letterInWord(correctWord, currentLetter)){
                feedback.add(currentLetter + " In woord");
            } else{
                feedback.add(currentLetter + " Is niet in woord");
            }
        }
        return feedback;
    }

    private boolean letterInWord(String correctWord, char currentLetter){
        for (int d = 0;d < correctWord.length(); d++){
            if(currentLetter == correctWord.charAt(d)){
                return true;
            }
        }
        return false;
    }
}
