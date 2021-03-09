package com.lms.demo.exceptions;

public class InvalidOperationException extends RuntimeException{

    public InvalidOperationException(){
    }

    public InvalidOperationException(String message){
        super(message);
    }
}
