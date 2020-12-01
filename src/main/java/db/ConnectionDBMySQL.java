package db;

import db.setup.DataConnect;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Class to create, instantiate Connection
 * It is a singleton class to have only one connection
 */
public class ConnectionDBMySQL implements ConnectionDB{

    /**
     * Connection of the system
     */
    private Connection connection = null;


    private ConnectionDBMySQL()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(DataConnect.URL,DataConnect.USER,DataConnect.PASSWORD);

        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * The static class definition LazyHolder within it is not initialized until the JVM determines that LazyHolder must be executed
     */
    private static class LazyHolder {
        public static final ConnectionDBMySQL INSTANCE= new ConnectionDBMySQL();
    }

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static ConnectionDBMySQL getInstance(){
        return LazyHolder.INSTANCE;
    }

    /**
     * Method return connection to tadatabase
     * @return A connection (session) with a specific database
     */
    public Connection getDb(){
        return connection;
    };
}
