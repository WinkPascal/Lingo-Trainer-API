package nl.hu.lingo.Import.Persistence;

import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public interface Database {
    Connection getConn();
}
