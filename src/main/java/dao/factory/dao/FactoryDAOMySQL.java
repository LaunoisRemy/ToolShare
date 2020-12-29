package dao.factory.dao;

import dao.structure.*;

/**
 * Factory concrete using MySQL database
 */
public class FactoryDAOMySQL extends AbstractFactoryDAO {
    protected FactoryDAOMySQL(){}


    @Override
    public OfferDAO getOfferDAO() {
        return new OfferDaoMySQL();
    }

    @Override
    public ReservationDAO getReservationDao() {
        return new ReservationDaoMySQL();
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDaoMySQL();
    }

    @Override
    public HistoryDAO getHistoryDAO() {
        return new HistoryDaoMySQL();
    }

    @Override
    public QuestionDAO getQuestionDAO() {
        return new QuestionDaoMySQL();
    }

    @Override
    public AnswerDAO getAnswerDAO() {
        return new AnswerDaoMySQL();
    }

    @Override
    public ScoreOfferDAO getScoreOfferDAO() {
        return new ScoreOfferDaoMySQL();
    }

    @Override
    public CommentDAO getCommentDAO() {
        return new CommentDaoMySQL();
    }

    @Override
    public CategoryDAO getCategoryDAO() {
        return new CategoryDaoMySQL();
    }

    @Override
    public FavoryDAO getFavoryDAO() {
        return new FavoryDaoMySQL();
    }

    @Override
    public ReturnOfferDAO getReturnOfferDAO() {
        return new ReturnOfferDaoMySQL();
    }

    @Override
    public ScoreDAO getScoreDAO() {
        return new ScoreDaoMySQL();
    }

}
