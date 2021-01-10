package business.system;

import business.system.offer.Offer;
import business.system.scorable.Comment;
import business.system.user.User;

/**
 * Represent the Score of an Offer in the application
 */
public class ScoreOffer {
    private int scoreId;
    private float rate;
    private Offer offer;
    private Comment comment;
    private User user;

    /**
     * Constructors
     */
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

    /**
     * Setter of the id
     * @param scoreId the id of the offer score
     */
    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    /**
     * Getter of the id
     * @return the id of the offer score
     */
    public int getScoreId() {
        return scoreId;
    }

    /**
     * Getter of the rate
     * @return the rate of the offer score
     */
    public float getRate() {
        return rate;
    }

    /**
     * Getter of the offer
     * @return the offer of the score
     */
    public Offer getOffer() {
        return offer;
    }

    /**
     * Getter of the comment
     * @return the comment of the offer score
     */
    public Comment getComment() {
        return comment;
    }

    /**
     * Getter of the user
     * @return the author of the offer score
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter of the rate
     * @param rate the rate of the offer score
     */
    public void setRate(float rate) {
        this.rate = rate;
    }

    /**
     * Setter of the offer
     * @param offer the offer of the score
     */
    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    /**
     * Setter of the comment
     * @param comment the comment of the offer score
     */
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**
     * Setter of the user
     * @param user the new author of the offer score
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * method that allows to format the object ScoreOffer
     * @return String as a description of the ScoreOffer
     */
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
