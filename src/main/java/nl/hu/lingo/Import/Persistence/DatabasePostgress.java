package nl.hu.lingo.Import.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabasePostgress implements Database {
    private static Connection conn = null;

    public Connection getConn() {
        if(conn == null){
            try {
                String url = "jdbc:postgresql://ec2-54-247-158-179.eu-west-1.compute.amazonaws.com/desicr6n121p1p";
                Properties props = new Properties();
                props.setProperty("user","stxyvzdgnzonia");
                props.setProperty("password","3d07117c12dcbad6dec505b756ccf510a02ef8c71918222296478a89c6a3d990");
                props.setProperty("ssl","false");

                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new Error(e);
            }
        }
        return conn;
    }
}
