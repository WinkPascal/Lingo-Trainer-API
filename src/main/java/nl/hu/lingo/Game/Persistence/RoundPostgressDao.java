package nl.hu.lingo.Game.Persistence;

import java.sql.Connection;

public class RoundPostgressDao {
    private Connection conn = null;

    public RoundPostgressDao(Database database) {
        conn = database.getConn();
    }



}
