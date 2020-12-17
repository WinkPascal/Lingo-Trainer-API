package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.*;
import nl.hu.lingo.Import.Application.WordServiceInterface;
import java.util.Map;

public class GameFacadeLingo {
    private GameDao gameDao;
    private WordServiceInterface wordService;
    private TryDao tryDao;

    public GameFacadeLingo(GameDao gameDao, WordServiceInterface wordService, TryDao tryDao){
        this.gameDao = gameDao;
        this.wordService = wordService;
        this.tryDao = tryDao;
    }

    public int startGame() {
        RoundDao roundDao = new RoundPostgressDao(new DataBasePostgress());
        Game game = new GameLingo(0, null, null, gameDao, wordService,roundDao);
        int id = game.startGame();
        return id;
    }

    public Map<String, String> nextMove(int gameId, String word) {
        Game game = gameDao.getGameById(gameId, wordService);
        Try currentTry = new Try(0, word, wordService, tryDao);

        return game.nextMove(currentTry);
    }

    public int endGame(int id, String name) {
        Game game = gameDao.getGameById(id, wordService);
        return game.endGame(name);
    }

    public int getHighscore(String username){
        return Game.getHighscore(username, gameDao);
    }
}

