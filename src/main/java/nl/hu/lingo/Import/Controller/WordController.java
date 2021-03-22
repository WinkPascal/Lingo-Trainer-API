package nl.hu.lingo.Import.Controller;

import nl.hu.lingo.Import.Domain.WordFilter;
import nl.hu.lingo.Import.Domain.WordFilterLingo;
import nl.hu.lingo.Import.Persistence.FileReadDao;
import nl.hu.lingo.Import.Persistence.WordsDao;

import java.util.List;

public class WordController {
    private WordsDao wordsDao;
    private FileReadDao fileReadDao;

    public WordController(WordsDao wordsDao, FileReadDao fileReadDao) {
        this.wordsDao = wordsDao;
        this.fileReadDao = fileReadDao;
    }

    public String pickwordForGame(int length) {
        WordFilter wordFilterLingo = new WordFilterLingo(wordsDao, fileReadDao, length);
        return wordFilterLingo.pickwordForGame();
    }

    public List<String> getAllWordsWithLength(int length) {
        WordFilter wordFilterLingo = new WordFilterLingo(wordsDao, fileReadDao, length);
        return wordFilterLingo.getAllWordsWithLength();
    }
}
