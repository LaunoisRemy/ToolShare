package business.system.offer;

import java.util.Locale;

/**
 * Enumeration of possibilities stats of a tool
 */
public enum ToolSate {
    EXCELLENT,
    GOOD,
    USED,
    BAD,
    DAMAGED;

    /**
     * Transform enum in string
     * @return
     */
    public String getString(){
        return String.valueOf(this).toLowerCase(Locale.ROOT);
    }

    /**
     * Get type by a string
     * @param type
     * @return
     */
    public static ToolSate getType(String type){
        return switch (type.toLowerCase(Locale.ROOT)) {
            case "excellent" -> EXCELLENT;
            case "good" -> GOOD;
            case "used" -> USED;
            case "bad" -> BAD;
            case "damaged" -> DAMAGED;
            default -> null;
        };
    }
}
