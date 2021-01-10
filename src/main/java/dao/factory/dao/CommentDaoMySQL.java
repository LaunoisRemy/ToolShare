package dao.factory.dao;

import business.system.scorable.Comment;
import business.system.scorable.Scorable;
import dao.structure.CommentDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The data access object of Comment
 */
public class CommentDaoMySQL extends CommentDAO {
    private final Connection connection;
    public static final String COMMENT_ID = "comment_id";
    public static final String COMMENT_SCORE = "commentScore";
    public static final String COMMENT_CONTENT = "commentContent";

    /**
     * Constructor of OfferDaoMySQL
     */
    protected CommentDaoMySQL() {
        this.connection = AbstractFactoryDAO.connection;
    }

    /**
     * Method which communicate with DB for retrieve the data T with id
     * @param id id of the object T the system wants
     * @return a object T if he exist in the DB, else return null
     */
    @Override
    public Comment find(int id,int... others) {
        Comment comment = null;
        try {
            String sql = "SELECT *  FROM comment " +
                    " WHERE "+ COMMENT_CONTENT +" = ?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                comment = createCommentFromRs(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return comment;
    }


    @Override
    public List<Scorable> getAllCommentsByIdOffer(int idOffer) {
        return getComments(idOffer, ScoreOfferDaoMySQL.OFFER_ID_COL);
    }

    @Override
    public List<Scorable> getAllCommentsByIdUser(int idUser) {
        return getComments(idUser, ScoreOfferDaoMySQL.USER_ID_COL);
    }

    private List<Scorable> getComments(int id, String clause) {
        List<Scorable> res = new ArrayList<>();

        try {
            String sql = "SELECT *  FROM comment " +
                    "JOIN score_offer so on comment."+COMMENT_ID+" = so."+ScoreOfferDaoMySQL.COMMENT_ID_COL+" " +
                    " WHERE so."+ clause +" = ?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();

            while (rs.next()){
                Scorable s = createCommentFromRs(rs);
                res.add(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    /**
     * Method which communicate with DB for create an obj T
     * @param obj object to save in database
     * @return true if the object is save in database, else return null.
     */
    @Override
    public Comment create(Comment obj) {
        try {
            String sql = "INSERT INTO comment ("+
                    COMMENT_SCORE +","+
                    COMMENT_CONTENT+") "+
                    "VALUES (?,?)";
            PreparedStatement prep = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prep.setInt(1,obj.getCommentScore());
            prep.setString(2,obj.getComment());
            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            obj.setCommentId(generatedKey);
            return obj;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * Method which communicate with DB for update an obj T
     * @param obj object to update in database
     * @return true if the object is update in database, else return null.
     */
    @Override
    public Comment update(Comment obj) {
        try {
            String sql ="UPDATE comment " +
                    "SET "+COMMENT_SCORE + " = ?, " +
                    COMMENT_CONTENT + " = ? " +
                    "WHERE "+ COMMENT_ID + " = ?";
            PreparedStatement prep = connection.prepareStatement(sql);
            prep.setInt(1,obj.getCommentScore());
            prep.setString(2,obj.getComment());
            prep.setInt(3,obj.getCommentId());
            int rs = prep.executeUpdate();
            return obj;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * Method which communicate with DB for delete an obj T
     * @param obj object to delete in database
     * @return true if the object is delete in database, else return null.
     */
    @Override
    public boolean delete(Comment obj) {
        try {
            String sql ="DELETE FROM comment WHERE "+COMMENT_ID+" = ?";

            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,obj.getCommentId());
            ResultSet rs = prep.executeQuery();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /**
     * Create a Comment from resultset
     * @param rs the resultset
     * @return a Comment
     * @throws SQLException Exception regarding the SQL query
     */
    public static Comment createCommentFromRs(ResultSet rs) throws SQLException {
        return  new Comment(
                rs.getInt(COMMENT_ID),
                rs.getString(COMMENT_CONTENT),
                rs.getInt(COMMENT_SCORE)
        );
    }
}
