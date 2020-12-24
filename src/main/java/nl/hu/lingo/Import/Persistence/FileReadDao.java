package nl.hu.lingo.Import.Persistence;

import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Repository
public class FileReadDao implements ReadDao {

    public List<String> getAllWords(){
        List<String> words = new ArrayList<>();
        try {
            File myObj = new File("src\\main\\resources\\static\\filteredWords\\wordlist.txt");
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
