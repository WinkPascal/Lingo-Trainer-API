package nl.hu.lingo.Game.Persistence;

import java.sql.Connection;

public interface Database {
    Connection getConn();
}
