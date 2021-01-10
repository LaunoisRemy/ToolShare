package business.system;

import java.util.Locale;

/**
 * Enumeration of possibilities type of a score
 */
public enum ScoreType {
    QUESTION,
    ANSWER,
    COMMENT;

    /**
     * Getter of the String value of the type
     * @return the type as a String
     */
    public String getString(){
        return String.valueOf(this).toLowerCase(Locale.ROOT);
    }

    /**
     * Getter of the ScoreType
     * @param type value of the type as a String
     * @return the ScoreType
     */
    public static ScoreType getType(String type){
        return switch (type.toLowerCase(Locale.ROOT)) {
            case "question" -> QUESTION;
            case "answer" -> ANSWER;
            case "comment" -> COMMENT;
            default -> null;
        };
    }

    /**
     * Getter of the ScoreType
     * @param type value of the type as an int
     * @return the ScoreType
     */
    public static ScoreType getTypeByInt(int type){
        return switch (type) {
            case 0 -> QUESTION;
            case 1 -> ANSWER;
            case 2 -> COMMENT;
            default -> null;
        };
    }

    /**
     * Getter of the int value of the type
     * @return the id of the type
     */
    public int getIntByType(){
        return switch (this) {
            case QUESTION -> 0;
            case ANSWER -> 1;
            case COMMENT -> 2;
        };
    }
}
