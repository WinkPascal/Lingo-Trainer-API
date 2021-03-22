package nl.hu.lingo.Import.Service;

import nl.hu.lingo.Import.Controller.WordController;
import nl.hu.lingo.Import.Persistence.DatabasePostgress;
import nl.hu.lingo.Import.Persistence.FileReadDao;
import nl.hu.lingo.Import.Persistence.PostgressWordsDao;
import nl.hu.lingo.Import.Persistence.WordsDao;

import java.util.List;

public class WordService implements WordServiceInterface {
    public String pickwordForGame(int length) {
        if(length == 5 || length == 6 || length == 7){
            WordsDao postgressWordsDao = new PostgressWordsDao(new DatabasePostgress());
            FileReadDao fileReadDao = new FileReadDao();
            WordController wordController = new WordController(postgressWordsDao, fileReadDao);
            return wordController.pickwordForGame(length);
        } else{
            return null;
        }
    }

    public List<String> getAllWordsWithLength(int length){
        if(length == 5 || length == 6 || length == 7) {

            WordsDao postgressWordsDao = new PostgressWordsDao(new DatabasePostgress());
            FileReadDao fileReadDao = new FileReadDao();

            WordController wordController = new WordController(postgressWordsDao, fileReadDao);
            return wordController.getAllWordsWithLength(length);
        }
        return null;
    }
}
