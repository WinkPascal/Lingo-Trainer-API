package nl.hu.lingo.Import.Application;

import nl.hu.lingo.Import.Domain.ReadWords;
import nl.hu.lingo.Import.Domain.WordFilter;
import nl.hu.lingo.Import.Domain.WordFilterLingo;
import nl.hu.lingo.Import.Domain.WriteWords;
import nl.hu.lingo.Import.Persistence.FileReadWords;
import nl.hu.lingo.Import.Persistence.FileWriteWords;

public class WordService implements WordServiceInterface {
    public String pickwordForGame(int lenght) {
        WriteWords writeWords = new FileWriteWords();
        ReadWords readWords = new FileReadWords();

        WordFilter wordFilterLingo = new WordFilterLingo(writeWords, readWords, lenght);
        return wordFilterLingo.pickwordForGame();
    }
}
