package exceptions;

import constants.Constants;

public class ResolutionException extends RuntimeException {
    public ResolutionException() {
        super(Constants.stringIncorrectFormatMessage(Constants.RES_INPUT.strip()));
    }
}