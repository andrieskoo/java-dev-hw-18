package com.homework18.exception;


public class IllegalArgumentException extends Exception{
    private static final String ILLEGAL_ARGUMENT_PASSED_TO_METHOD_TEXT = "Illegal or unsuitable argument passed to a method, argument is %s";
    private static final String ILLEGAL_ARGUMENT = "Illegal argument exception. %s";


    public IllegalArgumentException(String argument) {
        super(String.format(ILLEGAL_ARGUMENT_PASSED_TO_METHOD_TEXT, argument));
    }
    public IllegalArgumentException() {
        super(ILLEGAL_ARGUMENT);
    }

}
