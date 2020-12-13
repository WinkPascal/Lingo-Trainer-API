package nl.hu.lingo.Import.Application;

import nl.hu.lingo.Import.Domain.ReadWords;
import nl.hu.lingo.Import.Domain.WordFilter;
import nl.hu.lingo.Import.Domain.WordFilterLingo;
import nl.hu.lingo.Import.Domain.WriteWords;
import nl.hu.lingo.Import.Persistence.FileReadWords;
import nl.hu.lingo.Import.Persistence.FileWriteWords;

import java.util.List;

public class WordService implements WordServiceInterface {
    public String pickwordForGame(int length) {
        if(length == 5 || length == 6 || length == 7){
            WriteWords writeWords = new FileWriteWords();
            ReadWords readWords = new FileReadWords();

            WordFilter wordFilterLingo = new WordFilterLingo(writeWords, readWords, length);
            return wordFilterLingo.pickwordForGame();
        } else{
            return null;
        }
    }

    public List<String> getAllWordsWithLength(int length){
        if(length == 5 || length == 6 || length == 7) {
            WriteWords writeWords = new FileWriteWords();
            ReadWords readWords = new FileReadWords();
            WordFilter wordFilterLingo = new WordFilterLingo(writeWords, readWords, length);

            return wordFilterLingo.getAllWordsWithLength(length);
        }
        return null;
    }
}
