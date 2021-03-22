package nl.hu.lingo.Game.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.lingo.Game.Domain.*;
import org.openjdk.jmh.annotations.State;

import java.util.Map;

public class GameController {
    GameFacadeLingo gameFacade;

    public GameController(GameFacadeLingo gameFacade) {
        this.gameFacade = gameFacade;
    }

    public int startGame(){
        return gameFacade.startGame();
    }

    public String nextMove(int gameId, String word) {
        Map<String, String> feedback = gameFacade.nextMove(gameId, word);

        //convert to json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(feedback);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public int gameFinished(String id, String username) {
        if(id.equals("") || username.equals("")) return 0;
        return gameFacade.endGame(Integer.parseInt(id), username);
    }

    public int getHighscore(String username) {
        return gameFacade.getHighscore(username);
    }
}