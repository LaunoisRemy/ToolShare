package dao.structure;

import business.system.offer.Offer;
import business.system.offer.PriorityOffer;
import dao.factory.AbstractFactoryDAO;

import java.util.ArrayList;

public abstract class OfferDAO implements DAO<Offer> {
    private static final OfferDAO INSTANCE = AbstractFactoryDAO.getInstance().getOfferDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static OfferDAO getInstance(){
        return INSTANCE;
    }

    public abstract ArrayList<Offer> getOffersFromUser(int user_id);

    public abstract ArrayList<Offer> getOffersByCity(String city);

    //public abstract ArrayList<Offer> getLatestOffers();

    public abstract ArrayList<Offer> getOffersByCategory(int category_id);

    public abstract ArrayList<PriorityOffer> getPriorityOffer();
}

