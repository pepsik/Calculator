package org.pepsik.controller.button;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import static javafx.scene.input.KeyCode.*;

/**
 * Enum represents keyboard shortcuts and simply fires correspond calculator button
 */
public enum KeyboardShortcut {//todo simplify
    Q_BUTTON(Q) {
        @Override
        public void execute(KeyEvent event) {
            KeyCombination kb = new KeyCodeCombination(code, KeyCombination.CONTROL_DOWN);

            if (kb.match(event)) {
                CalculatorButton.MEMORY_SUBTRACT.getButton().fire();
            }
        }
    },
    P_BUTTON(P) {
        @Override
        public void execute(KeyEvent event) {
            KeyCombination kb = new KeyCodeCombination(code, KeyCombination.CONTROL_DOWN);

            if (kb.match(event)) {
                CalculatorButton.MEMORY_ADD.getButton().fire();
            }
        }
    },
    M_BUTTON(M) {
        @Override
        public void execute(KeyEvent event) {
            KeyCombination kb = new KeyCodeCombination(code, KeyCombination.CONTROL_DOWN);

            if (kb.match(event)) {
                CalculatorButton.MEMORY_SAVE.getButton().fire();
            }
        }
    },
    R_BUTTON(R) {
        @Override
        public void execute(KeyEvent event) {
            KeyCombination kb = new KeyCodeCombination(code, KeyCombination.CONTROL_DOWN);

            if (kb.match(event)) {
                CalculatorButton.MEMORY_RECALL.getButton().fire();
            } else {
                CalculatorButton.FRACTION.getButton().fire();
            }
        }
    },
    L_BUTTON(L) {
        @Override
        public void execute(KeyEvent event) {
            KeyCombination kb = new KeyCodeCombination(code, KeyCombination.CONTROL_DOWN);

            if (kb.match(event)) {
                CalculatorButton.MEMORY_CLEAR.getButton().fire();
            }
        }
    },

    F9_BUTTON(F9) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NEGATE.getButton().fire();
        }
    },
    SLASH_BUTTON(SLASH) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.DIVIDE.getButton().fire();
        }
    },
    MULTIPLY_BUTTON(MULTIPLY) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.MULTIPLY.getButton().fire();
        }
    },
    PLUS_BUTTON(PLUS) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.ADD.getButton().fire();
        }
    },
    MINUS_BUTTON(MINUS) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.SUBTRACT.getButton().fire();
        }
    },
    SQUARE_BUTTON(AT) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.SQUARE.getButton().fire();
        }
    },

    ONE_BUTTON(DIGIT1) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NUMBER_1.getButton().fire();
        }
    },
    TWO_BUTTON(DIGIT2) {
        @Override
        protected void execute(KeyEvent event) {
            KeyCombination kb = new KeyCodeCombination(code, KeyCombination.CONTROL_DOWN);

            if (kb.match(event)) {
                CalculatorButton.SQUARE_ROOT.getButton().fire();
            } else {
                CalculatorButton.NUMBER_2.getButton().fire();
            }
        }
    },
    THREE_BUTTON(DIGIT3) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NUMBER_3.getButton().fire();
        }
    },
    FOUR_BUTTON(DIGIT4) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NUMBER_4.getButton().fire();
        }
    },
    FIVE_BUTTON(DIGIT5) {
        @Override
        protected void execute(KeyEvent event) {
            KeyCombination kb = new KeyCodeCombination(code, KeyCombination.CONTROL_DOWN);

            if (kb.match(event)) {
                CalculatorButton.PERCENT.getButton().fire();
            } else {
                CalculatorButton.NUMBER_5.getButton().fire();
            }
        }
    },
    SIX_BUTTON(DIGIT6) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NUMBER_6.getButton().fire();
        }
    },
    SEVEN_BUTTON(DIGIT7) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NUMBER_7.getButton().fire();
        }
    },
    EIGHT_BUTTON(DIGIT8) {
        @Override
        protected void execute(KeyEvent event) {
            KeyCombination kb = new KeyCodeCombination(code, KeyCombination.CONTROL_DOWN);

            if (kb.match(event)) {
                CalculatorButton.MULTIPLY.getButton().fire();
            } else {
                CalculatorButton.NUMBER_8.getButton().fire();
            }
        }
    },
    NINE_BUTTON(DIGIT9) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NUMBER_9.getButton().fire();
        }
    },
    ZERO_BUTTON(DIGIT0) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NUMBER_0.getButton().fire();
        }
    },

    EQUALS_BUTTON(EQUALS) {
        @Override
        protected void execute(KeyEvent event) {
            KeyCombination kb = new KeyCodeCombination(code, KeyCombination.CONTROL_DOWN);

            if (kb.match(event)) {
                CalculatorButton.ADD.getButton().fire();
            } else {
                CalculatorButton.EQUAL.getButton().fire();
            }
        }
    },
    PERIOD_BUTTON(PERIOD) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.POINT.getButton().fire();
        }
    },
    BACKSPACE_BUTTON(BACK_SPACE) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.BACKSPACE.getButton().fire();
        }
    },
    ESC_BUTTON(ESCAPE) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.CLEAR_ALL.getButton().fire();
        }
    },
    DEL_BUTTON(DELETE) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.CLEAR_ENTRY.getButton().fire();
        }
    },
    ENTER_BUTTON(ENTER) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.EQUAL.getButton().fire();
        }
    },

    NUMPAD_ONE_BUTTON(NUMPAD1) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NUMBER_1.getButton().fire();
        }
    },
    NUMPAD_TWO_BUTTON(NUMPAD2) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NUMBER_2.getButton().fire();
        }
    },
    NUMPAD_THREE_BUTTON(NUMPAD3) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NUMBER_3.getButton().fire();
        }
    },
    NUMPAD_FOUR_BUTTON(NUMPAD4) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NUMBER_4.getButton().fire();
        }
    },
    NUMPAD_FIVE_BUTTON(NUMPAD5) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NUMBER_5.getButton().fire();
        }
    },
    NUMPAD_SIX_BUTTON(NUMPAD6) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NUMBER_6.getButton().fire();
        }
    },
    NUMPAD_SEVEN_BUTTON(NUMPAD7) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NUMBER_7.getButton().fire();
        }
    },
    NUMPAD_EIGHT_BUTT(NUMPAD8) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NUMBER_8.getButton().fire();
        }
    },
    NUMPAD_NINE_BUTTON(NUMPAD9) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NUMBER_9.getButton().fire();
        }
    },
    NUMPAD_ZERO_BUTTON(NUMPAD0) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.NUMBER_0.getButton().fire();
        }
    },
    NUMPAD_ADD_BUTTON(ADD) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.ADD.getButton().fire();
        }
    },
    NUMPAD_SUBTRACT_BUTTON(SUBTRACT) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.SUBTRACT.getButton().fire();
        }
    },
    NUMPAD_DIVIDE_BUTTON(DIVIDE) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.DIVIDE.getButton().fire();
        }
    },
    NUMPAD_DECIMAL_BUTTON(DECIMAL) {
        @Override
        protected void execute(KeyEvent event) {
            CalculatorButton.POINT.getButton().fire();
        }
    };

    /**
     * Keyboard button code
     */
    protected KeyCode code;

    KeyboardShortcut(KeyCode code) {
        this.code = code;
    }

    /**
     * Finds keyboard key and pushes corresponding calculator button
     *
     * @param event keyboard key event
     */
    public static void findAncExecuteKey(KeyEvent event) {
        for (KeyboardShortcut ks : values()) {
            if (ks.code == event.getCode()) {
                ks.execute(event);
            }
        }
    }

    /**
     * Push correspond JavaFX node button
     *
     * @param event keyboard event
     */
    protected abstract void execute(KeyEvent event);
}
