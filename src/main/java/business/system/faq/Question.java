package business.system.faq;

import business.system.user.User;

public class Question {
    private int questionId;
    private int questionScore; //TODO A reflechir si c'est pas a calculer
    private String questionContent;
    private Answer answer;
    private int offerId;
    private User user;

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
}
