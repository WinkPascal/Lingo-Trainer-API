package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.*;
import nl.hu.lingo.Import.Application.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RoundTest {
    private TryDao tryDaoMock;
    private  WordService wordServiceMock;
    private RoundDao roundDaoMock;

    @BeforeEach
    void beforeEach(){
        wordServiceMock = mock(WordService.class);
        tryDaoMock = mock(TryPostgressDao.class);
        roundDaoMock = mock(RoundPostgressDao.class);
    }

    //=====================================================================================================================
    //====================================================== IsCorrect ====================================================
    //=====================================================================================================================

    static Stream<Arguments> IsCorrect_set() {
        return Stream.of(
                // 1. Check if there are already five tries made
                Arguments.of(5, "zweep", "Game over, because you already did 5 tries in this round. call endGame method to save name."),
                Arguments.of(5, "fiets", "Game over, because you already did 5 tries in this round. call endGame method to save name."),
                // 3. check spelling constraints
                Arguments.of(3, "fiegs", "Spelling was not correct."),
                // 5. check if last try was incorrect
                Arguments.of(4, "zweep", "This was your fifth try. Game over, call endGame method to save name."),
                Arguments.of(4, "fiets", null)
        );
    }

    @ParameterizedTest
    @MethodSource("IsCorrect_set")
    void IsCorrect(int amountPreviousTries, String word, String message){
        List<String> words = new ArrayList<>();
        words.add("fiets");
        words.add("zweep");
        when(wordServiceMock.getAllWordsWithLength(anyInt()))
                .thenReturn(words);

        List<Try> tries = new ArrayList<>();
        for (int i = 0; i < amountPreviousTries; i++) {
            tries.add(new Try(0, "zweep",null, wordServiceMock, tryDaoMock));
        }

        Round round = new Round(28, "fiets", tries, roundDaoMock);
        Try try_  = new Try(0, word, null, wordServiceMock, tryDaoMock);

        Map<String, String> response = round.IsCorrect(try_);
        assertEquals(response.get("message") , message);
    }

    //=====================================================================================================================
    //======================================================= isActive ====================================================
    //=====================================================================================================================

    static Stream<Arguments> isActive_set() {
        return Stream.of(
                // 1. Check if there are already five tries made
                Arguments.of(0, true),
                Arguments.of(1, true),
                Arguments.of(2, true),
                Arguments.of(3, true),
                Arguments.of(4, true),
                Arguments.of(5, false),
                Arguments.of(6, false),
                Arguments.of(7, false)
        );
    }
    @ParameterizedTest
    @MethodSource("isActive_set")
    void isActive(int amountPreviousTries, boolean expectation){
        List<Try> tries = new ArrayList<>();
        for (int i = 0; i < amountPreviousTries; i++) {
            tries.add(new Try(0, "zweep",null, wordServiceMock, tryDaoMock));
        }
        Round round = new Round(0, "test", tries, roundDaoMock);

        assertEquals(round.isActive(), expectation);
    }

    //=====================================================================================================================
    //======================================================= getId =======================================================
    //=====================================================================================================================

    @Test
    void getId(){
        int id = 10;
        Round round = new Round(id, "test", null, roundDaoMock);
        assertEquals(round.getId(), id);
    }
}
