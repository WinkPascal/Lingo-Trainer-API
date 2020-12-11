package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Import.Application.WordService;
import nl.hu.lingo.Import.Application.WordServiceInterface;

import java.util.List;
import java.util.Map;

public class GameFacadeLingo {
    private GameDao gameRepository;
    private WordServiceInterface wordService;

    public GameFacadeLingo(GameDao gameRepository, WordServiceInterface wordService){
        this.gameRepository = gameRepository;
        this.wordService = wordService;
    }

    public int startGame() {
        Game game = new GameLingo(0, null, null, gameRepository, wordService);
        int id = game.startGame();
        return id;
    }

    public Map<String, String> nextMove(int gameId, String word) {
        Game game = gameRepository.getGameById(gameId);
        Try currentTry = new Try(0, word, wordService);

        return game.nextMove(currentTry);
    }

    public int gameFinished(int id, String name) {
        return 0;
    }
}

