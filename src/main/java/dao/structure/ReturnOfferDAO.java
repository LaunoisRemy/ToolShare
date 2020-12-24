package dao.structure;

import business.system.reservation.Reservation;
import business.system.reservation.ReturnOffer;
import dao.factory_business.AbstractFactoryDAO;

public abstract class ReturnOfferDAO implements DAO<ReturnOffer> {
    private static final ReturnOfferDAO INSTANCE = AbstractFactoryDAO.getInstance().getReturnOfferDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static ReturnOfferDAO getInstance(){
        return INSTANCE;
    }

}

