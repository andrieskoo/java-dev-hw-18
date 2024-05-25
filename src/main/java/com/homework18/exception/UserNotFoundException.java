package com.homework18.exception;

import java.util.UUID;

public class UserNotFoundException extends Exception{
    private static final String USER_NOT_FOUND_EXCEPTION_TEXT = "User with id = %s not found.";
    private static final String USEREMAIL_NOT_FOUND_EXCEPTION_TEXT = "User with username = %s not found.";
    private static final String CAN_NOT_UPDATE_USER_WITHOUT_ID_EXCEPTION_TEXT = "Can not found user without id.";

    public UserNotFoundException() {
        super(CAN_NOT_UPDATE_USER_WITHOUT_ID_EXCEPTION_TEXT);
    }

    public UserNotFoundException(UUID customerId) {
        super(String.format(USER_NOT_FOUND_EXCEPTION_TEXT, customerId));
    }
    public UserNotFoundException(String username) {
        super(String.format(USEREMAIL_NOT_FOUND_EXCEPTION_TEXT, username));
    }
}
