package nl.hu.lingo.Import.Domain;

import java.util.List;

public interface ReadWords {
    List<String> readWordsFilterByWordLength(int wordLength);

    List<String> getAllWords();
}
