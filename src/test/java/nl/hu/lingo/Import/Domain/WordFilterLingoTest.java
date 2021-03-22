package nl.hu.lingo.Import.Domain;

import nl.hu.lingo.Import.Persistence.DatabasePostgress;
import nl.hu.lingo.Import.Persistence.FileReadDao;
import nl.hu.lingo.Import.Persistence.PostgressWordsDao;
import nl.hu.lingo.Import.Persistence.WordsDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openjdk.jmh.annotations.*;
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
        WordsDao wordDaoMock = mock(WordsDao.class);
        List<String> words = new ArrayList<>();
        words.add("fiets");
        words.add("fiets");
        words.add("fiets");
        when(wordDaoMock.readWordsFilterByWordLength(anyInt()))
                .thenReturn(words);
        FileReadDao fileReadDao = mock(FileReadDao.class);

        WordFilter wordFilter = new WordFilterLingo(wordDaoMock, fileReadDao, length);
        String word = wordFilter.pickwordForGame();

        assertEquals(isNull, word == null);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 1)
    public void pickwordForGame_benchmark() {
        PostgressWordsDao postgressWordsDao = new PostgressWordsDao(new DatabasePostgress());
        FileReadDao fileReadDao = new FileReadDao();

        WordFilter wordFilter = new WordFilterLingo(postgressWordsDao, fileReadDao, 5);
        wordFilter.pickwordForGame();
    }

    @ParameterizedTest
    @MethodSource("word_Test_lengths")
    void getAllWordsWithLength_Test(int length, boolean isNull){
        PostgressWordsDao postgressWordsDaoMock = mock(PostgressWordsDao.class);
        FileReadDao fileReadDaoMock = mock(FileReadDao.class);

        List<String> wordsFiveLetters = new ArrayList<>();
        wordsFiveLetters.add("braam");
        wordsFiveLetters.add("fiets");
        wordsFiveLetters.add("stoom");
        when(postgressWordsDaoMock.readWordsFilterByWordLength(anyInt()))
                .thenReturn(wordsFiveLetters);

        WordFilter wordFilter = new WordFilterLingo(postgressWordsDaoMock, fileReadDaoMock, length);

        List<String> words = wordFilter.getAllWordsWithLength();

        assertEquals(isNull, words == null);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 1)
    public void getAllWordsWithLength_benchmark() {
        PostgressWordsDao postgressWordsDaoMock = mock(PostgressWordsDao.class);
        FileReadDao fileReadDaoMock = mock(FileReadDao.class);

        List<String> wordsFiveLetters = new ArrayList<>();
        wordsFiveLetters.add("braam");
        wordsFiveLetters.add("fiets");
        wordsFiveLetters.add("stoom");
        when(postgressWordsDaoMock.readWordsFilterByWordLength(anyInt()))
                .thenReturn(wordsFiveLetters);

        WordFilter wordFilter = new WordFilterLingo(postgressWordsDaoMock, fileReadDaoMock, 5);

        wordFilter.getAllWordsWithLength();
    }
}
