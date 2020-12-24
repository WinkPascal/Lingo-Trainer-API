package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.GameDao;
import nl.hu.lingo.Game.Persistence.RoundDao;
import nl.hu.lingo.Import.Application.WordServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GameLingo implements Game {
    private int id;
    private String userName;
    private List<Round> rounds;

    @Autowired
    private GameDao gameDao;
    @Autowired
    private WordServiceInterface wordService;

    public GameLingo(int id, String userName, List<Round> rounds){
        this.id = id;
        this.userName = userName;
        this.rounds = rounds;
    }

    private Round getLastRound(){
        Round lastRound = null;
        int biggestId = 0;
        if(rounds != null){
            for (Round round : rounds) {
                int roundId = round.getId();
                if (biggestId < roundId) {
                    lastRound = round;
                }
            }
        }
        return lastRound;
    }

    @Override
    public Map<String, String> nextMove(Try currentTry) {
        Round currentRound = getLastRound();

        Map<String, String> feedback = new HashMap<>();
        if(currentRound == null){
            feedback.put("message", "Game over, This game has no rounds");
        } else{
            feedback = currentRound.IsCorrect(currentTry);
            if(feedback.get("message") != null) return feedback;

            if(feedback.get("lettersWrong").equals("0")) {
                int nextWordLength = 0;
                if (feedback.get("lettersCorrect").equals("5")) nextWordLength = 6;
                if (feedback.get("lettersCorrect").equals("6")) nextWordLength = 7;
                if (feedback.get("lettersCorrect").equals("7")) nextWordLength = 5;

                String word = newRound(nextWordLength);
                feedback.replace("Letters in word", Integer.toString(word.length()));
                feedback.replace("Tries left", "5");
                feedback.put("message", "Attempt was correct, new round has started");
            } else{
                feedback.put("message", "This was not the right word. Try again");
            }
        }
        return feedback;
    }

    @Override
    public int endGame(String name) {
        if(getLastRound().isActive() || this.userName != null){
            return 0;
        } else{
            gameDao.update(this.id, name);
            return gameDao.getScore(this.id);
        }
    }

    @Override
    public int startGame() {
        this.id = gameDao.newGame();
        if(newRound(5) == null){
            return 0;
        }
        return id;
    }

    private String newRound(int length){
        String word = wordService.pickwordForGame(length);
        Round round = new Round(0, word, null);
        round.save(this.id);
        return word;
    }

    @Override
    public int getHighscore(){
        return gameDao.getHighscore(userName);
    }
}
