package nl.hu.lingo.Import.Service;

import java.util.List;

public interface WordServiceInterface {
    String pickwordForGame(int lenght);

    List<String> getAllWordsWithLength(int length);
}
