package business.system;

public class Comment {
    private int commentId;
    private String comment;
    private int commentScore;

    public Comment(int commentId, String comment, int commentScore) {
        this.commentId = commentId;
        this.comment = comment;
        this.commentScore = commentScore;
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
}
