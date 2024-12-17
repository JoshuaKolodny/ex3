package exceptions;

import constants.Constants;

public class BoundariesResolutionException extends RuntimeException {
    public BoundariesResolutionException() {
        super(Constants.BOUNDARIES_COMMAND);
    }
}
