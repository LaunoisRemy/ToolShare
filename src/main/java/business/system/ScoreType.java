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
}
