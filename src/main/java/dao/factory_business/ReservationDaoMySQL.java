package dao.factory_business;

import business.system.Reservation;
import dao.structure.ReservationDAO;

import java.sql.Connection;

public class ReservationDaoMySQL extends ReservationDAO {
    private final Connection connection;

    /**
     * Constructor of OfferDaoMySQL
     * @param connection to have a link of the connection
     */
    protected ReservationDaoMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Reservation find(int id,int... others) {
        return null;
    }

    @Override
    public Reservation create(Reservation obj) {
        return null;
    }

    @Override
    public Reservation update(Reservation obj) {
        return null;
    }

    @Override
    public boolean delete(Reservation obj) {
        return false;
    }
}
