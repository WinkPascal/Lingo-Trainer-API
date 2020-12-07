package nl.hu.lingo.Import.Persistence;

import nl.hu.lingo.Import.Domain.ReadWords;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileReadWords implements ReadWords {

    private Object FileNotFoundException;

    @Override
    public List<String> readWordsFilterByWordLength(int wordLength){
        List<String> words = new ArrayList<>();

        try {
            File myObj;
            switch (wordLength){
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

            Scanner myReader = new Scanner(myObj);
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
        String splitKeyWord = ", ";
        words = Arrays.asList(words.get(0).split(splitKeyWord));
        return words;
    }

    public List<String> getAllWords(){
        List<String> words = new ArrayList<>();

        try {
            File myObj = new File("src\\main\\resources\\opentaal-wordlist\\wordlist.txt");
            Scanner myReader = new Scanner(myObj);
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

        return words;
    }
}
