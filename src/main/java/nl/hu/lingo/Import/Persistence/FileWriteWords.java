package nl.hu.lingo.Import.Persistence;

import nl.hu.lingo.Import.Domain.WriteWords;

import java.io.FileWriter;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileWriteWords implements WriteWords {

    @Override
    public void writeWords(List<String> words, int wordLength) throws IOException {
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
