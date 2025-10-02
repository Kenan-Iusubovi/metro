package com.solvd.metro.application.exception;

public class NoEmployeeAssignedException extends RuntimeException {

    public NoEmployeeAssignedException(String message) {
        super(message);
    }

    public NoEmployeeAssignedException(String message, Throwable cause) {
        super(message, cause);
    }
}
