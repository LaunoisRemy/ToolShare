package business.exceptions;

/**
 * Exception that is raised when the user enter a wrong password when he logs in.
 */
public class WrongPasswordException extends Exception{
    /**
     * WrongPasswordException's constructor that create an exception with a special message
     * @param error_msg, the custom error message
     */
    public WrongPasswordException (String error_msg) {
        super(error_msg);
    }
}
