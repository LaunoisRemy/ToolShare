package business.exceptions;

public class UserBannedException extends Exception{
    public UserBannedException(String error_msg) {
        super(error_msg);
    }
}
