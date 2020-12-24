package nl.hu.lingo.Import.Persistence;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordsDao {
     void refreshWordsWithLength(List<String> words, int wordLength);
     List<String> readWordsFilterByWordLength(int wordLength);
    }
