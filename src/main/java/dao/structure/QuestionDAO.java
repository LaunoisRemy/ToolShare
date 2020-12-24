package dao.structure;

import business.system.scorable.faq.Question;
import dao.factory_business.AbstractFactoryDAO;

public abstract class QuestionDAO implements DAO<Question> {
    private static final QuestionDAO INSTANCE = AbstractFactoryDAO.getInstance().getQuestionDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static QuestionDAO getInstance(){
        return INSTANCE;
    }


}

