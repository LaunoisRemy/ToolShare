package dao.factory_business;

import business.system.ScoreOffer;
import dao.structure.ScoreOfferDAO;

import java.sql.Connection;

public class ScoreOfferDaoMySQL  extends ScoreOfferDAO {
    private final Connection connection;

    /**
     * Constructor of OfferDaoMySQL
     * @param connection to have a link of the connection
     */
    protected ScoreOfferDaoMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ScoreOffer find(int id) {
        return null;
    }

    @Override
    public ScoreOffer create(ScoreOffer obj) {
        return null;
    }

    @Override
    public ScoreOffer update(ScoreOffer obj) {
        return null;
    }

    @Override
    public boolean delete(ScoreOffer obj) {
        return false;
    }
}
