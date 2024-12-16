package exceptions;

import constants.Constants;

public class InvalidRemoveException extends RuntimeException{
    public InvalidRemoveException(){
        super(Constants.stringIncorrectFormatMessage(Constants.REMOVE_INPUT.strip()));
    }
}