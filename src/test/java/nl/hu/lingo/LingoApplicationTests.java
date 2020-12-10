package nl.hu.lingo;

import nl.hu.lingo.Game.Domain.GameDao;
import nl.hu.lingo.Game.Persistence.DataBasePostgress;
import nl.hu.lingo.Game.Persistence.Database;
import nl.hu.lingo.Game.Persistence.GamePostgressDaoImpl;
import nl.hu.lingo.Import.Application.WordService;
import nl.hu.lingo.Import.Application.WordServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class LingoApplicationTests {

	@Test
	void contextLoads() {
		WordServiceInterface wordService = new WordService();
		String word = wordService.pickwordForGame(5);
		int s = word.length();
	//	Assert.isTrue(s == 5);
	}

	@Test
	void testDatabase(){
		Database database = new DataBasePostgress();

		GameDao gameRepo = new GamePostgressDaoImpl(database);

		int id = gameRepo.saveGame();
		Assert.isTrue(id == 1);
	}

	@Test
	void saveRound(){
		Database database = new DataBasePostgress();

		GameDao gameRepo = new GamePostgressDaoImpl(database);
		gameRepo.saveRound("test", 1);
	}

	@Test
	void saveTry(){
		Database database = new DataBasePostgress();

		GameDao gameRepo = new GamePostgressDaoImpl(database);
		gameRepo.saveTry(1, "tsas");
	}
}
