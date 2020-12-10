package business.exceptions;

/**
 * Exception that is raised if the object is not found (f.ex : in database)
 */
public class ObjectNotFoundException extends Exception {
    /**
     * ObjectNotFoundException's constructor that create an exception with a special message
     * @param error_msg, the custom error message
     */
    public ObjectNotFoundException(String error_msg) {
        super(error_msg);
    }
}
