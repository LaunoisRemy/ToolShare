package business.system;

import java.util.Locale;

public enum ScoreType {
    QUESTION,
    ANSWER,
    COMMENT;

    public String getString(){
        return String.valueOf(this).toLowerCase(Locale.ROOT);
    }
    public static ScoreType getType(String type){
        return switch (type.toLowerCase(Locale.ROOT)) {
            case "question" -> QUESTION;
            case "answer" -> ANSWER;
            case "comment" -> COMMENT;
            default -> null;
        };
    }

    public static ScoreType getTypeByInt(int type){
        return switch (type) {
            case 0 -> QUESTION;
            case 1 -> ANSWER;
            case 2 -> COMMENT;
            default -> null;
        };
    }
    public static int getIntByType(ScoreType type){
        return switch (type) {
            case QUESTION -> 0;
            case ANSWER -> 1;
            case COMMENT -> 2;
        };
    }
}
