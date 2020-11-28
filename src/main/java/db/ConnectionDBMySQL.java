package db;

import db.setup.DataConnect;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDBMySQL implements ConnectionDB{
    private Connection connection =null;

    private ConnectionDBMySQL()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(DataConnect.getUrl());

        }
        catch(Exception e)
        {
            //TODO comment on fait les erreurs ?
        }
    }

    private static class createConnection {
        public static final ConnectionDBMySQL INSTANCE= new ConnectionDBMySQL();
    }

    public static ConnectionDBMySQL getInstance(){
        return createConnection.INSTANCE;
    }

    public Connection getDb(){
        return connection;
    };
}
