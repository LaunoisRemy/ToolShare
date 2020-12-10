package business.exceptions;

/**
 * Exception that is raised bad insertions in database like bad creations, updates etc.
 */
public class BadInsertionInBDDException extends Exception{

    /**
     * BadInsertionInBDDException's constructor that create an exception with a special message
     * @param error_msg, the custom error message
     */
    public BadInsertionInBDDException(String error_msg) {
        super(error_msg);
    }
}
