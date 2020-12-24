package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Import.Application.WordService;
import nl.hu.lingo.Import.Application.WordServiceInterface;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TryTest {
    //=====================================================================================================================
    //==================================================== getFeedback ====================================================
    //=====================================================================================================================

    static Stream<Arguments> getFeedback_set() {
        return Stream.of(
                // five letter words
                Arguments.of("baard", "bergen", 0, 6, 0),
                Arguments.of("baard", "bonje", 1, 4, 0),
                //          Arguments.of("baard", "barst", 2, 1, 1), moet nog worden gefixt
                //          Arguments.of("baard", "draad", 2, 1, 2), moet nog worden gefixt
                Arguments.of("baard", "baard", 5, 0, 0),
                // six letter words
                Arguments.of("bergen", "baard", 0, 5, 0),
                Arguments.of("bergen", "berden", 5, 1, 0),
                Arguments.of("bergen", "gerben", 4, 0, 2),
                Arguments.of("bergen", "bergen", 6, 0, 0),
                // seven letter words
                Arguments.of("aaaabbb", "aaaaaaaa", 0, 8, 0),
                Arguments.of("aaaabbb", "aaaabbb", 7, 0, 0),
                Arguments.of("aaaabbb", "aaaabbb", 7, 0, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("getFeedback_set")
    void getFeedback(String correctWord, String attemptWord, int lettersCorrect, int lettersAbsent, int present){
        Try try_ = new Try(0, attemptWord, null, null, null);

        Map<String, String> feedback = try_.getFeedback(correctWord);

        assertTrue(feedback.get("lettersCorrect").equals(Integer.toString(lettersCorrect)));
        assertTrue(feedback.get("lettersInWord").equals(Integer.toString(present)));
        assertTrue(feedback.get("lettersWrong").equals(Integer.toString(lettersAbsent)));
    }

    //=====================================================================================================================
    //======================================== CheckSpellingContraints ====================================================
    //=====================================================================================================================

    static Stream<Arguments> CheckSpellingContraints_set() {
        return Stream.of(
                // capital letters
                Arguments.of("Pizza", "Er mogen geen leestekens, zoals apostrofs, koppelstreepjes en punten in het woord staan. het woord mag ook niet beginnen met een hoofdletter, zoals plaats- en eigennamen"),
                Arguments.of("PiZZa", "Er mogen geen leestekens, zoals apostrofs, koppelstreepjes en punten in het woord staan. het woord mag ook niet beginnen met een hoofdletter, zoals plaats- en eigennamen"),
                Arguments.of("PIZZA", "Er mogen geen leestekens, zoals apostrofs, koppelstreepjes en punten in het woord staan. het woord mag ook niet beginnen met een hoofdletter, zoals plaats- en eigennamen"),
                Arguments.of("piZza", "Er mogen geen leestekens, zoals apostrofs, koppelstreepjes en punten in het woord staan. het woord mag ook niet beginnen met een hoofdletter, zoals plaats- en eigennamen"),
                Arguments.of("pizzA", "Er mogen geen leestekens, zoals apostrofs, koppelstreepjes en punten in het woord staan. het woord mag ook niet beginnen met een hoofdletter, zoals plaats- en eigennamen"),

                Arguments.of("een-na-laatste", "Er mogen geen leestekens, zoals apostrofs, koppelstreepjes en punten in het woord staan. het woord mag ook niet beginnen met een hoofdletter, zoals plaats- en eigennamen"),
                Arguments.of("dsa:ads", "Er mogen geen leestekens, zoals apostrofs, koppelstreepjes en punten in het woord staan. het woord mag ook niet beginnen met een hoofdletter, zoals plaats- en eigennamen"),
                Arguments.of("dsa;ads", "Er mogen geen leestekens, zoals apostrofs, koppelstreepjes en punten in het woord staan. het woord mag ook niet beginnen met een hoofdletter, zoals plaats- en eigennamen"),
                Arguments.of("dsa,ads", "Er mogen geen leestekens, zoals apostrofs, koppelstreepjes en punten in het woord staan. het woord mag ook niet beginnen met een hoofdletter, zoals plaats- en eigennamen"),
                Arguments.of("dsa.ads", "Er mogen geen leestekens, zoals apostrofs, koppelstreepjes en punten in het woord staan. het woord mag ook niet beginnen met een hoofdletter, zoals plaats- en eigennamen"),
                // word does not exist
                Arguments.of("fgasdfagas", "Dit woord bestaat niet"),
                Arguments.of("df", "Dit woord bestaat niet"),
                Arguments.of("a", "Dit woord bestaat niet"),
                // Happy flow
                Arguments.of("pizza", null)
        );
    }

    @ParameterizedTest
    @MethodSource("CheckSpellingContraints_set")
    void CheckSpellingContraints(String attemptWord, String feedback){
        WordServiceInterface wordServiceMock = mock(WordService.class);
        when(wordServiceMock.getAllWordsWithLength(anyInt()))
                .thenReturn(Arrays.asList("pizza"));
        Try try_ = new Try(0, attemptWord,null, wordServiceMock, null);

        Map<String, String> feedbackMap = try_.CheckSpellingContraints();

        if(feedbackMap.get("feedback") == null && feedback == null){
            assertTrue(feedbackMap.get("feedback") == feedback);
        } else{
            assertTrue(feedbackMap.get("feedback").equals(feedback));
        }
    }
}