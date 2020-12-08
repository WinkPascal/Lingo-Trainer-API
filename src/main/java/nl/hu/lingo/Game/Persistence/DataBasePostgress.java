package nl.hu.lingo.Game.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBasePostgress implements Database {

    private static Connection conn = null;

    public Connection getConn() {
        if(conn == null){
            try {
                String url = "jdbc:postgresql://localhost/lingo";
                Properties props = new Properties();
                props.setProperty("user","postgres");
                props.setProperty("password","PascalWink1");
                props.setProperty("ssl","false");

                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new Error(e);
            }
        }
        return conn;
    }
}

