package business.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String error_msg) {
        super(error_msg);
    }
}
