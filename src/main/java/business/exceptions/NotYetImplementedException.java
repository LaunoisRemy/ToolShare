package business.exceptions;

/**
 * Exception that is raised when the user enter a wrong password when he logs in.
 */
public class NotYetImplementedException extends Exception{
    /**
     * Use case no yet implemented constructor that create an exception with a special message
     */
    public NotYetImplementedException() {
        super("Not yet implemented");
    }
}
