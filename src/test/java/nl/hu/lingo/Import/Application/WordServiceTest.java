package nl.hu.lingo.Import.Application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class WordServiceTest {

    @Test
    void pickWordForGameAvailableLengthTest() {
        int wordLengthFive = 5;
        int wordLengthSix = 6;
        int wordLengthSeven = 7;
        WordServiceInterface wordService = new WordService();
        String word = wordService.pickwordForGame(wordLengthFive);

        Assert.isTrue(word.length() == wordLengthFive);

        String word2 = wordService.pickwordForGame(wordLengthSix);
        Assert.isTrue(word2.length() == wordLengthSix);

        String word3 = wordService.pickwordForGame(wordLengthSeven);
        Assert.isTrue(word3.length() == wordLengthSeven);
    }

    @Test
    void pickWordForGameZeroLengthTest() {
        WordServiceInterface wordService = new WordService();
        String word = wordService.pickwordForGame(0);

        Assert.isTrue(word.equals(null));
    }

    @Test
    void pickWordForGameUnsupportedLengthTest() {
        WordServiceInterface wordService = new WordService();

        String word = wordService.pickwordForGame(23);
        Assert.isTrue(word.equals(null));

        word = wordService.pickwordForGame(1);
        Assert.isTrue(word.equals(null));

       word = wordService.pickwordForGame(231251);
        Assert.isTrue(word.equals(null));

         word = wordService.pickwordForGame(1);
        Assert.isTrue(word.equals(null));

         word = wordService.pickwordForGame(635123);
        Assert.isTrue(word.equals(null));

         word = wordService.pickwordForGame(4216);
        Assert.isTrue(word.equals(null));
    }


    @Test
    void GetAllWordsWithCorrectLengthTest() {
        WordServiceInterface wordService = new WordService();
        List<String> words = wordService.getAllWordsWithLength(5);
        Assert.isTrue(words.size() > 10);
    }


}
