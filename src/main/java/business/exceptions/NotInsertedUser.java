package business.exceptions;

public class NotInsertedUser extends Exception{
    public NotInsertedUser(String error_msg) {
        super(error_msg);
    }
}
