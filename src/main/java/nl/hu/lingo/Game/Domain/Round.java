package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.RoundDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Round {
    private int id;
    private String word;
    private List<Try> tries;
    @Autowired
    private RoundDao roundDao;

    public Round(int id, String word, List<Try> tries) {
        this.id = id;
        this.word = word;
        this.tries = tries;
    }

    public Map<String, String> IsCorrect(Try currentTry) {
        Map<String, String> feedback = new HashMap<>();
        tries.add(currentTry);
        // 1. Check if there are already five tries made
        if(tries.size() > 5){
            feedback.put("message", "Game over, because you already did 5 tries in this round. call endGame method to save name.");
            return feedback;
        }
        // 2. save try
        currentTry.save(this.id);
        // 3. check spelling constraints
        feedback = currentTry.CheckSpellingContraints();
        if(feedback.size() > 0){
            feedback.put("message", "Spelling was not correct.");
            return feedback;
        }
        // 4. get feedback for letters
        feedback = currentTry.getFeedback(this.word);
        feedback.put("Letters in word", Integer.toString(word.length()));
        feedback.put("Tries left", Integer.toString(5 - tries.size()));
        // 5. check if last try was incorrect
        if(tries.size() == 5 && !feedback.get("lettersCorrect").equals(Integer.toString(word.length()))){
            feedback.put("message", "This was your fifth try. Game over, call endGame method to save name.");
        }
        return feedback;
    }

    public boolean isActive() {
        return tries.size() < 5;
    }
    public void save(int gameid){
        roundDao.save(word, gameid);
    }

    public int getId(){
        return id;
    }
}
