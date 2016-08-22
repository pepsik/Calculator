package org.pepsik.model.exception;

/**
 * Thrown when an application attempts to use an operation divide by zero.
 */
public class DivideByZeroException extends Exception {
    public DivideByZeroException(String message) {
        super(message);
    }
}
