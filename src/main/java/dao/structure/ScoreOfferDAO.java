package dao.structure;

import business.system.ScoreOffer;
import business.system.user.User;
import dao.factory.AbstractFactoryDAO;

public abstract class ScoreOfferDAO implements DAO<ScoreOffer> {
    private static final ScoreOfferDAO INSTANCE = AbstractFactoryDAO.getInstance().getScoreOfferDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static ScoreOfferDAO getInstance(){
        return INSTANCE;
    }


}

