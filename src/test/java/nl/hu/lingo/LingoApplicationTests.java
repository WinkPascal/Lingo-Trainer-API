package nl.hu.lingo;

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
		Assert.isTrue(s == 5);
	}
}
