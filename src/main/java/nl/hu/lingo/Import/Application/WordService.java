package nl.hu.lingo.Import.Application;

import nl.hu.lingo.Import.Domain.ReadWordsDao;
import nl.hu.lingo.Import.Domain.WordFilter;
import nl.hu.lingo.Import.Domain.WordFilterLingo;
import nl.hu.lingo.Import.Domain.WriteWordsDao;
import nl.hu.lingo.Import.Persistence.FileReadWordsDao;
import nl.hu.lingo.Import.Persistence.FileWriteWordsDao;

import java.util.List;

public class WordService implements WordServiceInterface {
    public String pickwordForGame(int length) {
        if(length == 5 || length == 6 || length == 7){
            WriteWordsDao writeWords = new FileWriteWordsDao();
            ReadWordsDao readWords = new FileReadWordsDao();

            WordFilter wordFilterLingo = new WordFilterLingo(writeWords, readWords, length);
            return wordFilterLingo.pickwordForGame();
        } else{
            return null;
        }
    }

    public List<String> getAllWordsWithLength(int length){
        if(length == 5 || length == 6 || length == 7) {
            WriteWordsDao writeWords = new FileWriteWordsDao();
            ReadWordsDao readWords = new FileReadWordsDao();
            WordFilter wordFilterLingo = new WordFilterLingo(writeWords, readWords, length);

            return wordFilterLingo.getAllWordsWithLength();
        }
        return null;
    }
}
