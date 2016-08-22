package org.pepsik.model.exception;

/**
 * Thrown when an application attempts to use illegal operand in operation.
 */
public class IllegalOperandException extends Exception {
    public IllegalOperandException(String message) {
        super(message);
    }
}
