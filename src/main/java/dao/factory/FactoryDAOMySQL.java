package dao.factory;

import dao.factory.AbstractFactoryDAO;
import dao.mysql.OfferDaoMySQL;
import dao.mysql.UserDaoMySQL;
import dao.structure.*;
import db.ConnectionDBMySQL;

import java.sql.Connection;

/**
 * Factory concrete using MySQL database
 */
public class FactoryDAOMySQL extends AbstractFactoryDAO {

    @Override
    public OfferDAO getOfferDAO() {
        return new OfferDaoMySQL(connection);
    }

    @Override
    public ReservationDAO getReservationDao() {
        return null;
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDaoMySQL(connection);
    }

    @Override
    public HistoryDAO getHistoryDAO() {
        return null;
    }

    @Override
    public QuestionDAO getQuestionDAO() {
        return null;
    }

    @Override
    public AnswerDAO getAnswerDAO() {
        return null;
    }

    @Override
    public ScoreOfferDAO getScoreOfferDAO() {
        return null;
    }

    @Override
    public CommentDAO getCommentDAO() {
        return null;
    }

    @Override
    public CategoryDAO getCategoryDAO() {
        return null;
    }

    @Override
    public FavoryDAO getFavoryDAO() {
        return null;
    }

}
