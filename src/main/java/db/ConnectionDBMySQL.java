package db;

import com.mysql.cj.MysqlConnection;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Class to create, instantiate Connection
 * It is a singleton class to have only one connection
 */
public class ConnectionDBMySQL implements ConnectionDB{

    /**
     * Connection of the system
     */
    private Connection connection = null;
    private static final ConnectionDBMySQL INSTANCE= new ConnectionDBMySQL();


    private ConnectionDBMySQL()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties p = getDatabaseProperties();
            this.connection = DriverManager.getConnection(p.getProperty("URL"),p.getProperty("USER"),p.getProperty("PASSWORD"));

        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private Properties getDatabaseProperties()
    {
        Properties p = new Properties();
        try (InputStream in = MysqlConnection.class.getClassLoader().getResourceAsStream("db/database.properties"))
        {
            if (in == null)
            {
                throw new NullPointerException("You must specify a database.properties file");
            }
            p.load(new InputStreamReader(in));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return p;
    }

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static ConnectionDBMySQL getInstance(){
        return INSTANCE;
    }

    /**
     * Method return connection to tadatabase
     * @return A connection (session) with a specific database
     */
    public Connection getDb(){
        return connection;
    };
}
