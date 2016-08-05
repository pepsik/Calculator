package org.pepsik.controller;

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
                this.getButton().setStyle("-fx-font: 20px arial;");
            } else {
                this.getButton().setStyle("-fx-font: 16px arial;");
            }
        }
    },
    SUBTRACT("-") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                this.getButton().setStyle("-fx-font: 20px arial;");
            } else {
                this.getButton().setStyle("-fx-font: 16px arial;");
            }
        }
    },
    DIVIDE("/") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                this.getButton().setStyle("-fx-font: 20px arial;");
            } else {
                this.getButton().setStyle("-fx-font: 16px arial;");
            }
        }
    },
    MULTIPLY("*") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                this.getButton().setStyle("-fx-font: 20px arial;");
            } else {
                this.getButton().setStyle("-fx-font: 16px arial;");
            }
        }
    },
    EQUAL("=") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                this.getButton().setStyle("-fx-font: 20px arial;");
            } else {
                this.getButton().setStyle("-fx-font: 16px arial;");
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
                this.getButton().setStyle("-fx-font: 18px arial;");
            } else {
                this.getButton().setStyle("-fx-font: 14px arial;");
            }
        }
    },
    SQUARE_ROOT("√") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                this.getButton().setStyle("-fx-font: 18px arial;");
            } else {
                this.getButton().setStyle("-fx-font: 14px arial;");
            }
        }
    },
    FRACTION("1/X") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                this.getButton().setStyle("-fx-font: 18px arial;");
            } else {
                this.getButton().setStyle("-fx-font: 14px arial;");
            }
        }
    },
    NEGATE("+-") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                this.getButton().setStyle("-fx-font: 20px arial;");
            } else {
                this.getButton().setStyle("-fx-font: 16px arial;");
            }
        }
    },
    PERCENT("%") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                this.getButton().setStyle("-fx-font: 18px arial;");
            } else {
                this.getButton().setStyle("-fx-font: 14px arial;");
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
                this.getButton().setStyle("-fx-font: 24px arial;");
            } else {
                this.getButton().setStyle("-fx-font: 18px arial;");
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
                this.getButton().setStyle("-fx-font: 16px arial;");
            } else {
                this.getButton().setStyle("-fx-font: 14px arial;");
            }
        }
    },
    CLEAR_ALL("C") {
        @Override
        protected void resize(double width, double height) {
            if (Double.compare(width, 270) > 0
                    && Double.compare(height, 450) > 0) {
                this.getButton().setStyle("-fx-font: 16px arial;");
            } else {
                this.getButton().setStyle("-fx-font: 14px arial;");
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
                this.getButton().setStyle("-fx-font: 16px arial;");
            } else {
                this.getButton().setStyle("-fx-font: 14px arial;");
            }
        }
    },

    /**
     * Memory button constants
     */
    MEMORY_CLEAR("MC") {
        @Override
        protected void resize(double width, double height) {
            this.getButton().setStyle(MEMORY_STYLE);
        }
    },
    MEMORY_RECALL("MR") {
        @Override
        protected void resize(double width, double height) {
            this.getButton().setStyle(MEMORY_STYLE);
        }
    },
    MEMORY_ADD("M+") {
        @Override
        protected void resize(double width, double height) {
            this.getButton().setStyle(MEMORY_STYLE);
        }
    },
    MEMORY_SUBTRACT("M-") {
        @Override
        protected void resize(double width, double height) {
            this.getButton().setStyle(MEMORY_STYLE);
        }
    },
    MEMORY_SAVE("MS") {
        @Override
        protected void resize(double width, double height) {
            this.getButton().setStyle(MEMORY_STYLE);
        }
    };

    /**
     * Memory default font css style
     */
    private static final String MEMORY_STYLE = "-fx-font: 12px arial;";

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
     * @param buttons collection FXML buttons
     */
    public static void setButtons(Set<Button> buttons) {
        for (Button button : buttons) {
            valueOf(button.getId().toUpperCase()).button = button;
        }
    }

    public Button getButton() {
        return button;
    }

    /**
     * Return string button presentation by FXML Button
     * @param button FXML Node Button
     * @return String represents correspond button
     *
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

    /**
     * Default resize (0-9 buttons)
     */
    protected void resize(double width, double height) {
        if (Double.compare(width, 270) > 0
                && Double.compare(height, 450) > 0) {
            this.button.setStyle("-fx-font: 24px arial;");
        } else {
            this.button.setStyle("-fx-font: 18px arial;");
        }
    }

    /**
     * Initialize resize logic for all buttons
     * @param width new stage width
     * @param height new stage height
     */
    public static void resizeButtons(double width, double height) {
        for (CalculatorButton value : values()) {
            value.resize(width, height);
        }
    }

    @Override
    public String toString() {
        return "CalculatorButton{" +
                ", button=" + button +
                '}';
    }
}
