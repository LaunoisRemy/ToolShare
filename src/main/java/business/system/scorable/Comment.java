package business.system.scorable;

import business.system.ScoreType;
import business.system.scorable.Scorable;

public class Comment implements Scorable {
    private int commentId;
    private String comment;
    private int commentScore;

    public Comment(int commentId, String comment, int commentScore) {
        this.commentId = commentId;
        this.comment = comment;
        this.commentScore = commentScore;
    }

    public Comment(String comment, int commentScore) {
        this.comment = comment;
        this.commentScore = commentScore;
    }

    public Comment(String comment) {
        this.comment = comment;
        this.commentScore = 0;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getComment() {
        return comment;
    }

    public int getCommentScore() {
        return commentScore;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int getId() {
        return getCommentId();
    }

    @Override
    public void setScore(int score) {
        setCommentScore(score);
    }

    @Override
    public int getScore() {
        return getCommentScore();
    }

    @Override
    public ScoreType getScoreType() {
        return ScoreType.COMMENT;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", comment='" + comment + '\'' +
                ", commentScore=" + commentScore +
                '}';
    }

    public void setCommentScore(int commentScore) {
        this.commentScore = commentScore;
    }
}
