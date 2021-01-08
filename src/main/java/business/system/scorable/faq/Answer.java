package business.system.scorable.faq;

import business.system.ScoreType;
import business.system.scorable.Scorable;
import business.system.user.User;

/**
 * Class used to instantiate Answer object
 */
public class Answer implements Scorable {
    private int answerId;
    private int answerScore;
    private String answerContent;
    private User user;

    /**
     * Constructor
     */
    public Answer(int answerId, int answerScore, String answerContent, User user) {
        this.answerId = answerId;
        this.answerScore = answerScore;
        this.answerContent = answerContent;
        this.user = user;
    }

    public Answer(int answerScore, String answerContent, User user) {
        this.answerScore = answerScore;
        this.answerContent = answerContent;
        this.user = user;
    }

    /**
     * Get the answer id
     * @return the id of the answer
     */
    public int getAnswerId() {
        return answerId;
    }

    /**
     * Set the answer id
     * @param answerId the new id of the answer
     */
    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    /**
     * Get the answer score
     * @return the score of the answer
     */
    public int getAnswerScore() {
        return answerScore;
    }

    /**
     * Set the answer score
     * @param answerScore the new score of the answer
     */
    public void setAnswerScore(int answerScore) {
        this.answerScore = answerScore;
    }

    /**
     * Get the answer content
     * @return the answer content
     */
    public String getAnswerContent() {
        return answerContent;
    }

    /**
     * Set the answer content
     * @param answerContent the new content of the answer
     */
    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    /**
     * Get the author of the answer
     * @return a user
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the author of the answer
     * @param user a user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Method from the Scorable Interface
     * @return the id of the Scorable Object
     */
    @Override
    public int getId() {
        return getAnswerId();
    }

    /**
     * Method from the Scorable Interface
     * @param score the score of the Scorable object
     */
    @Override
    public void setScore(int score) {
        setAnswerScore(score);
    }

    /**
     * Method from the Scorable Interface
     * @return the score of the Scorable object
     */
    @Override
    public int getScore() {
        return getAnswerScore();
    }

    /**
     * Method from the Scorable Interface
     * @return the score type of the Scorable object
     */
    @Override
    public ScoreType getScoreType() {
        return ScoreType.ANSWER;
    }

    /**
     * Convert the answer into a readable object
     * @return a string as a description of the answer
     */
    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", answerScore=" + answerScore +
                ", answerContent='" + answerContent + '\'' +
                ", user=" + user +
                '}';
    }
}
