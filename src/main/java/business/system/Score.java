package business.system;

import business.system.user.User;

public class Score {
    private User user;
    private int object_id;
    private ScoreType scoreType;
    private int scoreValue;

    public Score(User user, int object_id, ScoreType scoreType, int scoreValue) {
        this.user = user;
        this.object_id = object_id;
        this.scoreType = scoreType;
        this.scoreValue = scoreValue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
    }

    public ScoreType getScoreType() {
        return scoreType;
    }

    public void setScoreType(ScoreType scoreType) {
        this.scoreType = scoreType;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }
}
