package org.pepsik.controller.button;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.pepsik.model.operation.Constant;

/**
 * This enum represents calculator button. Each constant consist values as String and JavaFX node button which used in Controller class
 * to handle button events
 */
public enum CalculatorButton {

    /**
     * Button number constants
     */
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

    /**
     * Button binary operation constants
     */
    ADD("+"),
    SUBTRACT("−"),
    DIVIDE("÷"),
    MULTIPLY("×"),
    EQUAL("="),

    /**
     * Button unary operation constants
     */
    SQUARE("sqr"),
    SQUARE_ROOT("√"),
    FRACTION("1/"),
    NEGATE("negate"),
    PERCENT("%"),

    /**
     * Point constant
     */
    POINT("."),

    /**
     * Clear button constants
     */
    @FXML
    CLEAR_ENTRY("CE"),
    CLEAR_ALL("C"),

    /**
     * Backspace button constant
     */
    BACKSPACE("<"),

    /**
     * Memory button constants
     */
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

    CalculatorButton(String value) {
        this.value = value;
    }

    /**
     * Return string button presentation by FXML Button
     *
     * @param event FXML Node Button
     * @return String represents correspond button
     * @throws IllegalArgumentException in case button not found
     */
    public static CalculatorButton valueOf(ActionEvent event) {
        Button button = (Button) event.getSource();

        for (CalculatorButton cb : values()) {
            if (cb.button.equals(button)) {
                return cb;
            }
        }
        throw new IllegalArgumentException("No match button found to " + button);
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public Button getButton() {
        return button;
    }

    public String getValue() {
        return value;
    }
}
