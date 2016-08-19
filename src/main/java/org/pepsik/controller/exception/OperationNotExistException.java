package org.pepsik.controller.exception;

/**
 * Created by admin on 8/19/2016.
 */
public class OperationNotExistException extends RuntimeException {
    public OperationNotExistException(String message) {
        super(message);
    }
}
