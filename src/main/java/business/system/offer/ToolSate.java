package business.system.offer;

import business.system.ScoreType;

import java.util.Locale;

public enum ToolSate {
    EXCELLENT,
    GOOD,
    USED,
    BAD,
    DAMAGED;

    public String getString(){
        return String.valueOf(this).toLowerCase(Locale.ROOT);
    }

    public static ToolSate getType(String type){
        return switch (type.toLowerCase(Locale.ROOT)) {
            case "excellent" -> EXCELLENT;
            case "good" -> GOOD;
            case "user" -> USED;
            case "bad" -> BAD;
            case "damaged" -> DAMAGED;
            default -> null;
        };
    }
}
