package business.system.scorable;

import business.system.ScoreType;

public interface Scorable {
    int getId();
    void setScore(int score);
    int getScore();
    ScoreType getScoreType();

    @Override
    String toString();
}
