package nl.hu.lingo.Domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    public void startGame(){

    }

    public void filterWords(int wordLength){
        List<String> output = new ArrayList<String>();
        try {
            File myObj = new File("src\\main\\resources\\opentaal-wordlist\\wordlist.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String word = myReader.nextLine();

                if(word.length() == wordLength
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
                    && word.indexOf(";") == -1 ){
                    output.add(word);
                }
            }
            myReader.close();
            writeWordsToFile(output, wordLength);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeWordsToFile(List<String> words, int wordLength) throws IOException {
        FileWriter myWriter;
        switch (wordLength){
            case 5:
                myWriter = new FileWriter("src\\main\\resources\\filteredWords\\fiveLetterWords.txt");
                break;
            case 6:
                myWriter = new FileWriter("src\\main\\resources\\filteredWords\\sixLetterWords.txt");
                break;
            case 7:
                myWriter = new FileWriter("src\\main\\resources\\filteredWords\\sevenLetterWords.txt");
                break;
            default:
                return;
        }
        myWriter.write(words.toString());
        myWriter.close();
    }
}
