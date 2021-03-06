package dao.structure;

import business.system.scorable.Scorable;
import business.system.scorable.faq.Question;
import dao.factory.dao.AbstractFactoryDAO;

import java.util.List;

public abstract class QuestionDAO implements DAO<Question> {
    private static final QuestionDAO INSTANCE = AbstractFactoryDAO.getInstance().getQuestionDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static QuestionDAO getInstance(){
        return INSTANCE;
    }

    public abstract List<Scorable> getAllQuestionsByIdOffer(int idOffer);
    public abstract List<Scorable> getAllQuestionsByIdUser(int idUser);


}

