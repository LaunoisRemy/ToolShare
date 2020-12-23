package business.system;

import business.system.offer.Offer;
import business.system.user.User;

public class ScoreOffer {
    private int scoreId;
    private float rate;
    private Offer offer;
    private int commentId;
    private User user;

    public ScoreOffer(int scoreId, float rate, Offer offer, int commentId, User user) {
        this.scoreId = scoreId;
        this.rate = rate;
        this.offer = offer;
        this.commentId = commentId;
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

    public int getCommentId() {
        return commentId;
    }

    public User getUser() {
        return user;
    }
}
