package business.system;

import business.system.scorable.Scorable;
import business.system.user.User;

/**
 * Represent the Score in the application
 */
public class Score {
    private User user;
    private Scorable object;
    private ScoreType scoreType;
    private int scoreValue;

    /**
     * Constructor
     */
    public Score(User user, Scorable object, ScoreType scoreType, int scoreValue) {
        this.user = user;
        this.object = object;
        this.scoreType = scoreType;
        this.scoreValue = scoreValue;
    }

    /**
     * Getter of the user
     * @return the user of the score
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter of the user
     * @param user the new user of the score
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter of the Scorable Object
     * @return the Scorable of the score (Question, Answer or Comment...)
     */
    public Scorable getObject() {
        return object;
    }

    /**
     * Setter of the Scorable Object
     * @param object the new Scorable of the score
     */
    public void setObject(Scorable object) {
        this.object = object;
    }

    /**
     * Getter of the ScoreType
     * @return the ScoreType of the score
     */
    public ScoreType getScoreType() {
        return scoreType;
    }

    /**
     * Setter of the ScoreType
     * @param scoreType the new ScoreType of the score
     */
    public void setScoreType(ScoreType scoreType) {
        this.scoreType = scoreType;
    }

    /**
     * Getter of the score
     * @return return the score as an int
     */
    public int getScoreValue() {
        return scoreValue;
    }

    /**
     * Setter of the score
     * @param scoreValue the new score as an int
     */
    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    /**
     * method that allows to format the object score
     * @return String as a description of the score
     */
    @Override
    public String toString() {
        return "Score{" +
                "user=" + user +
                ", object=" + object +
                ", scoreType=" + scoreType +
                ", scoreValue=" + scoreValue +
                '}';
    }
}
