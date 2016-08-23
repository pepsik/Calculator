package org.pepsik.controller;

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
 * Class responsible for change buttons and display font size
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


    // Init mapping CalculatorButton to css class prefix
    static {
        //number buttons
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

        //binary operation
        cssMap.put(ADD, "binary");
        cssMap.put(SUBTRACT, "binary");
        cssMap.put(MULTIPLY, "binary");
        cssMap.put(DIVIDE, "binary");
        cssMap.put(EQUAL, "binary");

        //unary operation
        cssMap.put(SQUARE, "unary");
        cssMap.put(SQUARE_ROOT, "unary");
        cssMap.put(FRACTION, "unary");
        cssMap.put(NEGATE, "unary");
        cssMap.put(PERCENT, "unary");

        //decimal point
        cssMap.put(POINT, "point");

        //clear operations
        cssMap.put(CLEAR_ALL, "clear");
        cssMap.put(CLEAR_ENTRY, "clear");
        cssMap.put(BACKSPACE, "clear");

        //disable memory recall and clear button at startup app
        disableMemoryClearAndRecallButton(true);
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
        int textLength = display.getText().length();

        //define text font size to fill display when big number is displayed
        double d = width / textLength * MULTIPLIER;

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
        display.setStyle("-fx-font-size:" + (int) d);
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

        String maxFont = "display_max_font";
        String minFont = "display_min_font";

        if (!styleClass.contains(maxFont)) {
            styleClass.add(maxFont);
        }
        if (styleClass.contains(minFont)) {
            styleClass.remove(minFont);
        }
        display.applyCss();
        maxDisplayFont = (int) display.getFont().getSize();

        //get css min display font
        if (!styleClass.contains(minFont)) {
            styleClass.add(minFont);
        }
        if (styleClass.contains(maxFont)) {
            styleClass.remove(maxFont);
        }
        display.applyCss();
        minDisplayFont = (int) display.getFont().getSize();

        if (styleClass.contains(minFont)) {
            styleClass.remove(minFont);
        }
    }

    /**
     * Resizes button font text size
     */
    private static void resize() {
        //get button css prefix
        String prefix = cssMap.get(button);

        //nothing for resize
        if (prefix == null) {
            return;
        }

        Scene scene = display.getScene();
        double width = scene.getWidth();
        double height = scene.getHeight();

        //Switches button css class uses button prefix
        String oldCssClass = prefix + "_small_font"; // css class consist small font
        String newCssClass = prefix + "_big_font";// css class consist big font

        if (compare(width, BOUNDARY_WIDTH) > 0 && compare(height, BOUNDARY_HEIGHT) > 0) {
            //change small font to big font
            changeCssClass(oldCssClass, newCssClass);
        } else {
            //change big font to small font
            changeCssClass(newCssClass, oldCssClass);
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
