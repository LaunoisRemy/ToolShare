package dao.factory;

import dao.structure.UserDAO;
import db.ConnectionDB;
import db.ConnectionDBMySQL;

import java.sql.Connection;

/**
 * Abstract Factory (Singleton pattern)
 * instantiate connection (Singleton)
 */
public abstract class AbstractFactoryDAO {
    /**
     * Connection to the database
     */
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

    /**
     * Get the data access object of user
     * @return User dao
     */
    public abstract UserDAO getUserDAO();
    /**
     * Get the data access object of  offer
     * @return Offer dao
     */
    public abstract UserDAO getOfferDAO();
    /**
     * Get the data access object of  Score offer (rate)
     * @return Offer dao
     */
    public abstract UserDAO getScoreOfferDAO();
    /**
     * Get the data access object of comment
     * @return comment dao
     */
    public abstract UserDAO getCommentDAO();



}
