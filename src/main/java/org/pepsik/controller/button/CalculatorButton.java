package org.pepsik.controller.button;

import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;

/**
 * This enum represents calculator button. Each constant consist values as String and JavaFX node button which used in Controller class
 * to handle button events and have resize font logic for each button.
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
                    && Double.compare(height, Constants.BOUNDARY_HEIGHT) > 0) {
                changeCssClass("binary_small_font", "binary_big_font");
            } else {
                changeCssClass("binary_big_font", "binary_small_font");
            }
        }
    },
    SUBTRACT("-") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, Constants.BOUNDARY_WIDTH) > 0
                    && Double.compare(height, Constants.BOUNDARY_HEIGHT) > 0) {
                changeCssClass("binary_small_font", "binary_big_font");
            } else {
                changeCssClass("binary_big_font", "binary_small_font");
            }
        }
    },
    DIVIDE("/") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, Constants.BOUNDARY_WIDTH) > 0
                    && Double.compare(height, Constants.BOUNDARY_HEIGHT) > 0) {
                changeCssClass("binary_small_font", "binary_big_font");
            } else {
                changeCssClass("binary_big_font", "binary_small_font");
            }
        }
    },
    MULTIPLY("*") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, Constants.BOUNDARY_WIDTH) > 0
                    && Double.compare(height, Constants.BOUNDARY_HEIGHT) > 0) {
                changeCssClass("binary_small_font", "binary_big_font");
            } else {
                changeCssClass("binary_big_font", "binary_small_font");
            }
        }
    },
    EQUAL("=") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, Constants.BOUNDARY_WIDTH) > 0
                    && Double.compare(height, Constants.BOUNDARY_HEIGHT) > 0) {
                changeCssClass("binary_small_font", "binary_big_font");
            } else {
                changeCssClass("binary_big_font", "binary_small_font");
            }
        }
    },

    /**
     * Button unary operation constants
     */
    SQUARE("sqr") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, Constants.BOUNDARY_WIDTH) > 0
                    && Double.compare(height, Constants.BOUNDARY_HEIGHT) > 0) {
                changeCssClass("unary_small_font", "unary_big_font");
            } else {
                changeCssClass("unary_big_font", "unary_small_font");
            }
        }
    },
    SQUARE_ROOT("√") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, Constants.BOUNDARY_WIDTH) > 0
                    && Double.compare(height, Constants.BOUNDARY_HEIGHT) > 0) {
                changeCssClass("unary_small_font", "unary_big_font");
            } else {
                changeCssClass("unary_big_font", "unary_small_font");
            }
        }
    },
    FRACTION("1/") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, Constants.BOUNDARY_WIDTH) > 0
                    && Double.compare(height, Constants.BOUNDARY_HEIGHT) > 0) {
                changeCssClass("unary_small_font", "unary_big_font");
            } else {
                changeCssClass("unary_big_font", "unary_small_font");
            }
        }
    },
    NEGATE("negate") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, Constants.BOUNDARY_WIDTH) > 0
                    && Double.compare(height, Constants.BOUNDARY_HEIGHT) > 0) {
                changeCssClass("unary_small_font", "unary_big_font");
            } else {
                changeCssClass("unary_big_font", "unary_small_font");
            }
        }
    },
    PERCENT("%") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, Constants.BOUNDARY_WIDTH) > 0
                    && Double.compare(height, Constants.BOUNDARY_HEIGHT) > 0) {
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
            if (Double.compare(width, Constants.BOUNDARY_WIDTH) > 0
                    && Double.compare(height, Constants.BOUNDARY_HEIGHT) > 0) {
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
            if (Double.compare(width, Constants.BOUNDARY_WIDTH) > 0
                    && Double.compare(height, Constants.BOUNDARY_HEIGHT) > 0) {
                changeCssClass("clear_small_font", "clear_big_font");
            } else {
                changeCssClass("clear_big_font", "clear_small_font");
            }
        }
    },
    CLEAR_ALL("C") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, Constants.BOUNDARY_WIDTH) > 0
                    && Double.compare(height, Constants.BOUNDARY_HEIGHT) > 0) {
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
            if (Double.compare(width, Constants.BOUNDARY_WIDTH) > 0
                    && Double.compare(height, Constants.BOUNDARY_HEIGHT) > 0) {
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
     * Boundary height and width when button and display fonts are changes
     */
    private static class Constants {
        static final int BOUNDARY_WIDTH = 270;
        static final int BOUNDARY_HEIGHT = 450;
    }

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
     * @param root node root hierarchy
     */
    public static void setButtons(Parent root) {
        for (CalculatorButton cb : values()) {
            cb.button = (Button) root.lookup("#" + cb.name().toLowerCase());
            if (cb.button == null){
                throw new RuntimeException("Button NOT FOUND! - " + cb.name());
            }
        }
    }

    /**
     * Initialize resize logic for all button
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
        throw new IllegalArgumentException("No match button found to " + button);
    }

    /**
     * Default resize (0-9 button)
     */
    protected void resize(double width, double height) {
        if (Double.compare(width, Constants.BOUNDARY_WIDTH) > 0
                && Double.compare(height, Constants.BOUNDARY_HEIGHT) > 0) {
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

    public Button getButton() {
        return button;
    }

    @Override
    public String toString() {
        return "CalculatorButton{" +
                ", button=" + button +
                '}';
    }
}
