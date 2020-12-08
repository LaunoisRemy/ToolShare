package db.setup;

import com.mysql.cj.MysqlConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Class to test the connection to the database
 */
public class TestConnection {
    public static void main(String[] args) {
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Properties p = getDatabaseProperties();
            System.out.println(p.getProperty("URL") + p.getProperty("USER") + p.getProperty("PASSWORD"));
        Connection connection = DriverManager.getConnection(p.getProperty("URL"),p.getProperty("USER"),p.getProperty("PASSWORD"));

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT *  FROM user");

        while (rs.next()) {
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                System.out.println(rs.getString(i));
            }
        }

        statement.close();


    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
    }
    private static Properties getDatabaseProperties()
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
}
