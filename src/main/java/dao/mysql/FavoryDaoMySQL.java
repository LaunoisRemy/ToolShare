package dao.mysql;

import business.system.Favory;
import business.system.offer.Offer;
import dao.structure.FavoryDAO;

import java.sql.Connection;

public class FavoryDaoMySQL extends FavoryDAO {
    private final Connection connection;

    /**
     * Constructor of OfferDaoMySQL
     * @param connection to have a link of the connection
     */
    public FavoryDaoMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Favory find(int id) {
        return null;
    }

    @Override
    public Favory create(Favory obj) {
        return null;
    }

    @Override
    public Favory update(Favory obj) {
        return null;
    }

    @Override
    public boolean delete(Favory obj) {
        return false;
    }
}
