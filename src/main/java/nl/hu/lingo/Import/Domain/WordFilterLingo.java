package nl.hu.lingo.Import.Domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class WordFilterLingo implements WordFilter {
    private WriteWords writeWordsDao;
    private ReadWords readWordsDao;
    private int lenght;

    public WordFilterLingo(WriteWords writeWordsDao, ReadWords readWordsDao, int lenght){
        this.writeWordsDao = writeWordsDao;
        this.readWordsDao = readWordsDao;
        this.lenght = lenght;
    }

    public String pickwordForGame(){
        List<String> words = readWordsDao.readWordsFilterByWordLength(lenght);
        if(words == null){
            words = filterWordsForGame(readWordsDao.getAllWords());
            try{
                writeWordsDao.writeWords(words, lenght);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int randomNum = ThreadLocalRandom.current().nextInt(0, words.size() + 1);

        return words.get(randomNum);
    }
    private List<String> filterWordsForGame(List<String> allWords) {
        List<String> filteredWords = new ArrayList<>();
        for (int i=0; i<allWords.size(); i++) {
            String word = allWords.get(i);

            if (word.length() == lenght
                    && !word.substring(0, 1).equals(word.substring(0, 1).toUpperCase())
                    && word.indexOf(".") == -1
                    && word.indexOf(",") == -1
                    && word.indexOf("-") == -1
                    && word.indexOf("=") == -1
                    && word.indexOf("'") == -1
                    && word.indexOf("?") == -1
                    && word.indexOf("!") == -1
                    && word.indexOf(":") == -1
                    && word.indexOf("\"") == -1
                    && word.indexOf(";") == -1) {
                filteredWords.add(word);
            }
        }
        return filteredWords;
    }

    public List<String> GetAllWordsWithLength(int lenght){
        return readWordsDao.readWordsFilterByWordLength(lenght);
    }
}
