package dao.factory_business;

import business.system.faq.Question;
import dao.structure.QuestionDAO;

import java.sql.Connection;

public class QuestionDaoMySQL extends QuestionDAO {
    private final Connection connection;

    /**
     * Constructor of OfferDaoMySQL
     * @param connection to have a link of the connection
     */
    protected QuestionDaoMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Question find(int id,int... others) {
        return null;
    }

    @Override
    public Question create(Question obj) {
        return null;
    }

    @Override
    public Question update(Question obj) {
        return null;
    }

    @Override
    public boolean delete(Question obj) {
        return false;
    }
}
