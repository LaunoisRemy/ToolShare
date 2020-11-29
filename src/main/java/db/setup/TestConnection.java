package db.setup;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class to test the connection to the database
 */
public class TestConnection {
    public static void main(String[] args) {
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection(DataConnect.URL,DataConnect.USER,DataConnect.PASSWORD);

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
}
