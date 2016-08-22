package org.pepsik.controller.exception;

/**
 * Thrown when an application uses binary or unary calculation and reaches limit values.
 */
public class LimitException extends Exception {
    public LimitException(String s) {
        super(s);
    }
}
