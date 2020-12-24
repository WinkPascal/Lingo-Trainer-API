package nl.hu.lingo.Game.Persistence;

import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public interface Database {
    Connection getConn();
}
