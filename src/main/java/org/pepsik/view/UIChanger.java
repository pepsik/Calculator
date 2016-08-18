package org.pepsik.view;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.pepsik.controller.button.CalculatorButton;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.compare;
import static javafx.scene.text.Font.font;
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
    public static final int BOUNDARY_WIDTH = 270;

    /**
     * Boundary calculator height for change font size
     */
    public static final int BOUNDARY_HEIGHT = 450;

    /**
     * Multiplier to fill display
     */
    public static final double MULTIPLIER = 1.6;

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

        //disable memory recall and clear button at startup app
        UIChanger.disableMemoryClearAndRecallButton(true);
    }

    /**
     * Set display for resizing and init max, min font values from css file
     *
     * @param display display label
     */
    public static void setDisplay(Label display) {
        UIChanger.display = display;
        getMinMaxDisplaySizes();
    }

    /**
     * Initial resize logic for all calculator buttons
     */
    public static void resizeButtons() {
        for (CalculatorButton value : values()) {
            button = value;
            resize();
        }
    }

    /**
     * Resize display font size
     */
    public static void resizeDisplay() {
        Scene scene = display.getScene();
        double width = scene.getWidth();
        double height = scene.getHeight();

        //define text font size to fill display when big number is displayed
        double d = width / display.getText().length() * MULTIPLIER;
        if (compare(width, BOUNDARY_WIDTH) > 0 && compare(height, BOUNDARY_HEIGHT) > 0) {

            //set max display font size if width > 270 and height > 450
            if (d > maxDisplayFont) {
                d = maxDisplayFont;
            }
        } else {
            //set min display font size
            if (d > minDisplayFont) {
                d = minDisplayFont;
            }
        }

        display.setFont(font(d));
    }

    /**
     * Disable clear and recall buttons
     *
     * @param b true when disable
     */
    public static void disableMemoryClearAndRecallButton(boolean b) {
        MEMORY_CLEAR.getButton().setDisable(b);
        MEMORY_RECALL.getButton().setDisable(b);
    }

    /**
     * Gets display min max sizes from css file
     */
    private static void getMinMaxDisplaySizes() {
        //get css max display font
        ObservableList<String> styleClass = display.getStyleClass();
        Font font = display.getFont();

        styleClass.add("display_max_font");
        maxDisplayFont = (int) font.getSize();
        styleClass.remove("display_max_font");

        //get css min display font
        styleClass.add("display_min_font");
        minDisplayFont = (int) font.getSize();
        styleClass.remove("display_min_font");
    }

    public static int getMaxDisplayFont() {
        return maxDisplayFont;
    }

    public static int getMinDisplayFont() {
        return minDisplayFont;
    }

    /**
     * Resizes button font text size
     */
    private static void resize() {
        String prefix = cssMap.get(button);

        if (prefix == null) {
            return;
        }

        Scene scene = display.getScene();
        double width = scene.getWidth();
        double height = scene.getHeight();

        if (compare(width, BOUNDARY_WIDTH) > 0 && compare(height, BOUNDARY_HEIGHT) > 0) {
            changeCssClass(prefix + "_small_font", prefix + "_big_font"); //todo
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
