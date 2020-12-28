package business.system.scorable.faq;

import business.system.ScoreType;
import business.system.scorable.Scorable;
import business.system.user.User;

public class Answer implements Scorable {
    private int answerId;
    private int answerScore;
    private String answerContent;
    private User user;

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

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(int answerScore) {
        this.answerScore = answerScore;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int getId() {
        return getAnswerId();
    }

    @Override
    public void setScore(int score) {
        setAnswerScore(score);
    }

    @Override
    public int getScore() {
        return getAnswerScore();
    }

    @Override
    public ScoreType getScoreType() {
        return ScoreType.ANSWER;
    }

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
