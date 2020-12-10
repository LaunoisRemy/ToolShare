package business.exceptions;

/**
 * Exception that is raised if the user is banned and so he can't access to the app
 */
public class UserBannedException extends Exception{

    /**
     * UserBannedException's constructor that create an exception with a special message
     * @param error_msg, the custom error message
     */
    public UserBannedException(String error_msg) {
        super(error_msg);
    }
}
