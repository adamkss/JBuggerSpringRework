package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.exceptions.BugNotFoundException;
import com.adam.kiss.jbugger.exceptions.LabelNotFoundException;
import com.adam.kiss.jbugger.exceptions.StatusNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({BugNotFoundException.class, StatusNotFoundException.class, LabelNotFoundException.class})
    public void handleConflict() {
        // TODO: add right error message
    }


}
