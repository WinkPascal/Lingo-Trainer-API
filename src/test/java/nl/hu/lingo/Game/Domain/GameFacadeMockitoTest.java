package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.*;
import nl.hu.lingo.Import.Application.WordService;
import nl.hu.lingo.Import.Application.WordServiceInterface;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
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

@RunWith(MockitoJUnitRunner.class)
class GameFacadeMockitoTest {

    //=====================================================================================================================
    //====================================================== startGame =====================================================
    //=====================================================================================================================


    @Mock
    private GameDao gameDao;
    @Mock
    private WordServiceInterface wordService ;
    @Mock
    private TryDao tryDaoMock;
    @InjectMocks
    private GameFacadeLingo gameFacade;


    @BeforeEach
    void beforeEach(){
        gameDao = mock(GameDao.class);
        wordService= mock(WordService.class);
        tryDaoMock = mock(TryDao.class);
        gameFacade = new GameFacadeLingo();
    }

    @Test
    void startGame(){

        when(gameDao.newGame())
                .thenReturn(15);
        when(wordService.pickwordForGame(5))
                .thenReturn("fiets");

        int id = gameFacade.startGame(); // Act

        assertTrue(id == 15); // Assert
        // ik test nu ook deze functie, terwijl dat misschien niet helemnal de bedoeling is
        // ,maar ik wil aantonen dat er echt een game met een ronde wordt gestart met het goede woord
        // Hierdoor probeer ik ook met de test tegelijk de systeem documentatie te schrijven.
        Map<String, String> resp = gameFacade.nextMove(id, "fiets");
        assertTrue(resp.get("message").equals("Attempt was correct, new round has started")); // Assert
    }

    //=====================================================================================================================
    //====================================================== endGame =====================================================
    //=====================================================================================================================


    @Test
    void endGame_game_over(){
        List<Try> tries = new ArrayList<>();
        tries.add(new Try(1, "", null));
        tries.add(new Try(2, "", null));
        tries.add(new Try(3, "", null));
        tries.add(new Try(4, "", null));
        tries.add(new Try(5, "", null));

        List<Round> rounds = new ArrayList<>();
        rounds.add(new Round(1, "fiets", tries));
        Game game = new GameLingo(1, null, rounds);

        when(gameDao.getGameById(anyInt()))
                .thenReturn(game);
        when(gameDao.getScore(anyInt()))
                .thenReturn(5);

        int score = gameFacade.endGame(18, "Pascal");

        assertTrue(score == 5);
    }


    @Test
    void endGame_game_not_over() {
        List<Try> tries = new ArrayList<>();
        List<Round> rounds = new ArrayList<>();
        rounds.add(new Round(1, "fiets", tries));
        Game game = new GameLingo(1, null, rounds);

        when(gameDao.getGameById(anyInt()))
                .thenReturn(game);
        when(gameDao.getScore(anyInt()))
                .thenReturn(5);

        int score = gameFacade.endGame(18, "Pascal");

        assertTrue(score == 0);
    }
    //=====================================================================================================================
    //====================================================== getHighscore =====================================================
    //=====================================================================================================================

    @Test
    void getHighscore_person_exists(){
        when(gameDao.getHighscore("Pascal"))
                .thenReturn(5);
        int score = gameFacade.getHighscore("Pascal");

        assertTrue(score == 5);
    }

    @Test
    void getHighScore_person_does_not_exist(){
        int score = gameFacade.getHighscore("Pascal");

        assertTrue(score == 0);
    }
    //=====================================================================================================================
    //====================================================== nextMove =====================================================
    //=====================================================================================================================

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
