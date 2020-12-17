package dao.factory;

import dao.structure.*;
import db.ConnectionDBMySQL;
import jdk.jfr.Category;

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
    private static AbstractFactoryDAO INSTANCE = null;


    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static AbstractFactoryDAO getInstance(){
        if(INSTANCE == null){
            INSTANCE = new FactoryDAOMySQL();
        }
        return INSTANCE;
    }
    /**
     * Get the data access object of  offer
     * @return Offer dao
     */
    public abstract OfferDAO getOfferDAO();
    /**
     * Get the data access object of reservation
     * @return Offer dao
     */
    public abstract ReservationDAO getReservationDao();
    /**
     * Get the data access object of user
     * @return User dao
     */
    public abstract UserDAO getUserDAO();

    public abstract HistoryDAO getHistoryDAO();
    public abstract QuestionDAO getQuestionDAO();
    public abstract AnswerDAO getAnswerDAO();
    /**
     * Get the data access object of  Score offer (rate)
     * @return Offer dao
     */
   public abstract ScoreOfferDAO getScoreOfferDAO();
    /**
     * Get the data access object of comment
     * @return comment dao
     */
    public abstract CommentDAO getCommentDAO();
    public abstract CategoryDAO getCategoryDAO();
    public abstract FavoryDAO getFavoryDAO();




}
