package exceptions;

import constants.Constants;

public class RemoveException extends RuntimeException{
    public RemoveException(){
        super(Constants.stringIncorrectFormatMessage(Constants.REMOVE_INPUT.strip()));
    }
}