package org.pepsik.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.pepsik.controller.button.CalculatorButton;
import org.pepsik.controller.button.KeyboardShortcut;
import org.pepsik.controller.util.InputNumber;
import org.pepsik.model.Model;
import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;
import org.pepsik.view.UIChanger;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static org.pepsik.controller.button.CalculatorButton.valueOf;
import static org.pepsik.controller.util.TextFormatter.display;
import static org.pepsik.controller.util.TextFormatter.formatInputNumber;
import static org.pepsik.controller.util.TextFormatter.history;

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
     * Block calculation button if error occurs
     * Can be removed by Clear, ClearALL button
     */
    private boolean noError = true;

    /**
     * Calculator Model witch calculate expression
     */
    private Model model = new Model();

    public void setStageAndSetupListeners(Stage stage) { //// TODO: refact
        stage.setTitle("Calculator");
        Scene scene = displayField.getScene();

        //Init max, min, pref sizes
        setUpApplicationSizes(stage);

        //Setup all node button to enums
        setUpButtons(scene.getRoot());

        //Init listeners for shortcut keys
        initKeyboardShortcutListeners(scene);

        //Init listeners for resizing button
        initResizeListeners(scene);

        displayField.setText(formatInputNumber());
    }

    /**
     * Handles digit event
     *
     * @param event digit event
     */
    @FXML
    private void handleDigitAction(ActionEvent event) {
        CalculatorButton cb = valueOf(event);

        if (noError) {
            InputNumber.addToInput(new BigDecimal(cb.getValue()));
            model.addNumber(InputNumber.getInput());

            displayField.setText(formatInputNumber());
        }
    }

    /**
     * Handles point event
     *
     * @param event point event
     */
    @FXML
    private void handlePointAction(ActionEvent event) {
        valueOf(event);

        if (noError) {
            String toDisplay = formatInputNumber();

            if (!InputNumber.isPointSet()) {
                InputNumber.addPoint();
                toDisplay += ".";
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
    private void handleBinaryOperationAction(ActionEvent event) {
        CalculatorButton cb = valueOf(event);

        String toDisplay;
        if (noError) {
            try {
                InputNumber.clearInput();
                model.addBinaryOperator(BinaryOperation.valueOf(cb.name()));

                BigDecimal modelResult = model.getResult();
                checksLimit(modelResult);

                toDisplay = display(modelResult, SCALE);
                displayHistory.setText(history(model.getCurrentExpression(), model.getOperand(), SCALE));
            } catch (ArithmeticException e) {
                noError = false;
                toDisplay = "Cannot divide by zero";
            } catch (RuntimeException ex) {
                noError = false;
                toDisplay = "Limit reached!";
            }

            displayField.setText(toDisplay);
        }
    }

    /**
     * Handles unary operation event
     *
     * @param event unary event
     */
    @FXML
    private void handleUnaryOperationAction(ActionEvent event) {
        CalculatorButton cb = valueOf(event);

        String toDisplay;
        if (noError) {
            try {
                InputNumber.clearInput();
                model.addUnaryOperator(UnaryOperation.valueOf(cb.name()));

                BigDecimal operand = model.getOperand();
                checksLimit(operand);

                toDisplay = display(operand, SCALE - 1); //unary scale less then binary by 1
                displayHistory.setText(history(model.getCurrentExpression(), operand, SCALE));
            } catch (ArithmeticException e) {
                noError = false;
                toDisplay = "Cannot divide by zero";
            } catch (RuntimeException ex) {
                noError = false;
                toDisplay = "Limit reached!";
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
        valueOf(event);

        model.clearEntry();
        InputNumber.clearInput();

        String result = EMPTY;
        if (noError) {
            result = history(model.getCurrentExpression(), model.getOperand(), SCALE);
        } else {
            noError = true;
        }

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
        valueOf(event);

        model = new Model();
        InputNumber.clearInput();
        noError = true;

        displayField.setText(ZERO);
        displayHistory.setText(EMPTY);
    }

    /**
     * Handles backspace event
     *
     * @param event backspace event
     */
    @FXML
    private void handleBackspaceAction(ActionEvent event) {
        valueOf(event);

        if (noError) {
            InputNumber.backspace();
            model.addNumber(InputNumber.getInput());

            String toDisplay = formatInputNumber();
            if (InputNumber.getScale() == 0 && InputNumber.isPointSet()) {
                toDisplay += ".";
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
    private void handleMemoryAddAction(ActionEvent event) {
        valueOf(event);

        if (noError) {
            model.addToMemory();
        }

        InputNumber.clearInput();
        UIChanger.disableMemoryClearAndRecallButton(false);
    }

    /**
     * Handles memory subtract event
     *
     * @param event memory subtract event
     */
    @FXML
    private void handleMemorySubtractAction(ActionEvent event) {
        valueOf(event);

        if (noError) {
            model.subtractFromMemory();
        }

        InputNumber.clearInput();
        UIChanger.disableMemoryClearAndRecallButton(false);
    }

    /**
     * Handles memory save event
     *
     * @param event memory save event
     */
    @FXML
    private void handleMemorySaveAction(ActionEvent event) {
        valueOf(event);

        if (noError) {
            model.saveMemory();
        }

        InputNumber.clearInput();
        UIChanger.disableMemoryClearAndRecallButton(false);
    }

    /**
     * Handles memory clear event
     *
     * @param event memory clear event
     */
    @FXML
    private void handleMemoryClearAction(ActionEvent event) {
        valueOf(event);

        if (noError) {
            model.clearMemory();
        }

        InputNumber.clearInput();
        UIChanger.disableMemoryClearAndRecallButton(true);
    }

    /**
     * Handles memory recall event
     *
     * @param event memory recall event
     */
    @FXML
    private void handleMemoryRecallAction(ActionEvent event) {
        valueOf(event);

        if (noError) {
            BigDecimal memory = model.getMemory();

            if (memory != null) {
                displayField.setText(display(memory, SCALE));
            }
        }

        InputNumber.clearInput();
    }

    private void checksLimit(BigDecimal bg) {
        if (bg.compareTo(BigDecimal.ZERO) != 0) {
            //lower limit
            if (bg.movePointRight(Model.SCALE).abs().compareTo(ONE) < 0) {
                throw new RuntimeException("Limit is reached!");
            }
            //upper limit
            if (bg.precision() - bg.scale() > Model.SCALE) {
                throw new RuntimeException("Limit is reached!");
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
     * @param root current root
     */
    private void setUpButtons(Parent root) {
        for (CalculatorButton cb : CalculatorButton.values()) {
            cb.setButton((Button) root.lookup("#" + cb.name().toLowerCase()));
            if (cb.getButton() == null) {
                throw new RuntimeException("Button NOT FOUND! - " + cb.name());
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
