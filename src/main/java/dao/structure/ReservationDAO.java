package dao.structure;

import business.system.user.User;
import dao.factory.AbstractFactoryDAO;

public abstract class ReservationDAO implements DAO<User> {
    private static final ReservationDAO INSTANCE = AbstractFactoryDAO.getInstance().getReservationDao();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static ReservationDAO getInstance(){
        return INSTANCE;
    }

}

