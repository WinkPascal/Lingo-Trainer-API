package nl.hu.lingo.Import.Controller;

import nl.hu.lingo.Import.Persistence.FileReadDao;
import nl.hu.lingo.Import.Persistence.WordsDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WordControllerTest {

    private WordsDao wordDaoMock;
    private FileReadDao fileReadDao;

    @BeforeEach
    void beforeEach() {
        wordDaoMock = mock(WordsDao.class);
        fileReadDao = mock(FileReadDao.class);
    }

    @Test
    void pickWordForGameSupportedLengthTest() {
        List<String> words = new ArrayList<>();
        words.add("fiets");
        words.add("fiets");
        words.add("fiets");
        when(wordDaoMock.readWordsFilterByWordLength(anyInt()))
            .thenReturn(words);

        WordController wordController = new WordController(wordDaoMock, fileReadDao);
        String word = wordController.pickwordForGame(5);
        boolean correctLength = false;
        if(word.length() == 5){
            correctLength = true;
        }

        assertTrue(correctLength);
    }

    static Stream<Arguments> provideUnSupportedLengths() {
        return Stream.of(
                Arguments.of(0, true),
                Arguments.of(1, true),
                Arguments.of(4, true),
                Arguments.of(8, true),
                Arguments.of(10, true),
                Arguments.of(89571, true)
        );
    }

    @ParameterizedTest
    @MethodSource("provideUnSupportedLengths")
    void pickWordForGameUnsupportedLengthTest(int length, boolean expectation) {
        WordController wordController = new WordController(wordDaoMock, fileReadDao);

        String word = wordController.pickwordForGame(length);
        assertEquals(expectation, word == null);
    }

    static Stream<Arguments> provideSupportedLengths() {
        return Stream.of(
                Arguments.of(5, true),
                Arguments.of(6, true),
                Arguments.of(7, true)
        );
    }

    @ParameterizedTest
    @MethodSource("provideSupportedLengths")
    void GetAllWordsWithSupportedLengthTest(int length, boolean expectation) {
        WordController wordController = new WordController(wordDaoMock, fileReadDao);

        boolean correctLength = true;
        List<String> words = wordController.getAllWordsWithLength(length);
        if(words == null) {
            correctLength = false;
        } else{
            for (String word : words) {
                if (word.length() != length) {
                    correctLength = false;
                }
            }
        }
        assertEquals(expectation, correctLength);
    }


    static Stream<Arguments> provideUnSupportedLengths_GetAllWordsWithUnSupportedLengthTest() {
        return Stream.of(
                Arguments.of(0, null),
                Arguments.of(1, null),
                Arguments.of(3, null),
                Arguments.of(8, null),
                Arguments.of(10, null),
                Arguments.of(89571, null)
        );
    }

    @ParameterizedTest
    @MethodSource("provideUnSupportedLengths_GetAllWordsWithUnSupportedLengthTest")
    void GetAllWordsWithUnSupportedLengthTest(int length, List<String> expectation) {
        WordController wordController = new WordController(wordDaoMock, fileReadDao);

        List<String> words = wordController.getAllWordsWithLength(length);

        assertSame(expectation, words);
    }
}
