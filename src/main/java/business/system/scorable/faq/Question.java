package business.system.scorable.faq;

import business.system.ScoreType;
import business.system.scorable.Scorable;
import business.system.user.User;

/**
 * Class used to instantiate Question object
 */
public class Question implements Scorable {
    private int questionId;
    private int questionScore;
    private String questionContent;
    private Answer answer;
    private final int offerId;
    private final User user;

    /**
     * Constructor
     */
    public Question(int questionId, int questionScore, String questionContent, Answer answer, int offerId, User user) {
        this.questionId = questionId;
        this.questionScore = questionScore;
        this.questionContent = questionContent;
        this.answer = answer;
        this.offerId = offerId;
        this.user = user;
    }

    public Question(int questionScore, String questionContent, Answer answer, int offerId, User user) {
        this.questionScore = questionScore;
        this.questionContent = questionContent;
        this.answer = answer;
        this.offerId = offerId;
        this.user = user;
    }

    /**
     * Set the question id
     * @param questionId the id
     */
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    /**
     * Get the question id
     * @return the id
     */
    public int getQuestionId() {
        return questionId;
    }

    /**
     * Get the question offer
     * @return the id of the offer
     */
    public int getOfferId() {
        return offerId;
    }

    /**
     * Get the author of the question
     * @return a user
     */
    public User getUser() {
        return user;
    }

    /**
     * Get the question score
     * @return the score as an int
     */
    public int getQuestionScore() {
        return questionScore;
    }

    /**
     * Set the question score
     * @param questionScore the new score of the question
     */
    public void setQuestionScore(int questionScore) {
        this.questionScore = questionScore;
    }

    /**
     * Get the question content
     * @return the question as a string
     */
    public String getQuestionContent() {
        return questionContent;
    }

    /**
     * Set the question content
     * @param questionContent the string of the question
     */
    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    /**
     * Get the question answer
     * @return an answer or null if the question doesn't have one yet
     */
    public Answer getAnswer() {
        return answer;
    }

    /**
     * Set the question answer
     * @param answer the answer of the question
     */
    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    /**
     * Method from the Scorable Interface
     * @return the id of the scorable object
     */
    @Override
    public int getId() {
        return getQuestionId();
    }

    /**
     * Method from the Scorable Interface
     * @param score the score of the scorable object
     */
    @Override
    public void setScore(int score) {
        setQuestionScore(score);
    }

    /**
     * Method from the Scorable Interface
     * @return the score of the scorable object
     */
    @Override
    public int getScore() {
        return getQuestionScore();
    }

    /**
     * Method from the Scorable Interface
     * @return the score type of the scorable object
     */
    @Override
    public ScoreType getScoreType() {
        return ScoreType.QUESTION;
    }
}
