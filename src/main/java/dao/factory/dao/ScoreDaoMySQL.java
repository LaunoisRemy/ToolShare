package dao.factory.dao;

import business.system.Score;
import business.system.ScoreType;
import business.system.scorable.Scorable;
import business.system.user.User;
import dao.structure.ScoreDAO;

import java.sql.*;
import java.util.Objects;

public class ScoreDaoMySQL extends ScoreDAO {
    private final Connection connection;
    public static final String OBJECT_ID = "object_id";
    public static final String USER_ID = "user_id";
    public static final String OBJECT_TYPE = "objectType";
    public static final String SCORE_VALUE = "scoreValue";

    /**
     * Constructor of OfferDaoMySQL
     * @param connection to have a link of the connection
     */
    protected ScoreDaoMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Score find(int idUser, int... others) {
        Score score = null;
        try {
            String sql = "SELECT *  FROM score " +
                    "JOIN user u on u."+UserDaoMySQL.USER_ID+" = score."+USER_ID+" " +
                    " WHERE  u."+ USER_ID +" = ? AND " + OBJECT_ID +" = ? AND " + OBJECT_TYPE +" = ?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,idUser);
            if(others.length != 2){
                throw new Exception("Problem of argument");
            }
            int objectId = others[0];
            String objectType= ScoreType.getTypeByInt(others[1]).getString();
            prep.setInt(1,idUser);
            prep.setInt(2,objectId);
            prep.setString(3,objectType);
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                ScoreType type = ScoreType.getType(rs.getString(OBJECT_TYPE));
                Scorable obj = switch (type) {
                    case QUESTION -> QuestionDaoMySQL.createQuestionFromRs(Objects.requireNonNull(joinFAQ("question", QuestionDaoMySQL.QUESTION_ID, objectId)));
                    case ANSWER -> AnswerDaoMySQL.createAnswerFromRs(Objects.requireNonNull(joinFAQ("answer", AnswerDaoMySQL.ANSWER_ID, objectId)));
                    case COMMENT -> CommentDaoMySQL.createCommentFromRs(Objects.requireNonNull(joinComment(objectId)));
                };
                score = createScoreFromRs(rs,obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return score;
    }
    private ResultSet joinFAQ(String table, String nameClause, int idClause){
        try {
            String sql = "SELECT * FROM " +table+" " +
                    "JOIN user u on u."+UserDaoMySQL.USER_ID+" = "+table+"."+USER_ID+" " +
                    "WHERE "+nameClause +" = ?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,idClause);
            ResultSet rs = prep.executeQuery();
            if(rs.next()){
                return rs;
            }else{
                throw new SQLException();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    private ResultSet joinComment(int idClause){
        try {
            String sql = "SELECT * FROM " + "comment" +" "+
                    "WHERE "+ CommentDaoMySQL.COMMENT_ID +" = ?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,idClause);
            ResultSet rs = prep.executeQuery();
            if(rs.next()){
                return rs;
            }else{
                throw new SQLException();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    @Override
    public Score create(Score obj) {
        try {
            String sql = "INSERT INTO score ("+
                    USER_ID +","+
                    OBJECT_ID +","+
                    OBJECT_TYPE +","+
                    SCORE_VALUE+") "+
                    "VALUES (?,?,?,?)";
            PreparedStatement prep = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            prep.setInt(1,obj.getUser().getUser_id());
            prep.setInt(2,obj.getObject().getId());
            prep.setString(3,obj.getScoreType().getString());
            prep.setInt(4,obj.getScoreValue());
            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            return obj;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public Score update(Score obj) {
        try {
            String sql ="UPDATE score " +
                    "SET "+SCORE_VALUE + " = ? " +
                    "WHERE "+ USER_ID + " = ? AND " + OBJECT_ID + " = ? AND "+ OBJECT_TYPE + " = ? ";
            PreparedStatement prep = connection.prepareStatement(sql);
            prep.setInt(1,obj.getScoreValue());
            prep.setInt(2,obj.getUser().getUser_id());
            prep.setInt(3,obj.getObject().getId());
            prep.setString(4,obj.getScoreType().getString());
            int rs = prep.executeUpdate();
            return obj;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(Score obj) {
        try {
            String sql ="DELETE FROM score WHERE "+OBJECT_ID+" = ?";

            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,obj.getObject().getId());
            ResultSet rs = prep.executeQuery();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static Score createScoreFromRs(ResultSet rs,Scorable obj) throws SQLException {
        User user = UserDaoMySQL.createUserFromRs(rs);
        return  new Score(
                user,
                obj,
                ScoreType.getType(rs.getString(OBJECT_TYPE)),
                rs.getInt(SCORE_VALUE)
        );
    }
}
