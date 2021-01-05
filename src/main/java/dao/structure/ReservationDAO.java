package dao.structure;

import business.system.reservation.Reservation;
import dao.factory.dao.AbstractFactoryDAO;

import java.util.List;

public abstract class ReservationDAO implements DAO<Reservation> {
    private static final ReservationDAO INSTANCE = AbstractFactoryDAO.getInstance().getReservationDao();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static ReservationDAO getInstance(){
        return INSTANCE;
    }

    public abstract List<Reservation> getReservationsByOffer(int id_offer);
    public abstract  List<Reservation> getReservationsByUser(int id_user);
    public abstract  List<Reservation> getReservationsByUserNotReturned(int id_user);
    public abstract int nbJoursForReservation(Reservation reservation);

}

