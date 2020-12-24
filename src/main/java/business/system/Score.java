package business.system;

import business.system.scorable.Scorable;
import business.system.user.User;

public class Score {
    private User user;
    private Scorable object;
    private ScoreType scoreType;
    private int scoreValue;

    public Score(User user, Scorable object, ScoreType scoreType, int scoreValue) {
        this.user = user;
        this.object = object;
        this.scoreType = scoreType;
        this.scoreValue = scoreValue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Scorable getObject() {
        return object;
    }

    public void setObject(Scorable object) {
        this.object = object;
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
