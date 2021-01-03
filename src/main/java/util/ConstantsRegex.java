package util;

import business.facade.SessionFacade;

import java.util.regex.Pattern;

/**
 * Class to save regex and contains to match pattern with string
 */
public class ConstantsRegex {
    private ConstantsRegex(){}

    private static final Pattern EMAIL_REGEX = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    private static final Pattern NAME_REGEX = Pattern.compile("^(([A-Za-z]+[,.]?[ ]?|[a-z]+['-]?)+)$");
    private static final Pattern PHONE_REGEX = Pattern.compile("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$");
    private static final Pattern CODE_REGEX = Pattern.compile("^[a-zA-Z0-9]{6}$");
    private static final Pattern FLOAT_REGEX = Pattern.compile("^([+-]?\\d*\\.?\\d*)$");

    /**
     * Generic method who match a string with a pattern
     * @param p Pattern (Regex) we want this match with the next parameter
     * @param text Text which needs to match with regex
     * @return if the text match with the pattern p
     */
    public static boolean match(Pattern p, String text){
        return p.matcher(text).find();
    }

    /**
     * Method using a regex for check email
     * @param mail text suppose to be a mail
     * @return true if the param is a mail
     */
    public static boolean matchEmail(String mail){
        return match(EMAIL_REGEX,mail);
    }

    /**
     * Use regex for name
     * @param name  Text suppose to be name
     * @return true if the param is a name
     */
    public static boolean matchNameRegex(String name){
        return match(NAME_REGEX,name);
    }

    /**
     * USe regex for a phone number
     * @param phone ext suppose to be a phoen number
     * @return true if the param is a phone number
     */
    public static boolean matchPhoneRegex(String phone){
        return match(PHONE_REGEX,phone);
    }

    /**
     * Use regex of a code
     * @param code text suppose to be code
     * @return true if the param is a code like we send in {@link #CODE_REGEX}
     */
    public static boolean matchCodeRegex(String code){
        return match(CODE_REGEX,code);
    }

    /**
     * Use regex for check a flaot
     * @param number
     * @return true if the param is a float
     */
    public static boolean matchFloatRegex(String number) { return match(FLOAT_REGEX,number); }
}
