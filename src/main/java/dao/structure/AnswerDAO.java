package dao.structure;

import business.system.scorable.faq.Answer;
import dao.factory.dao.AbstractFactoryDAO;

public abstract class AnswerDAO implements DAO<Answer> {
    private static final AnswerDAO INSTANCE = AbstractFactoryDAO.getInstance().getAnswerDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static AnswerDAO getInstance(){
        return INSTANCE;
    }


}

