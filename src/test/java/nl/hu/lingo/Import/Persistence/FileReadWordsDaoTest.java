package nl.hu.lingo.Import.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FileReadWordsDaoTest {

    private PostgressWordsDao postgressWordsDao;
    private ReadDao fileReadDao;

    @BeforeEach
    void beforeEach(){
        this.postgressWordsDao = new PostgressWordsDao(new DatabasePostgress());
        this.fileReadDao = new FileReadDao();
    }

    static Stream<Arguments> readWordsFilterByWordLength_lengths() {
        return Stream.of(
                // capital letters
                Arguments.of(0, false),
                Arguments.of(1, false),
                Arguments.of(4, false),
                Arguments.of(5, true),
                Arguments.of(6, true),
                Arguments.of(7, true),
                Arguments.of(8, false)
        );
    }
    @ParameterizedTest
    @MethodSource("readWordsFilterByWordLength_lengths")
    void readWordsFilterByWordLength_lengthsAccepted(int length, boolean expectation){
        List<String> words = postgressWordsDao.readWordsFilterByWordLength(length);
        if(words == null){
            assertEquals(false, expectation);
        } else{
            boolean allWordsHaveRightLength = false;

            for (String word : words) {
                if(word.length() != length){
                    allWordsHaveRightLength = false;
                    break;
                }
                allWordsHaveRightLength = true;
            }

            assertEquals(allWordsHaveRightLength, expectation);
        }
    }
    @Test
    void getAllWordsTest(){
        List<String> words = fileReadDao.getAllWords();
        assertTrue(words.size() > 0);
    }
}