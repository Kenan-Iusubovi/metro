package com.solvd.metro.application.exception;

public class MetroOperationException extends Exception {

    public MetroOperationException(String message) {
        super(message);
    }

    public MetroOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}