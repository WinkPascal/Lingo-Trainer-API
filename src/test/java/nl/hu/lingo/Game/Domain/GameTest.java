package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.*;
import nl.hu.lingo.Import.Application.WordService;
import nl.hu.lingo.Import.Application.WordServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameTest {

    private GameDao gameDao;
    private WordServiceInterface wordService;
    private RoundDao roundDao;
    @BeforeEach
    void beforeEach(){
        this.gameDao = new GamePostgressDaoImpl(new DataBasePostgress());
        this.wordService = new WordService();
        this.roundDao = new RoundPostgressDao(new DataBasePostgress());
    }
    @Test
    void nextMove(){
        Game game = new GameLingo(0, null, null, gameDao, wordService, roundDao);
    }
}
