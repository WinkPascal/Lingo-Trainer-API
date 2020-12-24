package nl.hu.lingo.Import.Domain;

import nl.hu.lingo.Import.Persistence.DatabasePostgress;
import nl.hu.lingo.Import.Persistence.FileReadDao;
import nl.hu.lingo.Import.Persistence.PostgressWordsDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WordFilterLingoTest {

    @BeforeEach
    void beforeEach(){

        List<String> wordsFiveLetters = new ArrayList<>();
        wordsFiveLetters.add("braam");
        wordsFiveLetters.add("fiets");
        wordsFiveLetters.add("stoom");

    }

    static Stream<Arguments> word_Test_lengths() {
        return Stream.of(
                Arguments.of(-1, true),
                Arguments.of(0, true),
                Arguments.of(1, true),
                Arguments.of(4, true),
                Arguments.of(5, false),
                Arguments.of(6, false),
                Arguments.of(7, false),
                Arguments.of(8, true)
        );
    }

    @ParameterizedTest
    @MethodSource("word_Test_lengths")
    void pickwordForGame_Test(int length, boolean isNull){
        WordFilter wordFilter = new WordFilterLingo(length);
        String word = wordFilter.pickwordForGame();

        assertEquals(isNull, word == null);
    }

    @ParameterizedTest
    @MethodSource("word_Test_lengths")
    void getAllWordsWithLength_Test(int length, boolean isNull){
        WordFilter wordFilter = new WordFilterLingo(length);
        List<String> words = wordFilter.getAllWordsWithLength();

        assertEquals(isNull, words == null);
    }

}
