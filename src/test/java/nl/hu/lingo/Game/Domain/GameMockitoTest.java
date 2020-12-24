package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.*;
import nl.hu.lingo.Import.Application.WordService;
import nl.hu.lingo.Import.Application.WordServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameMockitoTest {

    private GameDao gameDaoMock;
    private WordServiceInterface wordServiceMock;
    private TryDao tryDaoMock;
    private RoundDao roundDaoMock;
    @BeforeEach
    void beforeEach(){
        gameDaoMock = mock(GamePostgressDaoImpl.class);

        wordServiceMock = mock(WordService.class);
        roundDaoMock = mock(RoundPostgressDao.class);

        tryDaoMock = mock(TryPostgressDao.class);
    }
    //=====================================================================================================================
    //====================================================== nextMove =====================================================
    //=====================================================================================================================

    @Test
    void nextMove_no_rounds(){
        Game game = new GameLingo(0, null, null);
        Try attempt = new Try(1, "fiets",null);

        Map<String, String> response = game.nextMove(attempt);

        assertEquals(response.get("message"), "Game over, This game has no rounds");
    }

    static Stream<Arguments> nextMove_check_word_spelling_Test_words() {
        return Stream.of(
                Arguments.of(0, 6),
                Arguments.of(1, 7),
                Arguments.of(2, 5)
        );
    }
    @ParameterizedTest
    @MethodSource("nextMove_check_word_spelling_Test_words")
    void nextMove_correct_word(int roundsWonBefore, int nextWordLengthExpectation){
        when(wordServiceMock.pickwordForGame(5))
                .thenReturn("fiets");
        when(wordServiceMock.pickwordForGame(6))
                .thenReturn("fietss");
        when(wordServiceMock.pickwordForGame(7))
                .thenReturn("fietssd");

        Map<String, String> response = new HashMap<>();
        if(roundsWonBefore % 1 == 0) {
            response.put("lettersCorrect", "6");
            response.put("Letters in word", "0");
            response.put("Tries left", "0");
        } else if(roundsWonBefore % 2 == 0) {
            response.put("lettersCorrect", "7");
            response.put("Letters in word", "0");
            response.put("Tries left", "0");
        } else if(roundsWonBefore % 3 == 0) {
            response.put("lettersCorrect", "5");
            response.put("Letters in word", "0");
            response.put("Tries left", "0");
        }
        response.put("lettersWrong", "0");

        List<Round> rounds = new ArrayList<>();
        Round roundMock = mock(Round.class);
        rounds.add(roundMock);
        when(roundMock.IsCorrect(anyObject()))
                .thenReturn(response);
        when(roundMock.getId())
                .thenReturn(1);

        Game game = new GameLingo(0, null, rounds);
        Map<String, String> feedback = game.nextMove(new Try(0, "",null));

        assertEquals(feedback.get("Letters in word"), Integer.toString(nextWordLengthExpectation));
        assertEquals(feedback.get("message"), "Attempt was correct, new round has started");
    }

    //=====================================================================================================================
    //====================================================== endGame ======================================================
    //=====================================================================================================================

    @Test
    void endGame_round_active(){
        List<Round> rounds = new ArrayList<>();
        Round roundMock = mock(Round.class);
        rounds.add(roundMock);
        when(roundMock.isActive())
                .thenReturn(true);
        when(roundMock.getId())
                .thenReturn(1);

        Game game = new GameLingo(1, null, rounds);

        assertEquals(game.endGame("pascal"), 0);
    }

    @Test
    void endGame_has_username() {
        List<Round> rounds = new ArrayList<>();
        Round roundMock = mock(Round.class);
        rounds.add(roundMock);
        when(roundMock.isActive())
                .thenReturn(false);
        when(roundMock.getId())
                .thenReturn(1);

        Game game = new GameLingo(1, "pascal", rounds);

        assertEquals(game.endGame("pascal"), 0);
    }

    @Test
    void endGame_has_username_and_is_active() {
        List<Round> rounds = new ArrayList<>();
        Round roundMock = mock(Round.class);
        rounds.add(roundMock);
        when(roundMock.isActive())
                .thenReturn(true);
        when(roundMock.getId())
                .thenReturn(1);

        Game game = new GameLingo(1, "pascal", rounds);

        assertEquals(game.endGame("pascal"), 0);
    }

    @Test
    void endGame() {
        List<Round> rounds = new ArrayList<>();
        Round roundMock = mock(Round.class);
        rounds.add(roundMock);
        when(roundMock.isActive())
                .thenReturn(false);
        when(roundMock.getId())
                .thenReturn(1);
        int score = 5;
        when(gameDaoMock.getScore(anyInt()))
                .thenReturn(score);

        Game game = new GameLingo(1, null, rounds);

        assertEquals(game.endGame("pascal"), score);
    }

    //=====================================================================================================================
    //===================================================== startGame =====================================================
    //=====================================================================================================================

    @Test
    void startGame() {
        List<Round> rounds = new ArrayList<>();
        Round roundMock = mock(Round.class);
        rounds.add(roundMock);
        int newGameId = 5132;
        when(gameDaoMock.newGame())
                .thenReturn(newGameId);
        when(wordServiceMock.pickwordForGame(anyInt()))
                .thenReturn("fiets");

        Game game = new GameLingo(0, null, rounds);

        assertEquals(game.endGame("pascal"), newGameId);
    }
}