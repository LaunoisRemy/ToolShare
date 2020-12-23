package dao.factory_business;

import business.system.faq.Answer;
import business.system.faq.Question;
import business.system.user.User;
import dao.structure.AnswerDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerDaoMySQL extends AnswerDAO {
    private final Connection connection;
    public static final String ANSWER_ID = "answer_id";

    /**
     * Constructor of OfferDaoMySQL
     * @param connection to have a link of the connection
     */
    protected AnswerDaoMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Answer find(int id,int... others) {
        return null;
    }

    @Override
    public Answer create(Answer obj) {
        return null;
    }

    @Override
    public Answer update(Answer obj) {
        return null;
    }

    @Override
    public boolean delete(Answer obj) {
        return false;
    }

    public static Answer createAnswerFromRs(ResultSet rs) throws SQLException {
        return null;
    }
}
