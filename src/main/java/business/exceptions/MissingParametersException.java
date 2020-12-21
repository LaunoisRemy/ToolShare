package business.exceptions;

/**
 * Exception that is raised if the user didn't enter all the information needed (for example : the title of an offer)
 */
public class MissingParametersException extends Exception {
    /**
     * MissingParametersException's constructor that create an exception with a special message
     * @param error_msg, the custom error message
     */
    public MissingParametersException(String error_msg){
        super(error_msg);
    }

}
