package dao.factory_business;

import business.system.faq.Answer;
import dao.structure.AnswerDAO;

import java.sql.Connection;

public class AnswerDaoMySQL extends AnswerDAO {
    private final Connection connection;

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
}
