package nl.hu.lingo.Import.Application;

import nl.hu.lingo.Import.Domain.WordFilter;
import nl.hu.lingo.Import.Domain.WordFilterLingo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService implements WordServiceInterface {
    public String pickwordForGame(int length) {
        if(length == 5 || length == 6 || length == 7){
            WordFilter wordFilterLingo = new WordFilterLingo(length);
            return wordFilterLingo.pickwordForGame();
        } else{
            return null;
        }
    }

    public List<String> getAllWordsWithLength(int length){
        if(length == 5 || length == 6 || length == 7) {
            WordFilter wordFilterLingo = new WordFilterLingo(length);

            return wordFilterLingo.getAllWordsWithLength();
        }
        return null;
    }
}
