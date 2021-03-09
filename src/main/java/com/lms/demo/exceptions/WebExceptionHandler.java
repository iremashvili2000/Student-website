package com.lms.demo.exceptions;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class WebExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity handleUserNotFoundException(final NotFoundException ex) {

        return new ResponseEntity(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity invalidOperation(InvalidOperationException invalidOperationException){

        return new ResponseEntity(invalidOperationException.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadFormedDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity handleBadFormedData(BadFormedDataException bfe) {
        return new ResponseEntity(
                bfe.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

}
