package dao.factory.dao;

import business.system.ScoreOffer;
import business.system.offer.Offer;
import business.system.scorable.Comment;
import business.system.user.User;
import dao.factory.object.FactoryObject;
import dao.structure.ScoreOfferDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreOfferDaoMySQL  extends ScoreOfferDAO {
    private final Connection connection;
    //public static final String SCORE_OFFER_ID = "scoreOffer_id";
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
    public ScoreOffer find(int userId,int... others) {

        ScoreOffer scoreOffer = null;
        try {
            String sql = "SELECT *  FROM score_offer " +
                    "JOIN offer o on o."+OfferDaoMySQL.OFFER_ID_COL+" = score_offer."+OFFER_ID_COL+" " +
                    " LEFT JOIN category cat ON o."+OfferDaoMySQL.CATEGORY_ID_COL+" = cat."+CategoryDaoMySQL.CATEGORY_ID_COL+
                    " JOIN user u on u."+ UserDaoMySQL.USER_ID +" = score_offer."+USER_ID_COL+" " +
                    " LEFT JOIN comment c on  score_offer."+COMMENT_ID_COL+" = c."+CommentDaoMySQL.COMMENT_ID+" "+
                    " WHERE score_offer."+ USER_ID_COL +" = ? AND score_offer."+ OFFER_ID_COL +" = ?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            if(others.length != 1){
                throw new Exception();
            }else{

                prep.setInt(1,userId);
                prep.setInt(2,others[0]);
            }

            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                scoreOffer = createScoreOfferFromRs(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scoreOffer;
    }

    @Override
    public List<ScoreOffer> getScoreByUserId(int userId) {
        return getScoreOffers(userId, USER_ID_COL);
    }

    @Override
    public List<ScoreOffer> getScoreByOfferId(int offerId) {
        return getScoreOffers(offerId, OFFER_ID_COL);
    }

    private List<ScoreOffer> getScoreOffers(int id, String clause) {
        ArrayList<ScoreOffer> res = new ArrayList<>();
        try {
            String sql = "SELECT *  FROM score_offer " +
                    "JOIN offer o on o."+ OfferDaoMySQL.OFFER_ID_COL+" = score_offer."+OFFER_ID_COL+" " +
                    " LEFT JOIN category cat ON o."+OfferDaoMySQL.CATEGORY_ID_COL+" = cat."+CategoryDaoMySQL.CATEGORY_ID_COL+
                    " JOIN user u on u."+ UserDaoMySQL.USER_ID +" = score_offer."+USER_ID_COL+" "+
                    " LEFT JOIN comment c on  score_offer."+COMMENT_ID_COL+" = c."+CommentDaoMySQL.COMMENT_ID+" "+
                    " WHERE "+ clause +" = ?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                res.add(createScoreOfferFromRs(rs));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return res;
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
            Comment commentId = obj.getComment();
            if(commentId == null){
                prep.setNull(3, java.sql.Types.INTEGER);
            }else{
                prep.setInt(3,commentId.getCommentId());

            }
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
                    "SET "+RATE_COL + " = ?, " +
                    COMMENT_ID_COL + " = ? " +
                    "WHERE "+ USER_ID_COL + " = ? AND "+OFFER_ID_COL + " = ? " ;
            PreparedStatement prep = connection.prepareStatement(sql);
            prep.setFloat(1,obj.getRate());
            Comment commentId = obj.getComment();
            if(commentId == null){
                prep.setNull(2, java.sql.Types.INTEGER);
            }else{
                prep.setInt(2,commentId.getCommentId());

            }
            prep.setInt(3,obj.getUser().getUser_id());
            prep.setInt(4,obj.getOffer().getOffer_id());
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
            String sql ="DELETE FROM score_offer " +
                    "WHERE "+ USER_ID_COL + " = ? AND "+OFFER_ID_COL + " = ? " ;;
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,obj.getUser().getUser_id());
            prep.setInt(2,obj.getOffer().getOffer_id());
            ResultSet rs = prep.executeQuery();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    /**
     * Creates and returns a Score offer from a query ResultSet
     * @param rs the ResultSet that contains score offer information
     * @return new Score offer with rs information
     * @throws SQLException
     */
    public static ScoreOffer createScoreOfferFromRs(ResultSet rs) throws SQLException {
        Offer offer = OfferDaoMySQL.createOfferFromRs(rs);
        User user = FactoryObject.createUserFromResultSet(rs);
        Comment comment = null;
        int commentId = rs.getInt(COMMENT_ID_COL);
        if( !rs.wasNull() ){
            comment = CommentDaoMySQL.createCommentFromRs(rs);
        }

        return  new ScoreOffer(
                rs.getFloat(RATE_COL),
                offer,
                comment,
                user
                );
    }
}
