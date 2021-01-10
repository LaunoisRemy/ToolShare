package business.system.scorable;

import business.system.ScoreType;

/**
 * Class used to instantiate Comment object
 */
public class Comment implements Scorable {
    private int commentId;
    private String comment;
    private int commentScore;

    /**
     * Constructor
     */
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

    /**
     * Set comment id
     * @param commentId the new id of the comment
     */
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    /**
     * Get comment id
     * @return the id of the comment
     */
    public int getCommentId() {
        return commentId;
    }

    /**
     * Get comment content
     * @return the content of the comment as a String
     */
    public String getComment() {
        return comment;
    }

    /**
     * Get the comment score
     * @return the score of the comment
     */
    public int getCommentScore() {
        return commentScore;
    }

    /**
     * Set the comment content
     * @param comment the content of the comment as a String
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Method from the Scorable Interface
     * @return the id of the scorable object
     */
    @Override
    public int getId() {
        return getCommentId();
    }

    /**
     * Method from the Scorable Interface
     * @param score the score of the scorable object
     */
    @Override
    public void setScore(int score) {
        setCommentScore(score);
    }

    /**
     * Method from the Scorable Interface
     * @return the score of the scorable object
     */
    @Override
    public int getScore() {
        return getCommentScore();
    }

    /**
     * Method from the Scorable Interface
     * @return the score type of the scorable object
     */
    @Override
    public ScoreType getScoreType() {
        return ScoreType.COMMENT;
    }

    /**
     * Convert the answer into a readable object
     * @return a string as a description of the question
     */
    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", comment='" + comment + '\'' +
                ", commentScore=" + commentScore +
                '}';
    }

    /**
     * Set the score of the Comment
     * @param commentScore the comment score
     */
    public void setCommentScore(int commentScore) {
        this.commentScore = commentScore;
    }
}
