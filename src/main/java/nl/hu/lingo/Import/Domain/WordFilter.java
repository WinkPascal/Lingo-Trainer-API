package nl.hu.lingo.Import.Domain;

import java.util.List;

public interface WordFilter {
    String pickwordForGame();
    List<String> getAllWordsWithLength();
}
