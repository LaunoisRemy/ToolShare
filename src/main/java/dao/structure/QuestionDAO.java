package dao.structure;

import business.system.user.User;
import dao.factory.AbstractFactoryDAO;

public abstract class QuestionDAO implements DAO<User> {
    private static final QuestionDAO INSTANCE = AbstractFactoryDAO.getInstance().getQuestionDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static QuestionDAO getInstance(){
        return INSTANCE;
    }


}

