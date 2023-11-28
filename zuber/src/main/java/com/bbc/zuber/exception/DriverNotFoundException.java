package com.bbc.zuber.exception;

public class DriverNotFoundException extends RuntimeException{
    public DriverNotFoundException() {
    }

    public DriverNotFoundException(String message) {
        super(message);
    }
}
