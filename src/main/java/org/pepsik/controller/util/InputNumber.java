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
     * Maximum digits count
     */
    private static final int MAX_DIGITS = 16;

    /**
     * Decimal part
     */
    private static short scale;

    /**
     * Show if point set
     */
    private static boolean isPointSet = false;

    /**
     * Input number
     */
    private static BigDecimal input = ZERO;

    /**
     * Add input digit to input number
     *
     * @param digit input digit
     */
    public static void addToInput(String digit) {
        boolean hasPermission = canInput();
        if (hasPermission) {
            if (isPointSet) {
                scale++;
            }

            input = input.multiply(TEN).add(new BigDecimal(digit));
        }
    }

    /**
     * Returns input number
     *
     * @return actual input number
     */
    public static BigDecimal getInput() {
        return input.movePointLeft(scale);
    }

    /**
     * Adds point to input number
     */
    public static void addPointToInput() {
        isPointSet = true;
    }

    /**
     * Verify is point set
     *
     * @return bool SCALE if was set
     */
    public static boolean isInputPointSet() {
        return isPointSet;
    }

    /**
     * get scale of input number
     *
     * @return scale
     */
    public static int getInputScale() {
        return scale;
    }

    /**
     * Backspace operation on input number
     */
    public static void backspaceInput() {
        if (isPointSet) {
            if (scale > 0) {
                input = input.divideToIntegralValue(TEN);
                scale--;
            } else {
                isPointSet = false;
            }
        } else {
            input = input.divideToIntegralValue(TEN);
        }
    }

    /**
     * Clear input number
     */
    public static void clearInput() {
        scale = 0;
        input = ZERO;
        isPointSet = false;
    }

    /**
     * Checks if limit input digits is reached
     *
     * @return bool
     */
    private static boolean canInput() {
        return input.precision() < MAX_DIGITS && scale < MAX_DIGITS;
    }
}
