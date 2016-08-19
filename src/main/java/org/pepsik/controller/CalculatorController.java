package org.pepsik.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.pepsik.controller.button.CalculatorButton;
import org.pepsik.controller.button.KeyboardShortcut;
import org.pepsik.controller.exception.ButtonNotExistException;
import org.pepsik.controller.exception.LimitException;
import org.pepsik.controller.exception.OperationNotExistException;
import org.pepsik.model.Model;
import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;
import org.pepsik.view.UIChanger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.math.BigDecimal.ONE;
import static org.pepsik.controller.button.CalculatorButton.*;
import static org.pepsik.controller.util.InputNumber.*;
import static org.pepsik.controller.util.TextFormatter.display;
import static org.pepsik.controller.util.TextFormatter.formatInput;
import static org.pepsik.controller.util.TextFormatter.history;
import static org.pepsik.view.UIChanger.disableMemoryClearAndRecallButton;

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
     * Maps CalculatorButtons to Binary operation
     */
    private static Map<CalculatorButton, BinaryOperation> binaryMapping = new HashMap<>();

    /**
     * Maps CalculatorButtons to Unary operation
     */
    private static Map<CalculatorButton, UnaryOperation> unaryMapping = new HashMap<>();

    /**
     * Mapping Calculator buttons to Binary and Unary operations
     */
    static {
        binaryMapping.put(ADD, BinaryOperation.ADD);
        binaryMapping.put(SUBTRACT, BinaryOperation.SUBTRACT);
        binaryMapping.put(MULTIPLY, BinaryOperation.MULTIPLY);
        binaryMapping.put(DIVIDE, BinaryOperation.DIVIDE);
        binaryMapping.put(EQUAL, BinaryOperation.EQUAL);

        unaryMapping.put(SQUARE, UnaryOperation.SQUARE);
        unaryMapping.put(SQUARE_ROOT, UnaryOperation.SQUARE_ROOT);
        unaryMapping.put(NEGATE, UnaryOperation.NEGATE);
        unaryMapping.put(PERCENT, UnaryOperation.PERCENT);
        unaryMapping.put(FRACTION, UnaryOperation.FRACTION);
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
     */
    @FXML
    private void handleOperationAction(ActionEvent event) {
        CalculatorButton cb = valueOf((Button) event.getSource()); //// TODO: 8/18/2016

        String toDisplay;
        if (noError) {
            try {
                clearInput();
                BinaryOperation binaryOperation = binaryMapping.get(cb);
                BigDecimal modelValue;

                if (binaryOperation != null) {
                    model.addBinaryOperator(binaryOperation);        //todo mapping operators
                    modelValue = model.getResult();

                    checksLimit(modelValue);
                    toDisplay = display(modelValue, SCALE);
                } else {
                    UnaryOperation unaryOperation = unaryMapping.get(cb);
                    if (unaryOperation != null) {
                        model.addUnaryOperator(unaryOperation);
                        modelValue = model.getOperand();

                        checksLimit(modelValue);
                        toDisplay = display(modelValue, SCALE - 1); //unary scale less then binary by 1
                    } else {
                        throw new OperationNotExistException("No such operation found - " + cb.name()); //todo custom except
                    }
                }

                displayHistory.setText(history(model.getCurrentExpression(), model.getOperand(), SCALE));
            } catch (LimitException ex) { //todo handle custom
                noError = false;
                toDisplay = LIMIT_MSG;
            } catch (ArithmeticException ex) {
                if (ex.getMessage().equals("BigInteger divide by zero")) {
                    noError = false;
                    toDisplay = DIVIDE_ZERO_MSG;
                } else {
                    throw ex;
                }

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
        noError = true; /// TODO: 8/18/2016


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
            }

            if (cb.equals(MEMORY_CLEAR)) {
                model.clearMemory();
                disableMemoryClearAndRecallButton(true);
            }

            if (cb.equals(MEMORY_ADD)) {
                model.addToMemory();
                disableMemoryClearAndRecallButton(false);
            }

            if (cb.equals(MEMORY_SUBTRACT)) {
                model.subtractFromMemory();
                disableMemoryClearAndRecallButton(false);
            }

            if (cb.equals(MEMORY_SAVE)) {
                model.saveMemory();
                disableMemoryClearAndRecallButton(false);
            }
        }

        clearInput();
    }

    private void checksLimit(BigDecimal bg) {
        if (bg.compareTo(BigDecimal.ZERO) != 0) {
            //lower limit by checking digits count to the right of decimal point
            if (bg.movePointRight(Model.SCALE).abs().compareTo(ONE) < 0) {
                throw new LimitException("Lower limit reached - " + display(bg, SCALE) + " but scale is " + Model.SCALE); //// TODO: 8/18/2016  more info
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
            if (cb.getButton() == null) {
                throw new ButtonNotExistException("Button NOT FOUND! - " + cb.name());
            }
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
