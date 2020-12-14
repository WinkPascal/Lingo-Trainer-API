package nl.hu.lingo.Import.Domain;

import java.io.IOException;
import java.util.List;

public interface WriteWordsDao {
    void writeWords(List<String> words, int wordLength) throws IOException;
}
