package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Import.Application.WordService;
import nl.hu.lingo.Import.Application.WordServiceInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLingo implements Game {
    private int id;
    private String userName;
    private List<Round> rounds;
    private GameDao gameDao;
    private WordServiceInterface wordService;

    public GameLingo(int id, String userName, List<Round> rounds, GameDao gameDao, WordServiceInterface wordService){
        this.id = id;
        this.userName = userName;
        this.rounds = rounds;
        this.gameDao = gameDao;
        this.wordService = wordService;
    }

    @Override
    public List<Round> getRounds() {
        return null;
    }

    private Round getLastRound(){
        Round lastRound = null;
        int biggestId = 0;
        for (Round round : rounds) {
            int roundId = round.getId();
            if (biggestId < roundId) {
                lastRound = round;
            }
        }
        return lastRound;
    }

    @Override
    public Map<String, String> nextMove(Try currentTry) {
        Round currentRound = getLastRound();

        Map<String, String> feedback = new HashMap<>();
        if(currentRound == null){
            feedback.put("status", "Game over, call endGame method to save name.");
        } else{
            feedback = currentRound.IsCorrect(currentTry);
            if(feedback.get("invalid").equals("true")){
                return feedback;
            } else{
                if(feedback.get("lettersWrong").equals("0")) {
                    int nextWordLength = 0;
                    if (feedback.get("lettersCorrect").equals("5")) nextWordLength = 6;
                    if (feedback.get("lettersCorrect").equals("6")) nextWordLength = 7;
                    if (feedback.get("lettersCorrect").equals("7")) nextWordLength = 5;

                    String word = newRound(nextWordLength);
                    feedback.replace("Letters in word", Integer.toString(word.length()));
                    feedback.replace("Tries left", "5");
                }
            }
        }
        return feedback;
    }

    @Override
    public int endGame(String name) {
        Round lastRound = getLastRound();
        if(!lastRound.isActive()){
            return 0;
        } else{
            gameDao.saveName(this.id, name);
            return gameDao.getScore(this.id);
        }
    }

    @Override
    public int startGame() {
        this.id = gameDao.saveGame();
        if(newRound(5) == null){
            return 0;
        }
        return id;
    }

    private String newRound(int length){
        String word = wordService.pickwordForGame(length);
        gameDao.saveRound(word, id);
        return word;
    }
}
