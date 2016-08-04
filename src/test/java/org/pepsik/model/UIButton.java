package org.pepsik.model;

/**
 * Created by Berezovyi Aleksandr on 8/4/2016.
 */
public enum UIButton {
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

    private String value;

    UIButton(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String isExist(String input) {
        for (UIButton button : values()) {
            if (button.getValue().equals(input)) {
                return button.name().toLowerCase();
            }
        }
        throw new IllegalArgumentException("No match buttons found to " + input);
    }
}
