package org.pepsik.controller.button;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;

import java.util.Set;

/**
 * This enum represents calculator buttons. Each constant consist actual values (number or operation)  witch used in Model class in expression execution
 * and corresponding button from FXML and resize logic for it.
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
    ADD("+") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                changeCssClass("binary_small_font", "binary_big_font");
            } else {
                changeCssClass("binary_big_font", "binary_small_font");
            }
        }
    },
    SUBTRACT("-") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                changeCssClass("binary_small_font", "binary_big_font");
            } else {
                changeCssClass("binary_big_font", "binary_small_font");
            }
        }
    },
    DIVIDE("/") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                changeCssClass("binary_small_font", "binary_big_font");
            } else {
                changeCssClass("binary_big_font", "binary_small_font");
            }
        }
    },
    MULTIPLY("*") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                changeCssClass("binary_small_font", "binary_big_font");
            } else {
                changeCssClass("binary_big_font", "binary_small_font");
            }
        }
    },
    EQUAL("=") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                changeCssClass("binary_small_font", "binary_big_font");
            } else {
                changeCssClass("binary_big_font", "binary_small_font");
            }
        }
    },

    /**
     * Button unary operation constants
     */
    SQUARE("X²") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                changeCssClass("unary_small_font", "unary_big_font");
            } else {
                changeCssClass("unary_big_font", "unary_small_font");
            }
        }
    },
    SQUARE_ROOT("√") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                changeCssClass("unary_small_font", "unary_big_font");
            } else {
                changeCssClass("unary_big_font", "unary_small_font");
            }
        }
    },
    FRACTION("1/X") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                changeCssClass("unary_small_font", "unary_big_font");
            } else {
                changeCssClass("unary_big_font", "unary_small_font");
            }
        }
    },
    NEGATE("+-") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                changeCssClass("unary_small_font", "unary_big_font");
            } else {
                changeCssClass("unary_big_font", "unary_small_font");
            }
        }
    },
    PERCENT("%") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                changeCssClass("unary_small_font", "unary_big_font");
            } else {
                changeCssClass("unary_big_font", "unary_small_font");
            }
        }
    },

    /**
     * Point constant
     */
    POINT(".") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                changeCssClass("point_small_font", "point_big_font");
            } else {
                changeCssClass("point_big_font", "point_small_font");
            }
        }
    },

    /**
     * Clear button constants
     */
    CLEAR_ENTRY("CE") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                changeCssClass("clear_small_font", "clear_big_font");
            } else {
                changeCssClass("clear_big_font", "clear_small_font");
            }
        }
    },
    CLEAR_ALL("C") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                changeCssClass("clear_small_font", "clear_big_font");
            } else {
                changeCssClass("clear_big_font", "clear_small_font");
            }
        }
    },

    /**
     * Backspace button constant
     */
    BACKSPACE("<") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                changeCssClass("clear_small_font", "clear_big_font");
            } else {
                changeCssClass("clear_big_font", "clear_small_font");
            }
        }
    },

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
     * Set Button collection to constants
     *
     * @param buttons collection FXML buttons
     */
    public static void setButtons(Set<Button> buttons) {
        for (Button button : buttons) {
            valueOf(button.getId().toUpperCase()).button = button;
        }
    }

    /**
     * Initialize resize logic for all buttons
     *
     * @param width  new stage width
     * @param height new stage height
     */

    public static void resizeButtons(double width, double height) {
        for (CalculatorButton value : values()) {
            value.resize(width, height);
        }
    }

    /**
     * Return string button presentation by FXML Button
     *
     * @param button FXML Node Button
     * @return String represents correspond button
     * @throws IllegalArgumentException in case button not found
     */
    public static String valueOf(Button button) {
        for (CalculatorButton cb : values()) {
            if (cb.button.equals(button)) {
                return cb.value;
            }
        }
        throw new IllegalArgumentException("No match buttons found to " + button);
    }

    public Button getButton() {
        return button;
    }

    /**
     * Default resize (0-9 buttons)
     */
    protected void resize(double width, double height) {
        if (Double.compare(width, 270) > 0
                && Double.compare(height, 450) > 0) {
            changeCssClass("number_small_font", "number_big_font");
        } else {
            changeCssClass("number_big_font", "number_small_font");
        }
    }

    protected void changeCssClass(String oldClass, String newCss) {
        ObservableList<String> cssList = button.getStyleClass();

        cssList.remove(oldClass);

        if (!cssList.contains(newCss)) {
            cssList.add(newCss);
        }
    }

    @Override
    public String toString() {
        return "CalculatorButton{" +
                ", button=" + button +
                '}';
    }
}
