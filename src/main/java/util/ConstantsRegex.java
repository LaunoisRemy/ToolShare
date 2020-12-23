package util;

import java.util.regex.Pattern;

public class ConstantsRegex {
    private ConstantsRegex(){}

    private static final Pattern EMAIL_REGEX = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    private static final Pattern NAME_REGEX = Pattern.compile("^(([A-Za-z]+[,.]?[ ]?|[a-z]+['-]?)+)$");
    private static final Pattern PHONE_REGEX = Pattern.compile("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$");
    private static final Pattern CODE_REGEX = Pattern.compile("^[a-zA-Z0-9]{6}$");
    private static final Pattern FLOAT_REGEX = Pattern.compile("^([+-]?\\d*\\.?\\d*)$");

    public static boolean match(Pattern p, String text){
        return p.matcher(text).find();
    }

    public static boolean matchEmail(String mail){
        return match(EMAIL_REGEX,mail);
    }
    public static boolean matchNameRegex(String name){
        return match(NAME_REGEX,name);
    }
    public static boolean matchPhoneRegex(String phone){
        return match(PHONE_REGEX,phone);
    }
    public static boolean matchCodeRegex(String code){
        return match(CODE_REGEX,code);
    }
    public static boolean matchFloatRegex(String number) { return match(FLOAT_REGEX,number); }
}
