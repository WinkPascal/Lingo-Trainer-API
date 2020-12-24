package nl.hu.lingo.Import.Persistence;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadDao {
    List<String> getAllWords();
    }
