package dao.factory_business;

import business.system.ScoreOffer;
import business.system.user.User;
import dao.structure.ScoreOfferDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreOfferDaoMySQL  extends ScoreOfferDAO {
    private final Connection connection;
    static final String ID_COL = "scoreOfffer_id";

    /**
     * Constructor of OfferDaoMySQL
     * @param connection to have a link of the connection
     */
    protected ScoreOfferDaoMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ScoreOffer find(int id,int... others) {
        ScoreOffer scoreOffer = null;
        try {
            String sql = "SELECT *  FROM user WHERE "+ID_COL+" =?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                if(rs.getInt(1) == (id)){
                    //scoreOffer = createScoreOfferFromRs(rs);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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

    /**
     * Creates and returns an user from a query ResultSet
     * @param rs the ResultSet that contains user information
     * @return new User with rs information
     * @throws SQLException
     */
    public static User createScoreOfferFromRs(ResultSet rs) throws SQLException {
        return  null;
    }
}
