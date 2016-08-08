package org.pepsik.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.pepsik.controller.button.CalculatorButton;
import org.pepsik.controller.button.InputNumber;
import org.pepsik.model.Model;
import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;
import org.pepsik.util.TextFormatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for handle calculator events
 */
public class CalculatorController implements Initializable {

    private static final String ZERO = "0";
    private static final String EMPTY = "";

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
     * Block calculation buttons if error
     * Can be removed by Clear, ClearALL buttons
     */
    private boolean noError = true;
    /**
     * Calculator Model witch calculate expression
     */
    private Model model = new Model();

    /**
     * Handles digit event
     *
     * @param event digit event
     */
    @FXML
    private void handleDigitAction(ActionEvent event) {
        if (noError) {
            InputNumber.addDigit(event);
            model.addNumber(InputNumber.getInput());

            displayField.setText(InputNumber.getInput().toPlainString());
        }
    }

    /**
     * Handles point event
     *
     * @param event point event
     */
    @FXML
    private void handlePointAction(ActionEvent event) {
        CalculatorButton.valueOf(((Button) event.getSource()));

        if (noError) {
            InputNumber.addPoint();

            displayField.setText(InputNumber.getInput() + ".");
        }
    }

    /**
     * Handles binary operation event
     *
     * @param event binary event
     */
    @FXML
    private void handleBinaryOperationAction(ActionEvent event) {
        String operator = CalculatorButton.valueOf(((Button) event.getSource()));

        if (noError) {
            try {
                InputNumber.clearInput();
                model.addBinaryOperator(BinaryOperation.find(operator.charAt(0)));

                displayField.setText(model.getResult().setScale(16, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
                displayHistory.setText(TextFormatter.history(model.getCurrentExpression()));
            } catch (ArithmeticException e) {
                noError = false;

                displayField.setText("Cannot divide by zero");
            }
        }
    }

    /**
     * Handles unary operation event
     *
     * @param event unary event
     */
    @FXML
    private void handleUnaryOperationAction(ActionEvent event) {
        String operator = CalculatorButton.valueOf(((Button) event.getSource()));

        if (noError) {
            try {
                InputNumber.clearInput();
                model.addUnaryOperator(UnaryOperation.find(operator));

                displayField.setText(model.getOperand().setScale(16, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
                displayHistory.setText(TextFormatter.history(model.getCurrentExpression()));
            } catch (ArithmeticException e) {
                noError = false;

                displayField.setText("Cannot divide by zero");
            }
        }
    }

    /**
     * Handles Clear entry event
     *
     * @param event clear event
     */
    @FXML
    private void handleClearAction(ActionEvent event) {
        CalculatorButton.valueOf(((Button) event.getSource()));

        if (!noError) {
            displayHistory.setText("");
        }

        InputNumber.clearInput();
        noError = true;
        model.clearEntry();

        displayField.setText(ZERO);
    }

    /**
     * Handles clear all event
     *
     * @param event clear all event
     */
    @FXML
    private void handleClearAllAction(ActionEvent event) {
        CalculatorButton.valueOf(((Button) event.getSource()));

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
        CalculatorButton.valueOf(((Button) event.getSource()));

        if (noError) {
            InputNumber.backspace();
            if (InputNumber.getInput() != null) {
                model.addNumber(InputNumber.getInput());

                displayField.setText(InputNumber.getInput().toPlainString());
            } else {
                displayField.setText(ZERO);
            }
        }
    }

    /**
     * Handles memory add event
     *
     * @param event memory add event
     */
    @FXML
    private void handleMemoryAddAction(ActionEvent event) {
        CalculatorButton.valueOf(((Button) event.getSource()));

        if (noError) {
            model.addToMemory();
        }
    }

    /**
     * Handles memory subtract event
     *
     * @param event memory subtract event
     */
    @FXML
    private void handleMemorySubtractAction(ActionEvent event) {
        CalculatorButton.valueOf(((Button) event.getSource()));

        if (noError) {
            model.subtractFromMemory();
        }
    }

    /**
     * Handles memory save event
     *
     * @param event memory save event
     */
    @FXML
    private void handleMemorySaveAction(ActionEvent event) {
        CalculatorButton.valueOf(((Button) event.getSource()));

        if (noError) {
            model.saveMemory();
        }
    }

    /**
     * Handles memory clear event
     *
     * @param event memory clear event
     */
    @FXML
    private void handleMemoryClearAction(ActionEvent event) {
        CalculatorButton.valueOf(((Button) event.getSource()));

        if (noError) {
            model.clearMemory();
        }
    }

    /**
     * Handles memory recall event
     *
     * @param event memory recall event
     */
    @FXML
    private void handleMemoryRecallAction(ActionEvent event) {
        CalculatorButton.valueOf(((Button) event.getSource()));

        if (noError) {
            BigDecimal memory = model.getMemory();

            if (memory != null) {
                displayField.setText(memory.toString());
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayField.setText(ZERO);
    }
}
