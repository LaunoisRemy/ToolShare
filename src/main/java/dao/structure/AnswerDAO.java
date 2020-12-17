package dao.structure;

import business.system.faq.Answer;
import dao.factory.AbstractFactoryDAO;

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

