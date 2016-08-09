package org.pepsik.controller.button;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Enum represents an input number with backspace and limit number logic. Used in Controller when input digits
 */
public enum InputNumber {
    NUMBER_1("1"),
    NUMBER_2("2"),
    NUMBER_3("3"),
    NUMBER_4("4"),
    NUMBER_5("5"),
    NUMBER_6("6"),
    NUMBER_7("7"),
    NUMBER_8("8"),
    NUMBER_9("9"),
    NUMBER_0("0");

    /**
     * Maximum input number
     */
    private static final int MAX_DIGITS = 16;
    /**
     * Scale of input number to move point
     */
    private static short scale;

    /**
     * Input number
     */
    private static BigDecimal input;

    /**
     * Number represents enum constant
     */
    private BigDecimal value;
    /**
     * JavaFX number button
     */
    private Button button;

    /**
     * Returns input number
     * @return actual input number
     */
    public static BigDecimal getInput() {
        return input;
    }

    /**
     * Sets JavaFX buttons to enum constants
     * @param buttons javaFX node buttons
     */
    public static void setButtons(Set<Button> buttons) {
        for (Button button : buttons) {
            try {
                valueOf(button.getId().toUpperCase()).button = button;
            } catch (IllegalArgumentException e) { //todo refact
                /*nothing*/
            }
        }
    }

    /**
     * Adds digit to input number
     * @param event input digit event
     */
    public static void addDigit(ActionEvent event) {
        Button button = (Button) event.getSource();
        for (InputNumber nb : values()) {
            if (nb.button == button) {
                addToInput(nb.value);
            }
        }
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
     * Backspace operation on input number
     */
    public static void backspace() {
        if (input != null) {
            input = input.divideToIntegralValue(BigDecimal.TEN);
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
     * @param value input digit
     */
    private static void addToInput(BigDecimal value) {
        if (input == null) {
            input = value;
        } else if (canInput(input)) {
            if (scale != 0) {
                input = input.add(value.movePointLeft(scale));
                scale++;
            } else {
                input = value.add(input.multiply(BigDecimal.TEN));
            }
        }
    }

    /**
     * Checks if limit input digits is reached
     * @param n number to check
     * @return bool
     */
    private static boolean canInput(BigDecimal n) {
        n = n.stripTrailingZeros();
        return n.precision() < MAX_DIGITS;
    }

    InputNumber(String value) {
        this.value = new BigDecimal(value);
    }
}
