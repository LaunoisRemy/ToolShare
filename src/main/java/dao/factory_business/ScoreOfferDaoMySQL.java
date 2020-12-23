package dao.factory_business;

import business.system.ScoreOffer;
import business.system.offer.Offer;
import business.system.user.User;
import dao.structure.ScoreOfferDAO;

import java.sql.*;

public class ScoreOfferDaoMySQL  extends ScoreOfferDAO {
    private final Connection connection;
    public static final String SCORE_OFFER_ID = "scoreOffer_id";
    public static final String RATE_COL = "rate";
    public static final String USER_ID_COL = "user_id";
    public static final String COMMENT_ID_COL = "comment_id";
    public static final String OFFER_ID_COL = "offer_id";

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
            String sql = "SELECT *  FROM score_offer " +
                    "JOIN offer o on o."+OfferDaoMySQL.OFFER_ID_COL+" = score_offer."+OFFER_ID_COL+" " +
                    " JOIN user u on u."+ UserDaoMySQL.ID_COL+" = score_offer."+USER_ID_COL+" "+
                    " WHERE "+ SCORE_OFFER_ID +" = ?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                if(rs.getInt(1) == (id)){
                    scoreOffer = createScoreOfferFromRs(rs);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return scoreOffer;
    }

    @Override
    public ScoreOffer create(ScoreOffer obj) {

        try {
            String sql = "INSERT INTO score_offer ("+
                    RATE_COL +","+
                    OFFER_ID_COL+","+
                    COMMENT_ID_COL+","+
                    USER_ID_COL+") "+
                    "VALUES (?,?,?,?)";
            PreparedStatement prep = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prep.setFloat(1,obj.getRate());
            prep.setInt(2,obj.getOffer().getOffer_id());
            prep.setInt(3,obj.getCommentId());
            prep.setInt(4,obj.getUser().getUser_id());


            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            obj.setScoreId(generatedKey);
            return obj;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    @Override
    public ScoreOffer update(ScoreOffer obj) {
        try {
            String sql ="UPDATE score_offer " +
                    "SET "+RATE_COL + " = ? " +
                    "WHERE "+ SCORE_OFFER_ID + " = ?";
            PreparedStatement prep = connection.prepareStatement(sql);
            prep.setFloat(1,obj.getRate());
            prep.setInt(2,obj.getScoreId());
            int rs = prep.executeUpdate();
            return obj;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(ScoreOffer obj) {
        try {
            String sql ="DELETE FROM score_offer WHERE "+SCORE_OFFER_ID+" = ?";

            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,obj.getScoreId());
            ResultSet rs = prep.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Creates and returns a Score offer from a query ResultSet
     * @param rs the ResultSet that contains score offer information
     * @return new Score offer with rs information
     * @throws SQLException
     */
    public static ScoreOffer createScoreOfferFromRs(ResultSet rs) throws SQLException {
        Offer offer = OfferDaoMySQL.createOfferFromRs(rs);
        User user = UserDaoMySQL.createUserFromRs(rs);
        return  new ScoreOffer(
                rs.getInt(SCORE_OFFER_ID),
                rs.getFloat(RATE_COL),
                offer,
                rs.getInt(COMMENT_ID_COL),
                user
                );
    }
}
