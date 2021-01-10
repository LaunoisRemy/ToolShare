package dao.factory.dao;

import dao.structure.*;
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
    private static AbstractFactoryDAO INSTANCE = null;
    protected  AbstractFactoryDAO(){}

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
     * Get the data access object of offer
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

    /**
     * Get the data access object of history
     * @return History dao
     */
    public abstract HistoryDAO getHistoryDAO();

    /**
     * Get the data access object of Question
     * @return Question dao
     */
    public abstract QuestionDAO getQuestionDAO();

    /**
     * Get the date access object of Answer
     * @return Answer dao
     */
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

    /**
     * Get the data access object of category
     * @return category dao
     */
    public abstract CategoryDAO getCategoryDAO();

    /**
     * Get the data access object of favory
     * @return favory dao
     */
    public abstract FavoryDAO getFavoryDAO();

    /**
     * Get the data access object of returnoffer
     * @return returnoffer dao
     */
    public abstract ReturnOfferDAO getReturnOfferDAO();

    /**
     * Get the data access object of score
     * @return score dao
     */
    public abstract ScoreDAO getScoreDAO();




}
