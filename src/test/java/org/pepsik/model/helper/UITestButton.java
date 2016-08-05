package org.pepsik.model.helper;

import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 * Created by Berezovyi Aleksandr on 8/4/2016.
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

    private static Scene scene;

    private String value;
    private Button button;

    public static void setScene(Scene scene) {
        UITestButton.scene = scene;

        for (UITestButton button : values()) {
            button.button = (Button) scene.lookup("#" + button.name().toLowerCase());
        }
    }

    UITestButton(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void fire() {
        this.button.fire();
    }

    public static Button getButton(String input) {
        for (UITestButton button : values()) {
            if (button.getValue().equals(input)) {
                return button.button;
            }
        }
        throw new IllegalArgumentException("No match buttons found to " + input);
    }

    public Button getButton(){
        return button;
    }

    public static String isExist(String input) {
        for (UITestButton button : values()) {
            if (button.getValue().equals(input)) {
                return button.name().toLowerCase();
            }
        }
        throw new IllegalArgumentException("No match buttons found to " + input);
    }
}
