package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.TryDao;
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

    public Map<String, String> getFeedback(String correctWord) {
        Map<String, String> feedback = new HashMap<>();
        boolean correctLength = true;
        if(this.word.length() != correctWord.length()) correctLength = false;


        int lettersCorrect = 0;
        int lettersInWord = 0;
        int lettersWrong = 0;
        for (int i = 0;i < this.word.length(); i++){
            char currentLetter = this.word.charAt(i);
            String key = Integer.toString(i);
            if(!correctLength){
                feedback.put(key, "absent");
                lettersWrong++;
            } else if(correctWord.charAt(i) == currentLetter){
                feedback.put(key, "correct");
                lettersCorrect ++;
            } else if(letterInWord(correctWord, currentLetter)){
                feedback.put(key, "present");
                lettersInWord++;
            } else{
                feedback.put(key, "absent");
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

    public Map<String, String> CheckSpellingContraints() {
        Map<String, String> feedback = new HashMap<>();
        if(!word.matches("\\p{javaLowerCase}*")){
            feedback.put("feedback","Er mogen geen leestekens, zoals apostrofs, koppelstreepjes en punten in het woord staan. het woord mag ook niet beginnen met een hoofdletter, zoals plaats- en eigennamen");
            return feedback;
        }
        boolean wordExists = false;
        List<String> words = wordService.getAllWordsWithLength(word.length());
        if(words != null){
            for (String word : words) {
                if(word.equals(this.word)){
                     wordExists = true;
                }
            }
        }
        if(!wordExists) feedback.put("feedback", "Dit woord bestaat niet");
        return feedback;
    }
    public void save(int RoundId){
        tryDao.save(RoundId, word);
    }
}
