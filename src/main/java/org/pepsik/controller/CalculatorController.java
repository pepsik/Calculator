package org.pepsik.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.pepsik.controller.button.CalculatorButton;
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
     * User input value
     */
    private String inputNumber = EMPTY;
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
        String digit = CalculatorButton.valueOf((Button) event.getSource());

        if (inputNumber.isEmpty()) {
            inputNumber = digit;
        } else if (inputNumber.length() < 16) {
            inputNumber += digit;
            inputNumber = new BigDecimal(inputNumber).toString();
        }

        model.addNumber(new BigDecimal(inputNumber));
        displayField.setText(inputNumber);
    }

    /**
     * Handles point event
     *
     * @param event point event
     */
    @FXML
    private void handlePointAction(ActionEvent event) {
        String point = CalculatorButton.valueOf(((Button) event.getSource()));

        if (inputNumber.isEmpty()) {
            inputNumber = "0.";
        } else if (!inputNumber.contains(point)) {
            inputNumber += point;
        }

        displayField.setText(inputNumber);
    }

    /**
     * Handles binary operation event
     * @param event binary event
     */
    @FXML
    private void handleBinaryOperationAction(ActionEvent event) {
        String operator = CalculatorButton.valueOf(((Button) event.getSource()));
        model.addBinaryOperator(BinaryOperation.find(operator.charAt(0)));
        inputNumber = EMPTY;

        displayField.setText(model.getResult().setScale(16, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
        displayHistory.setText(TextFormatter.history(model.getCurrentExpression()));
    }

    /**
     * Handles unary operation event
     * @param event unary event
     */
    @FXML
    private void handleUnaryOperationAction(ActionEvent event) {
        String operator = CalculatorButton.valueOf(((Button) event.getSource()));
        model.addUnaryOperator(UnaryOperation.find(operator));
        inputNumber = ZERO;

        displayField.setText(model.getOperand().setScale(16, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
        displayHistory.setText(TextFormatter.history(model.getCurrentExpression()));
    }

    /**
     * Handles Clear entry event
     * @param event clear event
     */
    @FXML
    private void handleClearAction(ActionEvent event) {
        CalculatorButton.valueOf(((Button) event.getSource()));
        model.clearEntry();
        inputNumber = ZERO;

        displayField.setText(ZERO);
    }

    /**
     * Handles clear all event
     * @param event clear all event
     */
    @FXML
    private void handleClearAllAction(ActionEvent event) {
        CalculatorButton.valueOf(((Button) event.getSource()));
        model = new Model();
        inputNumber = ZERO;

        displayField.setText(ZERO);
        displayHistory.setText(EMPTY);
    }

    /**
     * Handles backspace event
     * @param event backspace event
     */
    @FXML
    private void handleBackspaceAction(ActionEvent event) {
        CalculatorButton.valueOf(((Button) event.getSource()));
        if (!inputNumber.isEmpty()) {
            if (inputNumber.length() > 1) {
                inputNumber = inputNumber.substring(0, inputNumber.length() - 1);
            } else {
                inputNumber = ZERO;
            }

            model.addNumber(new BigDecimal(inputNumber));
            displayField.setText(inputNumber);
        }
    }

    /**
     * Handles memory add event
     * @param event memory add event
     */
    @FXML
    public void handleMemoryAddAction(ActionEvent event) {
        CalculatorButton.valueOf(((Button) event.getSource()));
        model.addToMemory();
    }

    /**
     * Handles memory subtract event
     * @param event memory subtract event
     */
    @FXML
    public void handleMemorySubtractAction(ActionEvent event) {
        CalculatorButton.valueOf(((Button) event.getSource()));
        model.subtractFromMemory();
    }

    /**
     * Handles memory save event
     * @param event memory save event
     */
    @FXML
    public void handleMemorySaveAction(ActionEvent event) {
        CalculatorButton.valueOf(((Button) event.getSource()));
        model.saveMemory();
    }

    /**
     * Handles memory clear event
     * @param event memory clear event
     */
    @FXML
    public void handleMemoryClearAction(ActionEvent event) {
        CalculatorButton.valueOf(((Button) event.getSource()));
        model.clearMemory();
    }

    /**
     * Handles memory recall event
     * @param event memory recall event
     */
    @FXML
    public void handleMemoryRecallAction(ActionEvent event) {
        CalculatorButton.valueOf(((Button) event.getSource()));
        BigDecimal memory = model.getMemory();
        displayField.setText(memory.toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
