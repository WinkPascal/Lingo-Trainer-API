package nl.hu.lingo;

import nl.hu.lingo.Domain.Game;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LingoApplicationTests {

	@Test
	void contextLoads() {
		Game game = new Game();
		game.filterWords(7);
	}

}
