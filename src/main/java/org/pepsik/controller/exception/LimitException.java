package org.pepsik.controller.exception;

/**
 * Created by admin on 8/19/2016.
 */
public class LimitException extends ArithmeticException {
    public LimitException(String s) {
        super(s);
    }
}