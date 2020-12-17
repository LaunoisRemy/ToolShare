package dao.structure;

import business.system.History;
import business.system.user.User;
import dao.factory.AbstractFactoryDAO;

public abstract class HistoryDAO implements DAO<History> {
    private static final HistoryDAO INSTANCE = AbstractFactoryDAO.getInstance().getHistoryDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static HistoryDAO getInstance(){
        return INSTANCE;
    }


}

