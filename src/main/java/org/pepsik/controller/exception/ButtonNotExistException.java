package org.pepsik.controller.exception;

/**
 * Created by admin on 8/19/2016.
 */
public class ButtonNotExistException extends RuntimeException {
    public ButtonNotExistException(String message) {
        super(message);
    }
}
