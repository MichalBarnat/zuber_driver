package com.bbc.zuber.exception;

public class DriverNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Driver with id %d not found!";

    public DriverNotFoundException(long id) {
        super(String.format(ERROR_MESSAGE, id));
    }
}
