package dao.structure;

import business.system.Reservation;
import dao.factory_business.AbstractFactoryDAO;

public abstract class ReservationDAO implements DAO<Reservation> {
    private static final ReservationDAO INSTANCE = AbstractFactoryDAO.getInstance().getReservationDao();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static ReservationDAO getInstance(){
        return INSTANCE;
    }

}
