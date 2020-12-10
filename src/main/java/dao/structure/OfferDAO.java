package dao.structure;

import business.system.user.User;
import dao.factory.AbstractFactoryDAO;

public abstract class OfferDAO implements DAO<User> {
    private static final OfferDAO INSTANCE = AbstractFactoryDAO.getInstance().getOfferDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static OfferDAO getInstance(){
        return INSTANCE;
    }


}

