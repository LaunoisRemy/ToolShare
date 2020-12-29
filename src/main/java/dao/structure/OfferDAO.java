package dao.structure;

import business.system.offer.Offer;
import dao.factory.dao.AbstractFactoryDAO;

import java.util.ArrayList;
import java.util.List;

public abstract class OfferDAO implements DAO<Offer> {
    private static final OfferDAO INSTANCE = AbstractFactoryDAO.getInstance().getOfferDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static OfferDAO getInstance(){
        return INSTANCE;
    }

    public abstract List<Offer> getOffersFromUser(int user_id);

    public abstract List<Offer> getOffersByCity(String city);

    //public abstract ArrayList<Offer> getLatestOffers();
    public abstract  List<Offer> getAllOffers();

    public abstract List<Offer> getOffersByCategory(int category_id);

    public abstract List<Offer> getPriorityOffer();
}

