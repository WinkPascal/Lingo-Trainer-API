package nl.hu.lingo.Game.Domain;

import java.util.List;

public class GameFacadeLingo {
    private GameRepository gameRepository;

    public GameFacadeLingo(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    public int startGame() {
        return gameRepository.saveGame();
    }

    public List<String> nextMove(int gameId, String word) {
        Game game = gameRepository.getGameById(gameId);
        Try currentTry = new Try(0, word);
        game.nextMove(currentTry);
        return null;
    }

    public int gameFinished(int id, String name) {
        return 0;
    }
}

