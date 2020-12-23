package nl.hu.lingo.Import.Application;

import nl.hu.lingo.Import.Domain.WordFilter;
import nl.hu.lingo.Import.Domain.WordFilterLingo;
import nl.hu.lingo.Import.Persistence.DatabasePostgress;
import nl.hu.lingo.Import.Persistence.FileReadDao;
import nl.hu.lingo.Import.Persistence.PostgressWordsDao;

import java.util.List;

public class WordService implements WordServiceInterface {
    public String pickwordForGame(int length) {
        if(length == 5 || length == 6 || length == 7){
            PostgressWordsDao postgressWordsDao = new PostgressWordsDao(new DatabasePostgress());
            FileReadDao fileReadDao = new FileReadDao();
            WordFilter wordFilterLingo = new WordFilterLingo(postgressWordsDao, fileReadDao, length);
            return wordFilterLingo.pickwordForGame();
        } else{
            return null;
        }
    }

    public List<String> getAllWordsWithLength(int length){
        if(length == 5 || length == 6 || length == 7) {

            PostgressWordsDao postgressWordsDao = new PostgressWordsDao(new DatabasePostgress());
            FileReadDao fileReadDao = new FileReadDao();
            WordFilter wordFilterLingo = new WordFilterLingo(postgressWordsDao, fileReadDao, length);

            return wordFilterLingo.getAllWordsWithLength();
        }
        return null;
    }
}
