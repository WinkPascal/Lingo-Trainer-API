package nl.hu.lingo.Import.Persistence;

import nl.hu.lingo.Import.Domain.WriteWordsDao;

import java.io.FileWriter;
import java.util.List;
import java.io.IOException;

public class FileWriteWordsDao implements WriteWordsDao {

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
