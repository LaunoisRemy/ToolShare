package business.system;

public class ScoreOffer {
    private int scoreId;
    private float rate;
    private int offerId;
    private int commentId;
    private int userId;

    public ScoreOffer(int scoreId, float rate, int offerId, int commentId, int userId) {
        this.scoreId = scoreId;
        this.rate = rate;
        this.offerId = offerId;
        this.commentId = commentId;
        this.userId = userId;
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

    public int getOfferId() {
        return offerId;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getUserId() {
        return userId;
    }
}
