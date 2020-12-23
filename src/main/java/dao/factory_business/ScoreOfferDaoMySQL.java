package dao.factory_business;

import business.system.ScoreOffer;
import dao.structure.ScoreOfferDAO;

import java.sql.*;

public class ScoreOfferDaoMySQL  extends ScoreOfferDAO {
    private final Connection connection;
    static final String ID_COL = "scoreOffer_id";
    static final String RATE_COL = "rate";
    static final String USER_ID_COL = "user_id";
    static final String COMMENT_ID_COL = "comment_id";
    static final String OFFER_ID_COL = "offer_id";

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
            String sql = "SELECT *  FROM score_offer WHERE "+ID_COL+" =?";
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
            prep.setInt(2,obj.getOfferId());
            prep.setInt(3,obj.getCommentId());
            prep.setInt(4,obj.getUserId());


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
                    "WHERE "+ ID_COL + " = ?";
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
        return false;
    }

    /**
     * Creates and returns a Score offer from a query ResultSet
     * @param rs the ResultSet that contains score offer information
     * @return new Score offer with rs information
     * @throws SQLException
     */
    public static ScoreOffer createScoreOfferFromRs(ResultSet rs) throws SQLException {
        return  new ScoreOffer(
                rs.getInt(ID_COL),
                rs.getFloat(RATE_COL),
                rs.getInt(OFFER_ID_COL),
                rs.getInt(COMMENT_ID_COL),
                rs.getInt(USER_ID_COL)
                );
    }
}
