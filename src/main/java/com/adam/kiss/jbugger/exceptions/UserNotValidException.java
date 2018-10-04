package com.adam.kiss.jbugger.exceptions;

import com.adam.kiss.jbugger.enums.UserNotValidErrorMessages;

public class UserNotValidException extends Exception {

    public UserNotValidException(UserNotValidErrorMessages message) {
        super(message.toString());
    }
}
