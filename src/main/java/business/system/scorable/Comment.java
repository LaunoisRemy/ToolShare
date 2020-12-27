package business.system.scorable;

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
}