package com.adam.kiss.jbugger.exceptions;

import com.adam.kiss.jbugger.enums.BugNotValidErrorMessages;

public class BugNotValidException extends Exception {
    public BugNotValidException(BugNotValidErrorMessages errorMessage){
        super(errorMessage.toString());
    }
}
