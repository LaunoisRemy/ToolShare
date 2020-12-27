package business.system;

import business.system.offer.Offer;
import business.system.scorable.Comment;
import business.system.user.User;

public class ScoreOffer {
    private int scoreId;
    private float rate;
    private Offer offer;
    private Comment comment;
    private User user;

    public ScoreOffer(int scoreId, float rate, Offer offer, Comment comment, User user) {
        this.scoreId = scoreId;
        this.rate = rate;
        this.offer = offer;
        this.comment = comment;
        this.user = user;
    }

    public ScoreOffer(float rate, Offer offer, Comment comment, User user) {
        this.rate = rate;
        this.offer = offer;
        this.comment = comment;
        this.user = user;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public int getScoreId() {
        return scoreId;
    }

    public float getRate() {
        return rate;
    }

    public Offer getOffer() {
        return offer;
    }

    public Comment getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ScoreOffer{" +
                "scoreId=" + scoreId +
                ", rate=" + rate +
                ", offer=" + offer +
                ", comment=" + comment +
                ", user=" + user +
                '}';
    }
}
