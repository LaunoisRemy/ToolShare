package dao.mysql;

import business.system.History;
import business.system.offer.Offer;
import dao.structure.HistoryDAO;

import java.sql.Connection;

public class HistoryDaoMySQL extends HistoryDAO {
    private final Connection connection;

    /**
     * Constructor of OfferDaoMySQL
     * @param connection to have a link of the connection
     */
    public HistoryDaoMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public History find(int id) {
        return null;
    }

    @Override
    public History create(History obj) {
        return null;
    }

    @Override
    public History update(History obj) {
        return null;
    }

    @Override
    public boolean delete(History obj) {
        return false;
    }
}
