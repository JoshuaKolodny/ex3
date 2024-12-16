package exceptions;

import constants.Constants;

public class AddException extends RuntimeException{
    public AddException(){
        super(Constants.stringIncorrectFormatMessage(Constants.ADD_INPUT.strip()));
    }
}