package business.system.scorable;

import business.system.ScoreType;

/**
 * Interface for Scorable classes
 * such as Answer, Question & Comment
 */
public interface Scorable {

    /**
     * Get the id of the Scorable object
     * @return the id of the scorable object
     */
    int getId();

    /**
     * Set the score of the scorable object
     * @param score the new score
     */
    void setScore(int score);

    /**
     * Get the score of the scorable object
     * @return the score of the scorable object
     */
    int getScore();

    /**
     * Get the score type of the scorable object
     * @return the ScoreType of the scorable object
     */
    ScoreType getScoreType();

    /**
     * Convert the scorable object into a readable String
     * @return a description of the scorable object as a String
     */
    @Override
    String toString();
}
