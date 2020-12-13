package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Import.Application.WordServiceInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Try {
    private int id;
    private String word;
    private WordServiceInterface wordService;
    private TryDao tryDao;

    public Try(int id, String word, WordServiceInterface wordService, TryDao tryDao){
        this.id = id;
        this.word = word;
        this.wordService = wordService;
        this.tryDao = tryDao;
    }

    public boolean WordExists(){
        List<String> words = wordService.getAllWordsWithLength(word.length());
        if(words != null){
            for (String word : words) {
                if(word.equals(this.word)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean wordIsLength(int length) {
        return this.word.length() == length;
    }

    public void save(int RoundId){
        tryDao.save(RoundId, word);
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

    public Map<String, String> getFeedback(String correctWord) {
        Map<String, String> feedback = new HashMap<>();
        int lettersCorrect = 0;
        int lettersInWord = 0;
        int lettersWrong = 0;
        for (int i = 0;i < this.word.length(); i++){
            char currentLetter = this.word.charAt(i);
            String key = Integer.toString(i)+":"+String.valueOf(currentLetter);

            if(correctWord.charAt(i) == currentLetter){
                feedback.put(key, " correct");
                lettersCorrect ++;
            } else if(letterInWord(correctWord, currentLetter)){
                feedback.put(key, " In woord");
                lettersInWord++;
            } else{
                feedback.put(key, " Is niet in woord");
                lettersWrong++;
            }
        }
        feedback.put("lettersCorrect", Integer.toString(lettersCorrect));
        feedback.put("lettersInWord", Integer.toString(lettersInWord));
        feedback.put("lettersWrong", Integer.toString(lettersWrong));

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
