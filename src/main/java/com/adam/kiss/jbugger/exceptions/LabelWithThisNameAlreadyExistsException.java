package com.adam.kiss.jbugger.exceptions;

public class LabelWithThisNameAlreadyExistsException extends Exception {
    public LabelWithThisNameAlreadyExistsException(String labelName){
        super("\"" + labelName + "\" label name already exists!");
    }
}
