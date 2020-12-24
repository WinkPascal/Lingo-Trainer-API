package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.GameDao;
import nl.hu.lingo.Game.Persistence.RoundDao;
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
    private RoundDao roundDao;

    public GameLingo(int id, String userName, List<Round> rounds, GameDao gameDao, WordServiceInterface wordService, RoundDao roundDao){
        this.id = id;
        this.userName = userName;
        this.rounds = rounds;
        this.gameDao = gameDao;
        this.wordService = wordService;
        this.roundDao = roundDao;
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
            feedback.put("message", "Game over, call endGame method to save name.");
            feedback.replace("triesleft", "0");
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
                feedback.replace("triesleft", "5");
                feedback.put("message", "Attempt was correct, new round has started");
            } else{
                feedback.put("message", "This was not the right word.");
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
        Round round = new Round(0, word, null, roundDao);
        round.save(this.id);
        return word;
    }
}
