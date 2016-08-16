package org.pepsik.controller.util;

import javafx.event.ActionEvent;

import java.math.BigDecimal;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;

/**
 * Class represents an input number which used in Controller {@link org.pepsik.controller.CalculatorController#handleDigitAction(ActionEvent)} method
 */
public class InputNumber {
    /**
     * Maximum input number
     */
    private static final int MAX_DIGITS = 16;
    /**
     * Constant of input number
     */
    private static short scale;

    /**
     * Input number
     */
    private static BigDecimal input;

    /**
     * Add input digit to input number
     *
     * @param value input digit
     */
    public static void addToInput(BigDecimal value) {
        if (input == null) {
            input = value;
            return;
        }

        boolean hasPermission = canInput(input); //todo name
        if (hasPermission) {
            if (scale != 0) {
                input = input.add(value.movePointLeft(scale));
                scale++;
            } else {
                input = value.add(input.multiply(TEN));
            }
        }
    }

    /**
     * Returns input number
     *
     * @return actual input number
     */
    public static BigDecimal getInput() {
        return input;
    }

    /**
     * Adds point to input number
     */
    public static void addPoint() {
        if (input == null) {
            input = ZERO;
        }
        scale++;
    }

    /**
     * Verify is point set
     *
     * @return bool SCALE if was set
     */
    public static boolean isPointSet() {
        return scale != 0;
    }

    /**
     * get scale of input number
     *
     * @return scale
     */
    public static int getScale() {
        return scale;
    }

    /**
     * Backspace operation on input number
     */
    public static void backspace() {
        if (input != null) {
            if (scale == 0) {
                input = input.divideToIntegralValue(TEN);
            } //convert to string and remove last digit
            else {
                String temp = input.toString();  /// TODO: add comment
                if (temp.length() > 1) {
                    input = new BigDecimal(temp.substring(0, temp.length() - 1));
                } else {
                    input = ZERO;
                }
                scale--;
            }
        }
    }

    /**
     * Clear input number
     */
    public static void clearInput() {
        scale = 0;
        input = null;
    }

    /**
     * Checks if limit input digits is reached
     *
     * @param n number to check
     * @return bool
     */
    private static boolean canInput(BigDecimal n) {
        return n.precision() - scale < MAX_DIGITS && scale < MAX_DIGITS;
    }
}
