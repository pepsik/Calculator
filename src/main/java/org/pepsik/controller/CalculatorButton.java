package org.pepsik.controller;

import javafx.scene.control.Button;

import java.util.Set;

/**
 * Created by Berezovyi Aleksandr on 8/3/2016.
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

    private String value;
    private Button button;

    CalculatorButton(String value) {
        this.value = value;
    }

    public static void setButtons(Set<Button> buttons) {
        for (Button button : buttons) {
            valueOf(button.getId().toUpperCase()).button = button;
        }
    }

    public Button getButton() {
        return button;
    }

    public static String valueOf(Button button) {
        for (CalculatorButton cb : values()) {
            if (cb.button.equals(button)) {
                return cb.value;
            }
        }
        throw new IllegalArgumentException("No match buttons found to " + button);
    }

    /**
     * default resize (0-9 buttons)
     */
    protected void resize(double width, double height) {
        if (Double.compare(width, 270) > 0
                && Double.compare(height, 450) > 0) {
            this.button.setStyle("-fx-font: 24px arial;");
        } else {
            this.button.setStyle("-fx-font: 18px arial;");
        }
    }

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

    private static final String MEMORY_STYLE = "-fx-font: 12px arial;";
}
