package org.pepsik.controller.button;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Map;

import static org.pepsik.controller.button.CalculatorButton.*;

/**
 * Class represents keyboard shortcuts and simply fires correspond calculator button
 */
public class KeyboardShortcut {
    /**
     * Map contains single keyboard button shortcut maps to calculator button
     */
    private static Map<KeyCode, CalculatorButton> buttonMap = new HashMap<>();
    /**
     * Map contains combination keyboard buttons shortcut maps to calculator button
     */
    private static Map<KeyCode, CalculatorButton> ctrlCombinationMap = new HashMap<>();

    /**
     * Shortcut to calculator button mapping
     */
    static {
        buttonMap.put(KeyCode.DIGIT0, NUMBER_0);
        buttonMap.put(KeyCode.DIGIT1, NUMBER_1);
        buttonMap.put(KeyCode.DIGIT2, NUMBER_2);
        buttonMap.put(KeyCode.DIGIT3, NUMBER_3);
        buttonMap.put(KeyCode.DIGIT4, NUMBER_4);
        buttonMap.put(KeyCode.DIGIT5, NUMBER_5);
        buttonMap.put(KeyCode.DIGIT6, NUMBER_6);
        buttonMap.put(KeyCode.DIGIT7, NUMBER_7);
        buttonMap.put(KeyCode.DIGIT8, NUMBER_8);
        buttonMap.put(KeyCode.DIGIT9, NUMBER_9);

        buttonMap.put(KeyCode.NUMPAD0, NUMBER_0);
        buttonMap.put(KeyCode.NUMPAD1, NUMBER_1);
        buttonMap.put(KeyCode.NUMPAD2, NUMBER_2);
        buttonMap.put(KeyCode.NUMPAD3, NUMBER_3);
        buttonMap.put(KeyCode.NUMPAD4, NUMBER_4);
        buttonMap.put(KeyCode.NUMPAD5, NUMBER_5);
        buttonMap.put(KeyCode.NUMPAD6, NUMBER_6);
        buttonMap.put(KeyCode.NUMPAD7, NUMBER_7);
        buttonMap.put(KeyCode.NUMPAD8, NUMBER_8);
        buttonMap.put(KeyCode.NUMPAD9, NUMBER_9);

        buttonMap.put(KeyCode.PERIOD, POINT);
        buttonMap.put(KeyCode.DECIMAL, POINT);
        buttonMap.put(KeyCode.BACK_SPACE, BACKSPACE);
        buttonMap.put(KeyCode.ESCAPE, CLEAR_ALL);
        buttonMap.put(KeyCode.DELETE, CLEAR_ENTRY);

        buttonMap.put(KeyCode.ENTER, EQUAL);
        buttonMap.put(KeyCode.EQUALS, EQUAL);
        buttonMap.put(KeyCode.PLUS, ADD);
        buttonMap.put(KeyCode.ADD, ADD);
        buttonMap.put(KeyCode.MINUS, SUBTRACT);
        buttonMap.put(KeyCode.SUBTRACT, SUBTRACT);
        buttonMap.put(KeyCode.DIVIDE, DIVIDE);
        buttonMap.put(KeyCode.SLASH, DIVIDE);
        buttonMap.put(KeyCode.MULTIPLY, MULTIPLY);
        buttonMap.put(KeyCode.STAR, MULTIPLY);

        buttonMap.put(KeyCode.AT, SQUARE);
        buttonMap.put(KeyCode.F9, NEGATE);
        buttonMap.put(KeyCode.R, FRACTION);

        ctrlCombinationMap.put(KeyCode.DIGIT2, SQUARE_ROOT);
        ctrlCombinationMap.put(KeyCode.DIGIT5, PERCENT);
        ctrlCombinationMap.put(KeyCode.DIGIT8, MULTIPLY);
        ctrlCombinationMap.put(KeyCode.EQUALS, ADD);

        ctrlCombinationMap.put(KeyCode.P, MEMORY_ADD);
        ctrlCombinationMap.put(KeyCode.Q, MEMORY_SUBTRACT);
        ctrlCombinationMap.put(KeyCode.M, MEMORY_SAVE);
        ctrlCombinationMap.put(KeyCode.R, MEMORY_RECALL);
        ctrlCombinationMap.put(KeyCode.L, MEMORY_CLEAR);
    }

    /**
     * Finds keyboard key and pushes corresponding calculator button
     *
     * @param event keyboard key event
     */
    public static void findAncExecuteKey(KeyEvent event) {
        KeyCode keyCode = event.getCode();

        if (ctrlCombinationMap.containsKey(keyCode)) {
            KeyCodeCombination combination = new KeyCodeCombination(keyCode, KeyCombination.CONTROL_DOWN);
            if (combination.match(event)) {
                CalculatorButton cb = ctrlCombinationMap.get(keyCode);
                cb.getButton().fire();
                return;
            }
        }

        if (buttonMap.containsKey(keyCode)) { //todo map contains
            CalculatorButton cb = buttonMap.get(keyCode);
            cb.getButton().fire();
        }
    }
}
