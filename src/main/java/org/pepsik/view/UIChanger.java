package org.pepsik.view;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.pepsik.controller.button.CalculatorButton;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.pepsik.controller.button.CalculatorButton.*;

/**
 * Class responsible for change buttons, display font size
 */
public class UIChanger {

    /**
     * Map contains button css prefix
     */
    private static Map<CalculatorButton, String> cssMap = new HashMap<>();

    /**
     * Boundary calculator width for change font size
     */
    private static final int BOUNDARY_WIDTH = 270;//todo see Dimension

    /**
     * Boundary calculator height for change font size
     */
    private static final int BOUNDARY_HEIGHT = 450;

    /**
     * Display for resize
     */
    private static Label display;

    /**
     * Max display font taken from css file
     */
    private static int maxDisplayFont;

    /**
     * Min display font taken from css file
     */
    private static int minDisplayFont;

    /**
     * Current button to resize
     */
    private static CalculatorButton button;

    static {
        cssMap.put(NUMBER_0, "number");
        cssMap.put(NUMBER_1, "number");
        cssMap.put(NUMBER_2, "number");
        cssMap.put(NUMBER_3, "number");
        cssMap.put(NUMBER_4, "number");
        cssMap.put(NUMBER_5, "number");
        cssMap.put(NUMBER_6, "number");
        cssMap.put(NUMBER_8, "number");
        cssMap.put(NUMBER_7, "number");
        cssMap.put(NUMBER_9, "number");
        cssMap.put(NUMBER_0, "number");

        cssMap.put(ADD, "binary");
        cssMap.put(SUBTRACT, "binary");
        cssMap.put(MULTIPLY, "binary");
        cssMap.put(DIVIDE, "binary");
        cssMap.put(EQUAL, "binary");

        cssMap.put(SQUARE, "unary");
        cssMap.put(SQUARE_ROOT, "unary");
        cssMap.put(FRACTION, "unary");
        cssMap.put(NEGATE, "unary");
        cssMap.put(PERCENT, "unary");

        cssMap.put(POINT, "point");

        cssMap.put(CLEAR_ALL, "clear");
        cssMap.put(CLEAR_ENTRY, "clear");
        cssMap.put(BACKSPACE, "clear");

        cssMap.put(MEMORY_ADD, "");
        cssMap.put(MEMORY_SUBTRACT, "");
        cssMap.put(MEMORY_SAVE, "");
        cssMap.put(MEMORY_RECALL, "");
        cssMap.put(MEMORY_CLEAR, "");

        //disable memory recall and clear button
        CalculatorButton.MEMORY_CLEAR.getButton().setDisable(true);
        CalculatorButton.MEMORY_RECALL.getButton().setDisable(true);
    }

    /**
     * Initial resize logic for all calculator buttons
     *
     * @param dimension dimension represent calculator window sizes
     */
    public static void resizeButtons(Dimension dimension) {
        for (CalculatorButton value : CalculatorButton.values()) {
            button = value;
            resize(dimension);
        }
    }

    /**
     * Set display for resizing and init max, min font values from css file
     *
     * @param display display label
     */
    public static void setDisplay(Label display) {
        UIChanger.display = display;
        //get css max display font
        display.getStyleClass().add("display_max_font");
        maxDisplayFont = (int) display.getFont().getSize();
        display.getStyleClass().remove("display_max_font");

        //get css min display font
        display.getStyleClass().add("display_min_font");
        minDisplayFont = (int) display.getFont().getSize();
        display.getStyleClass().remove("display_min_font");
    }

    /**
     * Resize display font size
     */
    public static void resizeDisplay() {
        if (display == null) {
            throw new RuntimeException("Display not set for resize!");
        }

        Scene scene = display.getScene();

        //define text font size to fill display when big number is displayed
        double d = display.getScene().getWidth() / display.getText().length() * 1.6; // multiplier to fill display
        if (Double.compare(scene.getWidth(), BOUNDARY_WIDTH) > 0 && Double.compare(scene.getHeight(), BOUNDARY_HEIGHT) > 0) {

            if (d > maxDisplayFont) {
                display.setFont(Font.font(maxDisplayFont));
            } else {
                display.setFont(Font.font(d));
            }
        } else {
            //set min display font size
            if (d > minDisplayFont) {
                display.setFont(Font.font(minDisplayFont));
            } else {
                display.setFont(Font.font(d));
            }
        }
    }

    /**
     * Disable clear and recall buttons
     *
     * @param b true when disable
     */
    public static void disableMemoryClearAndRecallButton(boolean b) {
        CalculatorButton.MEMORY_CLEAR.getButton().setDisable(b);
        CalculatorButton.MEMORY_RECALL.getButton().setDisable(b);
    }

    /**
     * Resizes button font text size
     *
     * @param dimension dimension represent calculator window sizes
     */
    private static void resize(Dimension dimension) {
        String prefix = cssMap.get(button);

        if (prefix == null) {
            return;
        }

        if (Double.compare(dimension.getWidth(), BOUNDARY_WIDTH) > 0
                && Double.compare(dimension.getHeight(), BOUNDARY_HEIGHT) > 0) {
            changeCssClass(prefix + "_small_font", prefix + "_big_font");
        } else {
            changeCssClass(prefix + "_big_font", prefix + "_small_font");
        }
    }

    /**
     * Switches css classes on button
     *
     * @param oldClass old css class
     * @param newCss   new css class
     */
    private static void changeCssClass(String oldClass, String newCss) {
        ObservableList<String> cssList = button.getButton().getStyleClass();
        cssList.remove(oldClass);

        if (!cssList.contains(newCss)) {
            cssList.add(newCss);
        }
    }
}
