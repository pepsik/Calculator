package org.pepsik.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.pepsik.controller.button.CalculatorButton;
import org.pepsik.controller.button.KeyboardShortcut;
import org.pepsik.controller.exception.*;
import org.pepsik.model.Model;
import org.pepsik.model.exception.DivideByZeroException;
import org.pepsik.model.exception.IllegalOperandException;
import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.math.BigDecimal.ONE;
import static org.pepsik.controller.button.CalculatorButton.*;
import static org.pepsik.controller.util.InputNumber.*;
import static org.pepsik.controller.util.TextFormatter.display;
import static org.pepsik.controller.util.TextFormatter.formatInput;
import static org.pepsik.controller.util.TextFormatter.history;
import static org.pepsik.controller.UIChanger.disableMemoryClearAndRecallButton;

/**
 * Controller for handle calculator events and display calculation results and history
 */
public class CalculatorController {

    /**
     * Calculator min height
     */
    private static final int MIN_HEIGHT = 365;

    /**
     * Display min width
     */
    private static final int MIN_WIDTH = 215;

    /**
     * Calculator pref height
     */
    private static final int PREF_HEIGHT = 450;

    /**
     * Calculator pref width
     */
    private static final int PREF_WIDTH = 260;

    /**
     * Calculator max width
     */
    private static final int MAX_WIDTH = 550;

    /**
     * Default scale for binary operations
     */
    private static final int SCALE = 16;

    /**
     * Empty string
     */
    private static final String EMPTY = "";

    /**
     * String contains zero number
     */
    private static final String ZERO = "0";

    /**
     * String contains point
     */
    private static final String POINT = ".";

    /**
     * Error message occurs when divide by zero
     */
    private static final String DIVIDE_ZERO_MSG = "Cannot divide by zero";

    /**
     * Error message occurs when limit is reached
     */
    private static final String LIMIT_MSG = "Limit reached!";

    /**
     * Error message occurs when calculate sqrt with negate operand
     */
    private static final String ILLEGAL_OPERAND_MSG = "Illegal operand";

    /**
     * Maps CalculatorButtons to Binary operation
     */
    private static Map<CalculatorButton, BinaryOperation> binaryMapping = new HashMap<>();

    /**
     * Maps CalculatorButtons to Unary operation
     */
    private static Map<CalculatorButton, UnaryOperation> unaryMapping = new HashMap<>();


    //Mapping Calculator buttons to Binary and Unary operations
    static {
        binaryMapping.put(ADD, BinaryOperation.ADD);
        binaryMapping.put(SUBTRACT, BinaryOperation.SUBTRACT);
        binaryMapping.put(MULTIPLY, BinaryOperation.MULTIPLY);
        binaryMapping.put(DIVIDE, BinaryOperation.DIVIDE);
        binaryMapping.put(EQUAL, BinaryOperation.EQUAL);
        binaryMapping = Collections.unmodifiableMap(binaryMapping);

        unaryMapping.put(SQUARE, UnaryOperation.SQUARE);
        unaryMapping.put(SQUARE_ROOT, UnaryOperation.SQUARE_ROOT);
        unaryMapping.put(NEGATE, UnaryOperation.NEGATE);
        unaryMapping.put(PERCENT, UnaryOperation.PERCENT);
        unaryMapping.put(FRACTION, UnaryOperation.FRACTION);
        unaryMapping = Collections.unmodifiableMap(unaryMapping);
    }

    /**
     * Calculator display field
     */
    @FXML
    private Label displayField;

    /**
     * Calculator history field
     */
    @FXML
    private Label displayHistory;

    /**
     * Block input buttons if error occurs
     * Can be removed by Clear, ClearALL button
     */
    private boolean noError = true;

    /**
     * Calculator Model witch calculate expression
     */
    private Model model = new Model();

    public void setStageAndSetupListeners(Stage stage) {
        stage.setTitle("Calculator");
        stage.initStyle(StageStyle.UNDECORATED);

        Scene scene = displayField.getScene();

        //Init max, min, pref sizes
        setUpApplicationSizes(stage);

        //Setup all node button to enums
        setUpCalculatorButtons(scene);

        //Init listeners for shortcut keys
        initKeyboardShortcutListeners(scene);

        //Init listeners for resizing button
        initResizeListeners(scene);

        displayField.setText(formatInput());
    }

    public static Map<CalculatorButton, BinaryOperation> getBinaryMapping() {
        return binaryMapping;
    }

    public static Map<CalculatorButton, UnaryOperation> getUnaryMapping() {
        return unaryMapping;
    }

    /**
     * Handles digit event
     *
     * @param event digit event
     */
    @FXML
    private void handleDigitAction(ActionEvent event) {
        CalculatorButton cb = valueOf((Button) event.getSource());

        if (noError) {
            addToInput(cb.getValue());
            model.addNumber(getInput());

            displayField.setText(formatInput());
        }
    }

    /**
     * Handles point event
     *
     * @param event point event
     */
    @FXML
    private void handlePointAction(ActionEvent event) {
        valueOf((Button) event.getSource());

        if (noError) {
            String toDisplay = formatInput();

            if (!isInputPointSet()) {
                addPointToInput();
                toDisplay += POINT;
            }

            displayField.setText(toDisplay);
        }
    }

    /**
     * Handles binary operation event
     *
     * @param event binary event
     * @throws IllegalStateException in cases if operation not found
     */
    @FXML
    private void handleOperationAction(ActionEvent event) {
        CalculatorButton cb = valueOf((Button) event.getSource());

        String toDisplay;
        if (noError) {
            try {
                clearInput();
                BigDecimal modelValue;

                //if binary operator - calculate binary operation
                BinaryOperation binaryOperation = binaryMapping.get(cb);
                if (binaryOperation != null) {
                    model.addBinaryOperator(binaryOperation);
                    modelValue = model.getResult();

                    checksLimit(modelValue);
                    toDisplay = display(modelValue, SCALE);
                } else {
                    //if unary operation - calculate unary operation
                    UnaryOperation unaryOperation = unaryMapping.get(cb);
                    if (unaryOperation != null) {
                        model.addUnaryOperator(unaryOperation);
                        modelValue = model.getOperand();

                        checksLimit(modelValue);
                        toDisplay = display(modelValue, SCALE - 1); //unary scale less then binary by 1
                    } else {
                        throw new IllegalStateException("No such operation found - " + cb.name());
                    }
                }

                displayHistory.setText(history(model.getCurrentExpression(), model.getOperand(), SCALE));
            } catch (LimitException ex) {
                noError = false;
                toDisplay = LIMIT_MSG;
            } catch (DivideByZeroException ex) {
                noError = false;
                toDisplay = DIVIDE_ZERO_MSG;
            } catch (IllegalOperandException ex) {
                noError = false;
                toDisplay = ILLEGAL_OPERAND_MSG;
            }

            displayField.setText(toDisplay);
        }

    }

    /**
     * Handles Clear entry event
     *
     * @param event clear event
     */
    @FXML
    private void handleClearAction(ActionEvent event) {
        valueOf((Button) event.getSource());

        model.clearEntry();
        clearInput();

        String result = EMPTY;
        if (noError) {
            result = history(model.getCurrentExpression(), model.getOperand(), SCALE);
        }
        noError = true;


        displayField.setText(ZERO);
        displayHistory.setText(result);
    }

    /**
     * Handles clear all event
     *
     * @param event clear all event
     */
    @FXML
    private void handleClearAllAction(ActionEvent event) {
        valueOf((Button) event.getSource());

        model = new Model();
        clearInput();
        noError = true;

        displayField.setText(ZERO);
        displayHistory.setText(EMPTY);
    }

    /**
     * Handles backspaceInput event
     *
     * @param event backspaceInput event
     */
    @FXML
    private void handleBackspaceAction(ActionEvent event) {
        valueOf((Button) event.getSource());

        if (noError) {
            backspaceInput();
            model.addNumber(getInput());

            String toDisplay = formatInput();
            if (getInputScale() == 0 && isInputPointSet()) {
                toDisplay += POINT;
            }

            displayField.setText(toDisplay);
        }
    }

    /**
     * Handles memory add event
     *
     * @param event memory add event
     */
    @FXML
    private void handleMemoryAction(ActionEvent event) {
        CalculatorButton cb = valueOf((Button) event.getSource());

        if (noError) {
            if (cb.equals(MEMORY_RECALL)) {
                BigDecimal memory = model.getMemory();

                if (memory != null) {
                    displayField.setText(display(memory, SCALE));
                }
            } else if (cb.equals(MEMORY_CLEAR)) {
                model.clearMemory();
                disableMemoryClearAndRecallButton(true);
            } else if (cb.equals(MEMORY_ADD)) {
                model.addToMemory();
                disableMemoryClearAndRecallButton(false);
            } else if (cb.equals(MEMORY_SUBTRACT)) {
                model.subtractFromMemory();
                disableMemoryClearAndRecallButton(false);
            } else if (cb.equals(MEMORY_SAVE)) {
                model.saveMemory();
                disableMemoryClearAndRecallButton(false);
            } else {
                throw new IllegalStateException("No such operation found - " + cb.name());
            }
        }

        clearInput();
    }

    /**
     * Checks if value between upper and lower boundary limit values
     *
     * @param bg calculation result
     * @throws LimitException in case if it reach boundary value
     */
    private void checksLimit(BigDecimal bg) throws LimitException {
        if (bg.compareTo(BigDecimal.ZERO) != 0) {
            //lower limit by checking digits count to the right of decimal point
            if (bg.movePointRight(Model.SCALE).abs().compareTo(ONE) < 0) {
                throw new LimitException("Lower limit reached - " + display(bg, SCALE) + " but scale is " + Model.SCALE);
            }
            //upper limit by checking digits count to the left of decimal point
            if (bg.precision() - bg.scale() > Model.SCALE) {
                throw new LimitException("Upper limit reached - " + display(bg, SCALE) + " but scale is " + Model.SCALE);
            }
        }
    }


    /**
     * Setup min max pref application sizes
     *
     * @param primaryStage primary stage
     */
    private void setUpApplicationSizes(Stage primaryStage) {
        //Min size
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);

        //Preference size
        primaryStage.setHeight(PREF_HEIGHT);
        primaryStage.setWidth(PREF_WIDTH);

        //Max size
        primaryStage.setMaxWidth(MAX_WIDTH);
    }

    /**
     * Set Javafx node button to enum
     *
     * @param scene current scene
     */
    private void setUpCalculatorButtons(Scene scene) {
        for (CalculatorButton cb : CalculatorButton.values()) {
            cb.setButton((Button) scene.lookup("#" + cb.name().toLowerCase()));
        }
    }

    /**
     * Initializes Keyboard shortcuts
     *
     * @param scene current scene
     */
    private void initKeyboardShortcutListeners(Scene scene) {
        scene.setOnKeyPressed(KeyboardShortcut::findAncExecuteKey);
    }

    /**
     * Resize button font
     *
     * @param scene current scene
     */
    private void initResizeListeners(Scene scene) {
        UIChanger.setDisplay(displayField);

        //displayField font resize listener
        displayField.textProperty().addListener((observable, oldValue, newValue) -> UIChanger.resizeDisplay());

        //add listener to width property
        scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            UIChanger.resizeButtons();
            UIChanger.resizeDisplay();
        });

        //add listener to height property
        scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
            UIChanger.resizeButtons();
            UIChanger.resizeDisplay();
        });
    }
}
