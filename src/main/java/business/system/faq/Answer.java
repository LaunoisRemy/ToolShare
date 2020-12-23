package business.system.faq;

import business.system.user.User;

public class Answer {
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
}
