package nl.hu.lingo.Import.Application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class WordServiceTest {

    private WordServiceInterface wordService;

    @BeforeEach
    void beforeEach() {
        wordService = new WordService();
    }

    static Stream<Arguments> pickWordForGameSupportedLengthTest_words() {
        return Stream.of(
                Arguments.of(5, true),
                Arguments.of(6, true),
                Arguments.of(7, true)
        );
    }

    @ParameterizedTest
    @MethodSource("pickWordForGameSupportedLengthTest_words")
    void pickWordForGameSupportedLengthTest(int length, boolean lengthIsCorrect) {
        String word = wordService.pickwordForGame(length);
        boolean correctLength = false;
        if(word.length() == length){
            correctLength = true;
        }
        assertEquals(correctLength, lengthIsCorrect);

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
        String word = wordService.pickwordForGame(length);
        assertEquals(expectation, word == null);
    }

    //==============================================================================================================
    //==============================================================================================================
    //==============================================================================================================
    //==============================================================================================================
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
        boolean correctLength = true;
        List<String> words = wordService.getAllWordsWithLength(length);
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
        List<String> words = wordService.getAllWordsWithLength(length);

        assertTrue(expectation == words);
    }
}
