package nl.hu.lingo.Game.Persistence;

import nl.hu.lingo.Game.Domain.Game;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GameDaoTest {
    @Test
    void getGameById(){
        GameDao gameDao = new GamePostgressDaoImpl(new DataBasePostgress());

        Game game = gameDao.getGameById(1);

        assertTrue(game.getClass() != null);
    }

    @Test
    void newGame(){
        GameDao gameDao = new GamePostgressDaoImpl(new DataBasePostgress());

        int newGameId = gameDao.newGame();

        assertTrue(newGameId > 0);
    }
    @Test
    void getScore_non_existing_game(){
        GameDao gameDao = new GamePostgressDaoImpl(new DataBasePostgress());

        int score = gameDao.getScore(0);

        assertTrue(score == 0);
    }
    @Test
    void getScore_existing_game(){
        GameDao gameDao = new GamePostgressDaoImpl(new DataBasePostgress());

        int score = gameDao.getScore(1);

        assertTrue(score == 10);
    }
    @Test
    void getHighscore_existing_username(){
        GameDao gameDao = new GamePostgressDaoImpl(new DataBasePostgress());

        int score = gameDao.getHighscore("Pascal");

        assertTrue(score == 5);
    }
    @Test
    void getHighscore_non_existing_username(){
        GameDao gameDao = new GamePostgressDaoImpl(new DataBasePostgress());

        int score = gameDao.getHighscore("dasdfafgsa");

        assertTrue(score == 0);
    }
}
