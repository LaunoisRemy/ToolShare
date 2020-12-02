package dao.factory;

import dao.structure.UserDAO;
import db.ConnectionDB;
import db.ConnectionDBMySQL;

import java.sql.Connection;

public abstract class AbstractFactoryDAO {
    public static final Connection connection = ConnectionDBMySQL.getInstance().getDb();

    /**
     * The static class definition LazyHolder within it is not initialized until the JVM determines that LazyHolder must be executed
     */
    private static class LazyHolder {
        public static final AbstractFactoryDAO INSTANCE= new FactoryDAOMySQL();
    }

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static AbstractFactoryDAO getInstance(){
        return AbstractFactoryDAO.LazyHolder.INSTANCE;
    }


    public abstract UserDAO getUserDAO();



}
