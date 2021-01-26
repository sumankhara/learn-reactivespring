package com.learnreactivespring.fluxandmono;

public class CustomException extends Exception{

    private String message;

    public CustomException(Throwable e) {
        super(e);
        this.message = e.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
