package com.i2i.app.customexception;

import java.lang.Exception;

/**
 * This class represents custom exception handling for exceptions that occur within student-related operations.
 */
public class StudentException extends Exception {
    public StudentException() {}

    public StudentException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentException(String message) {
        super(message);
    }
}
