package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.*;
import nl.hu.lingo.Import.Service.WordServiceInterface;
import java.util.Map;

public class GameFacadeLingo {
    private GameDao gameDao;
    private WordServiceInterface wordService;
    private TryDao tryDao;
    private RoundDao roundDao;

    public GameFacadeLingo(GameDao gameDao, WordServiceInterface wordService, TryDao tryDao, RoundDao roundDao){
        this.gameDao = gameDao;
        this.wordService = wordService;
        this.tryDao = tryDao;
        this.roundDao = roundDao;
    }

    public int startGame() {
        Game game = new GameLingo(0, null, null, gameDao, wordService, roundDao);
        return game.startGame();
    }

    public Map<String, String> nextMove(int gameId, String word) {
        Game game = gameDao.getGameById(gameId);
        if(game == null) return null;

        TryLingo currentTry = new TryLingo(0, word, null, wordService, tryDao);
        return game.nextMove(currentTry);
    }

    public int endGame(int id, String name) {
        Game game = gameDao.getGameById(id);
        return game.endGame(name);
    }

    public int getHighscore(String username){
        return Game.getHighscore(username, gameDao);
    }
}

