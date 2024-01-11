package com.bbc.zuber.exception;

public class RideAssignmentNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "RideAssignment with id: %d not found!";

    public RideAssignmentNotFoundException(long id) {
        super(String.format(ERROR_MESSAGE, id));
    }
}
