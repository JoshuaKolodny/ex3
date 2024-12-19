package exceptions;

import constants.Constants;

/**
 * The BoundariesResolutionException class represents an exception that is thrown
 * when an attempt to change the resolution exceeds the allowed boundaries.
 *
 * <p>This exception is a runtime exception, indicating an invalid operation
 * performed on the resolution settings.</p>
 *
 * @author Joshua Kolodny, Itamar Lev Ari
 */
public class BoundariesResolutionException extends RuntimeException {

    /**
     * Constructs a new BoundariesResolutionException with a default message.
     * The message is retrieved from the Constants class.
     */
    public BoundariesResolutionException() {
        super(Constants.BOUNDARIES_COMMAND);
    }
}
