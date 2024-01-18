package com.bbc.zuber.exception;

import java.util.UUID;

public class DriverUuidNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Driver with uuid: %s not found!";
    public DriverUuidNotFoundException(UUID driverUuid) {
        super(String.format(ERROR_MESSAGE, driverUuid));
    }
}
