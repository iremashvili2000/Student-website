package com.lms.demo.exceptions;

public class DontFoundException extends RuntimeException{

      private String from;

    public DontFoundException(){
    }

    public DontFoundException(String form , String message) {
        super(message);
        this.from = form;
    }
    public DontFoundException(String message){
        super(message);
    }



    public void getForm(String from) {
        this.from = from;
    }
}
