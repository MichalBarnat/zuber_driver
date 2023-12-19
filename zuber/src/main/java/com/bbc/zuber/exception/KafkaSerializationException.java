package com.bbc.zuber.exception;

public class KafkaSerializationException extends RuntimeException {
    public KafkaSerializationException() {
    }

    public KafkaSerializationException(String message) {
        super(message);
    }
}
