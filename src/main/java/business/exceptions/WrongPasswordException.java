package business.exceptions;

public class WrongPasswordException extends Exception{
    public WrongPasswordException (String error_msg) {
        super(error_msg);
    }
}
