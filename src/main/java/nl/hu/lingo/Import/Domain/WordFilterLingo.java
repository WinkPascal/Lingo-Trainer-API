package nl.hu.lingo.Import.Domain;

import nl.hu.lingo.Import.Persistence.FileReadDao;
import nl.hu.lingo.Import.Persistence.PostgressWordsDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WordFilterLingo implements WordFilter {
    private PostgressWordsDao postgressWordsDao;
    private FileReadDao fileReadDao;
    private int lenght;

    public WordFilterLingo(PostgressWordsDao postgressWordsDao, FileReadDao fileReadDao, int lenght){
        this.postgressWordsDao = postgressWordsDao;
        this.fileReadDao = fileReadDao;
        this.lenght = lenght;
    }

    public String pickwordForGame(){
        if (lenght == 5 || lenght == 6 || lenght == 7) {
            List<String> words = postgressWordsDao.readWordsFilterByWordLength(lenght);
            if(words.size() == 0){
                List<String> allWords = filterWordsForGame(fileReadDao.getAllWords());
                postgressWordsDao.refreshWordsWithLength(allWords, lenght);
                words = postgressWordsDao.readWordsFilterByWordLength(lenght);
            }
            int randomNum = ThreadLocalRandom.current().nextInt(0, words.size()-1);

            return words.get(randomNum);
        }
        return null;
    }
    private List<String> filterWordsForGame(List<String> allWords) {
        List<String> filteredWords = new ArrayList<>();
        for (int i = 0; i < allWords.size(); i++) {
            String word = allWords.get(i);

            if (word.length() == lenght &&
                    word.matches("\\p{javaLowerCase}*")) {
                filteredWords.add(word);
            }
        }
        return filteredWords;
    }
    public List<String> getAllWordsWithLength(){
        if(lenght == 5 || lenght == 6 || lenght == 7) {
            return postgressWordsDao.readWordsFilterByWordLength(lenght);
        }
        return null;
    }
}
