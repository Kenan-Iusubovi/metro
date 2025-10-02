package com.solvd.metro.application.exception;

public class CarriageNotOperationalException extends RuntimeException {

    public CarriageNotOperationalException(String message) {
        super(message);
    }

    public CarriageNotOperationalException(String message, Throwable cause) {
        super(message, cause);
    }
}
