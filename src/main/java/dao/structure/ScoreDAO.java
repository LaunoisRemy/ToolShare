package dao.structure;

import business.system.Score;
import dao.factory_business.AbstractFactoryDAO;

public abstract class ScoreDAO implements DAO<Score> {
    private static final ScoreDAO INSTANCE = AbstractFactoryDAO.getInstance().getScoreDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static ScoreDAO getInstance(){
        return INSTANCE;
    }


}

