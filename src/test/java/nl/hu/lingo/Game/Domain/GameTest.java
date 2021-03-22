package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.*;
import nl.hu.lingo.Import.Service.WordService;
import nl.hu.lingo.Import.Service.WordServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GameTest {
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

    @Test
    void nextMove_no_rounds(){
        List<Round> rounds = new ArrayList<>();
        Game game = new GameLingo(1, null, rounds, gameDaoMock, wordServiceMock, roundDaoMock);
        TryLingo attempt = new TryLingo(1, "fiets",null, wordServiceMock, tryDaoMock);

        Map<String, String> response = game.nextMove(attempt);

        assertEquals("Game over, call endGame method to save name.", response.get("message"));
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
        RoundLingo roundMock = mock(RoundLingo.class);
        rounds.add(roundMock);
        when(roundMock.IsCorrect(anyObject()))
                .thenReturn(response);
        when(roundMock.getId())
                .thenReturn(1);

        Game game = new GameLingo(1, null, rounds, gameDaoMock, wordServiceMock, roundDaoMock);
        Map<String, String> feedback = game.nextMove(new TryLingo(0, "",null, wordServiceMock, tryDaoMock));

        assertEquals(feedback.get("message"), "Attempt was correct, new round has started");
    }

    @Test
    void endGame_round_active(){
        List<Round> rounds = new ArrayList<>();
        RoundLingo roundMock = mock(RoundLingo.class);
        rounds.add(roundMock);
        when(roundMock.isActive())
                .thenReturn(true);
        when(roundMock.getId())
                .thenReturn(1);

        Game game = new GameLingo(1, null, rounds, gameDaoMock, wordServiceMock, roundDaoMock);

        assertEquals(game.endGame("pascal"), 0);
    }

    @Test
    void endGame_has_username() {
        List<Round> rounds = new ArrayList<>();
        RoundLingo roundMock = mock(RoundLingo.class);
        rounds.add(roundMock);
        when(roundMock.isActive())
                .thenReturn(false);
        when(roundMock.getId())
                .thenReturn(1);

        Game game = new GameLingo(1, "Pascal", rounds, gameDaoMock, wordServiceMock, roundDaoMock);

        assertEquals(game.endGame("pascal"), 0);
    }

    @Test
    void endGame_has_username_and_is_active() {
        List<Round> rounds = new ArrayList<>();
        RoundLingo roundMock = mock(RoundLingo.class);
        rounds.add(roundMock);
        when(roundMock.isActive())
                .thenReturn(true);
        when(roundMock.getId())
                .thenReturn(1);

        Game game = new GameLingo(1, "Pascal", rounds, gameDaoMock, wordServiceMock, roundDaoMock);

        assertEquals(game.endGame("pascal"), 0);
    }

    @Test
    void endGame() {
        List<Round> rounds = new ArrayList<>();
        RoundLingo roundMock = mock(RoundLingo.class);
        rounds.add(roundMock);
        when(roundMock.isActive())
                .thenReturn(false);
        when(roundMock.getId())
                .thenReturn(1);
        int score = 5;
        when(gameDaoMock.getScore(anyInt()))
                .thenReturn(score);

        Game game = new GameLingo(1, null, rounds, gameDaoMock, wordServiceMock, roundDaoMock);

        assertEquals(game.endGame("pascal"), score);
    }

    @Test
    void startGame() {
        List<Round> rounds = new ArrayList<>();
        RoundLingo roundMock = mock(RoundLingo.class);
        rounds.add(roundMock);
        int newGameId = 5132;
        when(gameDaoMock.newGame())
                .thenReturn(newGameId);
        when(roundMock.getId())
                .thenReturn(1);
        when(wordServiceMock.pickwordForGame(anyInt()))
                .thenReturn("fiets");

        Game game = new GameLingo(1, null, rounds, gameDaoMock, wordServiceMock, roundDaoMock);

        assertEquals(newGameId, game.startGame());
    }
}
