package org.pepsik.model.helper;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 * Helper class represents calculator buttons with logic to emulate push button in JavaFX Thread to avoid exceptions. Used only in tests
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

    private String shortName;
    private Button button;

    UITestButton(String value) {
        this.shortName = value;
    }

    public static void setUIButtons(Scene scene) {
        for (UITestButton button : values()) {
            button.button = (Button) scene.lookup("#" + button.name().toLowerCase());
        }
    }

    public static UITestButton getUIButton(String input) {
        for (UITestButton button : values()) {
            if (button.shortName.equals(input)) {
                return button;
            }
        }
        throw new IllegalArgumentException("No match buttons found to " + input);
    }

    public static String getShortName(String input) {
        for (UITestButton button : values()) {
            if (button.shortName.equals(input)) {
                return button.name().toLowerCase();
            }
        }
        throw new IllegalArgumentException("No match buttons found to " + input);
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

    public String getShortName() {
        return shortName;
    }
}
