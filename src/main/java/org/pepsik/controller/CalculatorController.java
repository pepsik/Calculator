package org.pepsik.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.pepsik.model.Model;
import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {

    @FXML
    private Label displayField;

    @FXML
    private Label displayHistory;

    String inputNumber;
    private Model model = new Model();

    @FXML
    private void handleDigitAction(ActionEvent event) {
        String digit = CalculatorButton.valueOf((Button) event.getSource());

        if (inputNumber == null) {
            inputNumber = digit;
        } else {
            inputNumber += digit;
        }

        model.addNumber(new BigDecimal(inputNumber));
        displayField.setText(model.getDisplay().toString());
    }

    @FXML
    private void handlePointAction(ActionEvent event) {
        String point = CalculatorButton.valueOf(((Button) event.getSource()));
        inputNumber += point;
        displayField.setText(model.getDisplay().toString());
    }

    @FXML
    private void handleBinaryOperationAction(ActionEvent event) {
        inputNumber = null;
        String operator = CalculatorButton.valueOf(((Button) event.getSource()));
        model.addBinaryOperator(BinaryOperation.find(operator.charAt(0)));

        displayField.setText(model.getDisplay().toString());
        displayHistory.setText(model.getCurrentExpression());
    }

    @FXML
    private void handleUnaryOperationAction(ActionEvent event) {
        String operator = CalculatorButton.valueOf(((Button) event.getSource()));
        model.addUnaryOperator(UnaryOperation.find(operator));
        displayField.setText(model.getDisplay().toString());
        displayHistory.setText(model.getCurrentExpression());
    }

    //todo add EQUAL handler

    @FXML
    private void handleClearAction(ActionEvent event) {
        model.clearEntry();
        displayField.setText(model.getDisplay().toString());
    }

    @FXML
    private void handleClearAllAction(ActionEvent event) {
//        //todo add Clear All in model
        model = new Model();
        displayField.setText(model.getDisplay().toString());
        displayHistory.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        displayField.setText(model.getDisplay());
    }
}
