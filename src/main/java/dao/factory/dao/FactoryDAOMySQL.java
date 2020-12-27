package dao.factory.dao;

import dao.structure.*;

/**
 * Factory concrete using MySQL database
 */
public class FactoryDAOMySQL extends AbstractFactoryDAO {
    protected FactoryDAOMySQL(){};


    @Override
    public OfferDAO getOfferDAO() {
        return new OfferDaoMySQL(connection);
    }

    @Override
    public ReservationDAO getReservationDao() {
        return new ReservationDaoMySQL(connection);
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDaoMySQL(connection);
    }

    @Override
    public HistoryDAO getHistoryDAO() {
        return new HistoryDaoMySQL(connection);
    }

    @Override
    public QuestionDAO getQuestionDAO() {
        return new QuestionDaoMySQL(connection);
    }

    @Override
    public AnswerDAO getAnswerDAO() {
        return new AnswerDaoMySQL(connection);
    }

    @Override
    public ScoreOfferDAO getScoreOfferDAO() {
        return new ScoreOfferDaoMySQL(connection);
    }

    @Override
    public CommentDAO getCommentDAO() {
        return new CommentDaoMySQL(connection);
    }

    @Override
    public CategoryDAO getCategoryDAO() {
        return new CategoryDaoMySQL(connection);
    }

    @Override
    public FavoryDAO getFavoryDAO() {
        return new FavoryDaoMySQL(connection);
    }

    @Override
    public ReturnOfferDAO getReturnOfferDAO() {
        return new ReturnOfferDaoMySQL(connection);
    }

    @Override
    public ScoreDAO getScoreDAO() {
        return new ScoreDaoMySQL(connection);
    }

}
