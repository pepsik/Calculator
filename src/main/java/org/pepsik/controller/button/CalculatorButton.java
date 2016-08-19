package org.pepsik.controller.button;

import javafx.scene.control.Button;
import org.pepsik.controller.exception.ButtonNotExistException;

import java.util.HashMap;
import java.util.Map;

/**
 * This enum represents calculator button. Each constant consist string representation and button node which used in Controller class
 * to handle button events
 */
public enum CalculatorButton {
    NUMBER_1("1"),
    NUMBER_2("2"),
    NUMBER_3("3"),
    NUMBER_4("4"),
    NUMBER_5("5"),
    NUMBER_6("6"),
    NUMBER_7("7"),
    NUMBER_8("8"),
    NUMBER_9("9"),
    NUMBER_0("0"),

    ADD("+"),
    SUBTRACT("−"),
    DIVIDE("÷"),
    MULTIPLY("×"),
    EQUAL("="),

    SQUARE("sqr"),
    SQUARE_ROOT("√"),
    FRACTION("1/"),
    NEGATE("negate"),
    PERCENT("%"),

    POINT("."),

    CLEAR_ENTRY("CE"),
    CLEAR_ALL("C"),
    BACKSPACE("<"),

    MEMORY_CLEAR("MC"),
    MEMORY_RECALL("MR"),
    MEMORY_ADD("M+"),
    MEMORY_SUBTRACT("M-"),
    MEMORY_SAVE("MS");

    /**
     * String presentation operation
     */
    private String value;

    /**
     * Button presentation operation
     */
    private Button button;

    /**
     * Map contains Javafx Button mapping to CalculatorButton
     */
    private static Map<Button, CalculatorButton> buttonMap = new HashMap<>();

    CalculatorButton(String value) {
        this.value = value;
    }

    /**
     * Return CalculatorButton presentation for JavaFx Button
     *
     * @param button FXML Node Button
     * @return String represents correspond button
     * @throws ButtonNotExistException in case button not found
     */
    public static CalculatorButton valueOf(Button button) {
        CalculatorButton cb = buttonMap.get(button);
        if (cb != null) {
            return cb;
        }
        throw new ButtonNotExistException("No match CalculatorButton found to map to" + button);
    }

    public void setButton(Button button) {
        this.button = button;
        buttonMap.put(button, this);
    }

    public Button getButton() {
        return button;
    }

    /**
     * Returns button name
     * @return button name
     */
    public String getValue() {
        return value;
    }
}
