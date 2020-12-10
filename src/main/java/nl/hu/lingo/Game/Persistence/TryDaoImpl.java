package nl.hu.lingo.Game.Persistence;

import nl.hu.lingo.Game.Domain.TryDao;

import java.util.List;

public class TryDaoImpl implements TryDao {
    Database database;
    public TryDaoImpl(Database database){
        this.database = database;
    }

    @Override
    public List<String> GetAllWords() {


        return null;
    }
}
