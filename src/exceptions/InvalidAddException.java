package exceptions;

import constants.Constants;

public class InvalidAddException extends RuntimeException{
    public InvalidAddException(){
        super(Constants.stringIncorrectFormatMessage(Constants.ADD_INPUT.strip()));
    }
}