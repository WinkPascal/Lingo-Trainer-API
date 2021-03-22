package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.*;
import nl.hu.lingo.Import.Service.WordService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class GameFacadeTest {
    private WordService wordServiceMock;
    private GameDao gameDaoMock;
    private TryDao tryDaoMock;
    private GameFacadeLingo gameFacade;
    private Game game;
    private RoundDao roundDaoMock;

    @BeforeEach
    void beforeEach(){
        roundDaoMock = mock(RoundPostgressDao.class);
        wordServiceMock = mock(WordService.class);
        List<String> words = new ArrayList<>();
        words.add("fiets");
        words.add("chauffeur");
        words.add("kaas");
        words.add("vis");
        words.add("ik");
        words.add("Elektriciteitsproductiemaatschappij");
        words.add("cavia");
        when(wordServiceMock.getAllWordsWithLength(5))
                .thenReturn(words);

        when(wordServiceMock.pickwordForGame(6))
                .thenReturn("letter");

        gameDaoMock = mock(GamePostgressDaoImpl.class);
        List<Round> rounds = new ArrayList<>();
        rounds.add(new RoundLingo(1, "fiets", new ArrayList<>(), roundDaoMock));
        game = new GameLingo(1, null, rounds, gameDaoMock, wordServiceMock, roundDaoMock);
        when(gameDaoMock.getGameById(anyInt()))
                .thenReturn(game);

        tryDaoMock = mock(TryPostgressDao.class);

        gameFacade = new GameFacadeLingo(gameDaoMock, wordServiceMock, tryDaoMock, roundDaoMock);
    }

    @Test
    void startGame(){
        when(gameDaoMock.newGame())
                .thenReturn(15);
        when(wordServiceMock.pickwordForGame(5))
                .thenReturn("fiets");

        int id = gameFacade.startGame(); // Act

        assertEquals(id, 15); // Assert
        // ik test nu ook deze functie, terwijl dat misschien niet helemnal de bedoeling is
        // ,maar ik wil aantonen dat er echt een game met een ronde wordt gestart met het goede woord
        // Hierdoor probeer ik ook met de test tegelijk de systeem documentatie te schrijven.
        Map<String, String> resp = gameFacade.nextMove(id, "fiets");
        assertEquals(resp.get("message"), "Attempt was correct, new round has started"); // Assert
    }



    @Test
    void endGame_game_over(){
        List<Try> tries = new ArrayList<>();
        tries.add(new TryLingo(1, "",null, wordServiceMock, tryDaoMock));
        tries.add(new TryLingo(1, "", null,wordServiceMock, tryDaoMock));
        tries.add(new TryLingo(1, "", null,wordServiceMock, tryDaoMock));
        tries.add(new TryLingo(1, "", null,wordServiceMock, tryDaoMock));
        tries.add(new TryLingo(1, "", null,wordServiceMock, tryDaoMock));

        List<Round> rounds = new ArrayList<>();
        rounds.add(new RoundLingo(1, "fiets", tries, roundDaoMock));
        Game game = new GameLingo(1, null, rounds, gameDaoMock, wordServiceMock, roundDaoMock);

        when(gameDaoMock.getGameById(anyInt()))
                .thenReturn(game);
        when(gameDaoMock.getScore(anyInt()))
                .thenReturn(5);

        int score = gameFacade.endGame(18, "Pascal");

        assertEquals(score, 5);
    }


    @Test
    void endGame_game_not_over() {
        List<Try> tries = new ArrayList<>();
        List<Round> rounds = new ArrayList<>();
        rounds.add(new RoundLingo(1, "fiets", tries, roundDaoMock));
        Game game = new GameLingo(1, null, rounds, gameDaoMock, wordServiceMock, roundDaoMock);

        when(gameDaoMock.getGameById(anyInt()))
                .thenReturn(game);
        when(gameDaoMock.getScore(anyInt()))
                .thenReturn(5);

        int score = gameFacade.endGame(18, "Pascal");

        assertEquals(score, 0);
    }

    @Test
    void getHighscore_person_exists(){
        when(gameDaoMock.getHighscore("Pascal"))
                .thenReturn(5);
        int score = gameFacade.getHighscore("Pascal");

        assertEquals(score, 5);
    }

    @Test
    void getHighScore_person_does_not_exist(){
        int score = gameFacade.getHighscore("Pascal");

        assertEquals(score, 0);
    }

    static Stream<Arguments> nextMove_check_word_spelling_Test_words() {
        return Stream.of(
                // capital letters
                Arguments.of("fiets", false),
                Arguments.of("Fiets", true),
                Arguments.of("PiZZa", true),
                Arguments.of("PIZZA", true),
                Arguments.of("piZza", true),
                Arguments.of("pizzA", true),
                // wrong length
                Arguments.of("chauffeur", true),
                Arguments.of("Elektriciteitsproductiemaatschappij", true),
                Arguments.of("vis", true),
                Arguments.of("kaas", true),
                Arguments.of("ik", true),
                // word does not exist
                Arguments.of("fgasdfagas", true),
                Arguments.of("df", true),
                Arguments.of("a", true)
        );
    }
    @ParameterizedTest
    @MethodSource("nextMove_check_word_spelling_Test_words")
    void nextMove_check_word_spelling_Test(String attempt, boolean isInCorrect){

        Map<String, String> resp = gameFacade.nextMove(1, attempt); // Act

        assertEquals(resp.get("message").equals("Spelling was not correct."), isInCorrect); // Assert
    }

    static Stream<Arguments> nextMove_nextMove_game_over_Test_words() {
        return Stream.of(
                Arguments.of(0, false),
                Arguments.of(1, false),
                Arguments.of(2, false),
                Arguments.of(3, false),
                Arguments.of(4, false),
                Arguments.of(5, true),
                Arguments.of(6, true),
                Arguments.of(7, true),
                Arguments.of(10, true),
                Arguments.of(99, true)
        );
    }

    @ParameterizedTest
    @MethodSource("nextMove_nextMove_game_over_Test_words")
    void nextMove_game_over_Test(int rounds, boolean GameOver) {
        Map<String, String> response = new HashMap();

        for (int i = 0; i <= rounds; i++) {
            response = gameFacade.nextMove(1, "cavia");
        }

        assertEquals(response.get("message").equals("Game over, because you already did 5 tries in this round. call endGame method to save name."), GameOver);
    }
}
