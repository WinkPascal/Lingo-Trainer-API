package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GameFacadeLingo {
    @Autowired
    private GameDao gamePostgressDaoImpl;

    public int startGame() {
        Game game = new GameLingo(0, null, null);
        int id = game.startGame();
        return id;
    }

    public Map<String, String> nextMove(int gameId, String word) {
        Game game = gamePostgressDaoImpl.getGameById(gameId);
        Try currentTry = new Try(0, word, null);

        return game.nextMove(currentTry);
    }

    public int endGame(int id, String name) {
        Game game = gamePostgressDaoImpl.getGameById(id);
        return game.endGame(name);
    }

    public int getHighscore(String username){
        Game game = new GameLingo(0, username, null);
        return game.getHighscore();
    }
}

