package com.i2i.app.customexception;

public class ValidationException extends RuntimeException {
    public ValidationException (String message) {
        super(message);
    }
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
