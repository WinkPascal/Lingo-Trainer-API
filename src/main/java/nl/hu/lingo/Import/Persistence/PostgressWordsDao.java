package nl.hu.lingo.Import.Persistence;


import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostgressWordsDao implements WordsDao {
    private Connection conn;

    public PostgressWordsDao(Database database){
        conn = database.getConn();
    }

    @Override
    public void refreshWordsWithLength(List<String> words, int wordLength) {
        try{
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM words where LENGTH(word) = "+wordLength;
            stmt.executeUpdate(sql);

            for (String word: words) {
                Statement stmt1 = conn.createStatement();
                String sql1 = "INSERT INTO words(word) VALUES ('"+word+"')";
                stmt1.executeUpdate(sql1);
            }
        } catch (SQLException e ) {
            throw new Error(e);
        }
    }
    @Override
    public List<String> readWordsFilterByWordLength(int wordLength){
        List<String> words = new ArrayList<>();

        String query = "select word from words where LENGTH(word) = "+wordLength+"";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                words.add(rs.getString("word"));
            }
        } catch (Exception e){
            throw new Error(e);
        }
        return words;
    }
}
