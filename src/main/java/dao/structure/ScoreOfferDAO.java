package dao.structure;

import business.system.ScoreOffer;
import dao.factory.dao.AbstractFactoryDAO;

import java.util.List;

public abstract class ScoreOfferDAO implements DAO<ScoreOffer> {
    private static final ScoreOfferDAO INSTANCE = AbstractFactoryDAO.getInstance().getScoreOfferDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static ScoreOfferDAO getInstance(){
        return INSTANCE;
    }

    public abstract List<ScoreOffer> getScoreByUserId(int userId) ;
    public abstract List<ScoreOffer> getScoreByOfferId(int offerId);


}

