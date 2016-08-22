package org.pepsik.model.helper;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import org.pepsik.controller.button.CalculatorButton;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class represents calculator button with logic to emulate push button in JavaFX Thread to avoid exceptions. Used only in tests
 */
public enum UITestButton {
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
    SUBTRACT("-"),
    DIVIDE("/"),
    MULTIPLY("*"),

    SQUARE("square"),
    SQUARE_ROOT("sqrt"),
    FRACTION("fraction"),
    NEGATE("negate"),
    PERCENT("percent"),

    EQUAL("="),
    POINT("."),

    CLEAR_ENTRY("CE"),
    CLEAR_ALL("C"),
    BACKSPACE("<"),

    MEMORY_CLEAR("MC"),
    MEMORY_RECALL("MR"),
    MEMORY_ADD("M+"),
    MEMORY_SUBTRACT("M-"),
    MEMORY_SAVE("MS");

    private static Map<String, UITestButton> buttonMap = new HashMap<>();

    private String value;
    private Button button;

    UITestButton(String value) {
        this.value = value;
    }

    public static void setUIButtons() {
        for (UITestButton button : values()) {
            String buttonName = button.name();

            button.button = CalculatorButton.valueOf(buttonName).getButton();
            buttonMap.put(button.value, button);
        }
    }

    public static UITestButton getUIButton(String input) {
        UITestButton button = buttonMap.get(input);
        if (button != null) {
            return button;
        }
        throw new IllegalStateException("No match button found to map to" + input);
    }

    /**
     * Gets button Css style classes
     *
     * @return list of css classes
     */
    public ObservableList<String> getStyleClass() {
        return button.getStyleClass();
    }

    public void push() {
        Platform.runLater(() -> button.fire());
    }
}
