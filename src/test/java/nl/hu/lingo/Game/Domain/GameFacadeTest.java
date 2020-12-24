package nl.hu.lingo.Game.Domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GameFacadeTest {
    @Test
    void getId(){
        int id = 10;
        Round round = new Round(id, "test", null);
        assertEquals(round.getId(), id);
    }
}
