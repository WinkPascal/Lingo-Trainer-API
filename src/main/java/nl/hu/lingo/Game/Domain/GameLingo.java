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

    @Override
    public Map<String, String> nextMove(Try currentTry) {
        Round currentRound = null;
        for (Round round : rounds) {
            if(round.isActive()){
                currentRound = round;
            }
        }

        Map<String, String> feedback = new HashMap<>();
        if(currentRound == null){
            feedback.put("status", "Game over, call endGame method to save name.");
        } else{
            feedback = currentRound.IsCorrect(currentTry);
            if(feedback.get("lettersWrong") == "0"){
                int nextWordLength= 0;
                if(feedback.get("lettersCorrect") == "5") nextWordLength = 6;
                if(feedback.get("lettersCorrect") == "6") nextWordLength = 7;
                if(feedback.get("lettersCorrect") == "7") nextWordLength = 5;

                String word = newRound(nextWordLength);
                feedback.replace("Letters in word", Integer.toString(word.length()));
                feedback.replace("Tries left", Integer.toString(nextWordLength));
            }
        }
        return feedback;
    }

    @Override
    public int endGame() {
        return 0;
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
