package nl.hu.lingo.Import.Domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WordFilterLingo implements WordFilter {
    private WriteWordsDao writeWordsDao;
    private ReadWordsDao readWordsDao;
    private int lenght;

    public WordFilterLingo(WriteWordsDao writeWordsDao, ReadWordsDao readWordsDao, int lenght){
        this.writeWordsDao = writeWordsDao;
        this.readWordsDao = readWordsDao;
        this.lenght = lenght;
    }

    public String pickwordForGame(){
        if (lenght == 5 || lenght == 6 || lenght == 7) {
            List<String> words = readWordsDao.readWordsFilterByWordLength(lenght);
            if(words == null){
                List<String> allWords = filterWordsForGame(readWordsDao.getAllWords());
                try{
                    writeWordsDao.writeWords(allWords, lenght);
                    words = readWordsDao.readWordsFilterByWordLength(lenght);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        int s = words.size();
            int randomNum = ThreadLocalRandom.current().nextInt(0, words.size());

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
            return readWordsDao.readWordsFilterByWordLength(lenght);
        }
        return null;
    }
}
