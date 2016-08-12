package org.pepsik.controller.buttons;

import javafx.event.ActionEvent;

import java.math.BigDecimal;

import static java.math.BigDecimal.TEN;

/**
 * Class represents an input number which used in Controller {@link org.pepsik.controller.CalculatorController#handleDigitAction(ActionEvent)} method
 */
public class InputNumber {
    /**
     * Maximum input number
     */
    private static final int MAX_DIGITS = 16;
    /**
     * Scale of input number
     */
    private static short scale;

    /**
     * Input number
     */
    private static BigDecimal input;

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
            input = new BigDecimal(0);
        }
        scale++;
    }

    /**
     * Verify is point set
     *
     * @return bool value if was set
     */
    public static boolean isPointSet() {
        return scale != 0;
    }

    public static int getScale(){
        return scale;
    }

    /**
     * Backspace operation on input number
     */
    public static void backspace() {
        if (input != null) {
            if (scale == 0) {
                input = input.divideToIntegralValue(TEN);
            } else {
                String temp = input.toString(); //crotch
                input = new BigDecimal(temp.substring(0, temp.length() - 1));
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
     * Add input digit to input number
     *
     * @param value input digit
     */
    public static void addToInput(BigDecimal value) {
        if (input == null) {
            input = value;
        } else if (canInput(input)) {
            if (scale != 0) {
                input = input.add(value.movePointLeft(scale));
                scale++;
            } else {
                input = value.add(input.multiply(TEN));
            }
        }
    }

    /**
     * Checks if limit input digits is reached
     *
     * @param n number to check
     * @return bool
     */
    private static boolean canInput(BigDecimal n) {
        n = n.stripTrailingZeros();
        return n.precision() - n.scale() < MAX_DIGITS;
    }
}
