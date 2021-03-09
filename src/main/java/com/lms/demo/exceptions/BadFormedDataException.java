package com.lms.demo.exceptions;

public class BadFormedDataException extends RuntimeException{

    protected String from;

    public BadFormedDataException(){
    }

    public BadFormedDataException(String form , String message) {
        super(message);
        this.from = form;
    }
    public BadFormedDataException(String message){
        super(message);
    }

    public String getFrom() {
        return from;
    }
}
