package business.system.scorable.faq;

import business.system.ScoreType;
import business.system.scorable.Scorable;
import business.system.user.User;

public class Question implements Scorable {
    private int questionId;
    private int questionScore;
    private String questionContent;
    private Answer answer;
    private final int offerId;
    private final User user;

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

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getOfferId() {
        return offerId;
    }

    public User getUser() {
        return user;
    }

    public int getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(int questionScore) {
        this.questionScore = questionScore;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public int getId() {
        return getQuestionId();
    }

    @Override
    public void setScore(int score) {
        setQuestionScore(score);
    }

    @Override
    public int getScore() {
        return getQuestionScore();
    }

    @Override
    public ScoreType getScoreType() {
        return ScoreType.QUESTION;
    }
}
