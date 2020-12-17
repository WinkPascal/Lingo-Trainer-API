package nl.hu.lingo.Import.Persistence;

import nl.hu.lingo.Import.Domain.ReadWordsDao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileReadWordsDao implements ReadWordsDao {

    @Override
    public List<String> readWordsFilterByWordLength(int wordLength){
        List<String> words = new ArrayList<>();

        try {
            File file = getRightFileDir(wordLength);
            if(file == null) return null;

            Scanner myReader = new Scanner(file);

            while (myReader.hasNextLine()) {
                String word = myReader.nextLine();
                words.add(word);
            }
            myReader.close();
        }
        catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!words.isEmpty()){
            String splitKeyWord = ", ";
            words = Arrays.asList(words.get(0).split(splitKeyWord));

            words.set(0, words.get(0).substring(1));
            words.set(words.size()-1, words.get(words.size()-1).substring(0, words.get(words.size()-1).length() - 1));
        }

        return words;
    }

    private File getRightFileDir(int lenght){
        File myObj;
        switch (lenght){
            case 5:
                myObj = new File("src\\main\\resources\\filteredWords\\fiveLetterWords.txt");
                break;
            case 6:
                myObj = new File("src\\main\\resources\\filteredWords\\sixLetterWords.txt");
                break;
            case 7:
                myObj = new File("src\\main\\resources\\filteredWords\\sevenLetterWords.txt");
                break;
            default:
                return null;
        }
        return myObj;
    }

    public List<String> getAllWords(){
        List<String> words = new ArrayList<>();

        try {
            File myObj = new File("src\\main\\resources\\filteredWords\\wordlist.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String word = myReader.nextLine();
                words.add(word);
            }
            myReader.close();
        }
        catch (java.io.FileNotFoundException e) {
                e.printStackTrace();
        }

        return words;
    }
}
