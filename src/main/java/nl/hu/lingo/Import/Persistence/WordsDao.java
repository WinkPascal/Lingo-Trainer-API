package nl.hu.lingo.Import.Persistence;

import java.util.List;

public interface WordsDao {
     void refreshWordsWithLength(List<String> words, int wordLength);
     List<String> readWordsFilterByWordLength(int wordLength);
    }
