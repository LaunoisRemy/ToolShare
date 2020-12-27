package dao.structure;

import business.system.History;
import business.system.offer.Offer;
import business.system.user.User;
import dao.factory.dao.AbstractFactoryDAO;

import java.util.List;

public abstract class HistoryDAO implements DAO<History> {
    private static final HistoryDAO INSTANCE = AbstractFactoryDAO.getInstance().getHistoryDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static HistoryDAO getInstance(){
        return INSTANCE;
    }

    public abstract List<Offer> getHistory(User user);


}

