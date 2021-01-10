package dao.factory.dao;

import business.system.scorable.faq.Answer;
import business.system.user.User;
import dao.structure.AnswerDAO;

import java.sql.*;

public class AnswerDaoMySQL extends AnswerDAO {
    private final Connection connection;
    public static final String ANSWER_ID = "answer_id";
    public static final String ANSWER_SCORE = "answerScore";
    public static final String ANSWER_CONTENT = "answerContent";
    public static final String USER_ID = "user_id";

    /**
     * Constructor of OfferDaoMySQL
     */
    protected AnswerDaoMySQL() {
        this.connection = AbstractFactoryDAO.connection;
    }

    @Override
    public Answer find(int id,int... others) {
        Answer answer = null;
        try {
            String sql = "SELECT *  FROM answer " +
                    "JOIN user u on u."+UserDaoMySQL.USER_ID +" = answer."+USER_ID+" " +
                    " WHERE "+ ANSWER_ID +" = ?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                answer = createAnswerFromRs(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return answer;
    }

    @Override
    public Answer create(Answer obj) {
        try {
            String sql = "INSERT INTO answer ("+
                    ANSWER_SCORE +", "+
                    ANSWER_CONTENT +", "+
                    USER_ID+") "+
                    "VALUES (?,?,?)";
            PreparedStatement prep = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prep.setInt(1,obj.getAnswerScore());
            prep.setString(2,obj.getAnswerContent());
            prep.setInt(3,obj.getUser().getUser_id());


            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            obj.setAnswerId(generatedKey);
            return obj;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public Answer update(Answer obj) {
        try {
            String sql ="UPDATE answer " +
                    "SET "+ ANSWER_SCORE + " = ?, " +
                    ANSWER_CONTENT + " = ?, " +
                    USER_ID + " = ? " +
                    "WHERE "+ ANSWER_ID + " = ?";
            PreparedStatement prep = connection.prepareStatement(sql);
            prep.setInt(1,obj.getAnswerScore());
            prep.setString(2,obj.getAnswerContent());
            prep.setInt(3,obj.getUser().getUser_id());
            prep.setInt(4,obj.getAnswerId());
            int rs = prep.executeUpdate();
            return obj;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(Answer obj) {
        try {
            String sql ="DELETE FROM question WHERE "+ANSWER_ID+" = ?";

            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,obj.getAnswerId());
            ResultSet rs = prep.executeQuery();
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static Answer createAnswerFromRs(ResultSet rs) throws SQLException {
        User user = UserDaoMySQL.createUserFromRs(rs);
        return new Answer(
                rs.getInt(ANSWER_ID),
                rs.getInt(ANSWER_SCORE),
                rs.getString(ANSWER_CONTENT),
                user
        );
    }
}
