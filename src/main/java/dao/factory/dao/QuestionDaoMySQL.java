package dao.factory.dao;

import business.system.scorable.Scorable;
import business.system.scorable.faq.Answer;
import business.system.scorable.faq.Question;
import business.system.user.User;
import dao.structure.QuestionDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoMySQL extends QuestionDAO {
    private final Connection connection;
    public static final String QUESTION_ID = "question_id";
    public static final String SCORE_COL = "questionScore";
    public static final String CONTENT_COL = "questionContent";
    public static final String USER_ID_COL = "user_id";
    public static final String OFFER_ID_COL = "offer_id";
    public static final String ANSWER_ID_COL = "answer_id";

    /**
     * Constructor of QuestionDaoMySQL
     */
    protected QuestionDaoMySQL() {
        this.connection = AbstractFactoryDAO.connection;
    }

    @Override
    public Question find(int id,int... others) {
        Question question = null;
        try {
            String sql = "SELECT *  FROM question " +
                    "JOIN user u on u."+UserDaoMySQL.USER_ID +" = question."+ USER_ID_COL +" "+
                    "LEFT JOIN answer a on a."+AnswerDaoMySQL.ANSWER_ID+" = question."+ANSWER_ID_COL+" " +
                    " WHERE "+ QUESTION_ID +" = ?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                question = createQuestionFromRs(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return question;
    }

    @Override
    public List<Scorable> getAllQuestionsByIdOffer(int idOffer) {
        return getQuestions(idOffer, OFFER_ID_COL);
    }
    @Override
    public List<Scorable> getAllQuestionsByIdUser(int idUser) {
        return getQuestions(idUser, USER_ID_COL);
    }

    private List<Scorable> getQuestions(int id, String clause) {
        ArrayList<Scorable> res = new ArrayList<>();

        try {
            String sql = "SELECT *  FROM question " +
                    "JOIN user u on u."+ UserDaoMySQL.USER_ID +" = question."+ USER_ID_COL +" "+
                    "LEFT JOIN answer a on a."+ AnswerDaoMySQL.ANSWER_ID+" = question."+ANSWER_ID_COL+" " +
                    " WHERE "+ clause +" = ?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                res.add(createQuestionFromRs(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public Question create(Question obj) {
        try {
            String sql = "INSERT INTO question ("+
                    SCORE_COL +","+
                    CONTENT_COL+","+
                    ANSWER_ID_COL+","+
                    OFFER_ID_COL+","+
                    USER_ID_COL+") "+
                    "VALUES (?,?,?,?,?)";
            PreparedStatement prep = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prep.setInt(1,obj.getQuestionScore());
            prep.setString(2,obj.getQuestionContent());
            Answer a = obj.getAnswer();
            if(a != null){
                prep.setInt(3,obj.getAnswer().getAnswerId());
            }else{
                prep.setNull(3,java.sql.Types.INTEGER);


            }

            prep.setInt(4,obj.getOfferId());
            prep.setInt(5,obj.getUser().getUser_id());


            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            obj.setQuestionId(generatedKey);
            return obj;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public Question update(Question obj) {
        try {
            String sql ="UPDATE question " +
                    "SET "+SCORE_COL + " = ?, " +
                    CONTENT_COL + " = ?, " +
                    ANSWER_ID_COL + " = ?, " +
                    OFFER_ID_COL + " = ? ," +
                    USER_ID_COL + " = ? " +
                    "WHERE "+ QUESTION_ID + " = ?";
            PreparedStatement prep = connection.prepareStatement(sql);
            prep.setInt(1,obj.getQuestionScore());
            prep.setString(2,obj.getQuestionContent());
            Answer answer = obj.getAnswer();
            if(answer != null){
                prep.setInt(3,obj.getAnswer().getAnswerId());

            }else{
                prep.setNull(3,java.sql.Types.INTEGER);
            }
            prep.setInt(4,obj.getOfferId());
            prep.setInt(5,obj.getUser().getUser_id());
            prep.setInt(6,obj.getQuestionId());
            int rs = prep.executeUpdate();
            return obj;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(Question obj) {
        try {
            String sql ="DELETE FROM question WHERE "+QUESTION_ID+" = ?";

            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,obj.getQuestionId());
            ResultSet rs = prep.executeQuery();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public static Question createQuestionFromRs(ResultSet rs) throws SQLException {
        Answer answer = null;
        rs.getInt(ANSWER_ID_COL);
        if(!rs.wasNull()){
            answer = AnswerDaoMySQL.createAnswerFromRs(rs);
        }
        rs.getInt(USER_ID_COL);
        User user = null;

        if(!rs.wasNull()){
            user = UserDaoMySQL.createUserFromRs(rs);
        }
        return  new Question(
                rs.getInt(QUESTION_ID),
                rs.getInt(SCORE_COL),
                rs.getString(CONTENT_COL),
                answer,
                rs.getInt(OFFER_ID_COL),
                user
                );
    }
}
