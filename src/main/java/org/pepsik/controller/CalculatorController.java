package org.pepsik.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.pepsik.model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {

    @FXML
    private Label displayField;

    @FXML
    private Label displayHistory;

    private Model model = new Model();

    @FXML
    private void handleDigitAction(ActionEvent event) {
        Button button = ((Button) event.getSource());
        String digit = CalculatorButton.valueOf(button);
        model.addInputDigit(digit);
        displayField.setText(model.getDisplay());
    }

    @FXML
    private void handlePointAction(ActionEvent event) {
        Button button = ((Button) event.getSource());
        String point = CalculatorButton.valueOf(button);
        model.addInputPoint(point);
        displayField.setText(model.getDisplay());
    }

    @FXML
    private void handleOperationAction(ActionEvent event) {
        Button button = ((Button) event.getSource());
        String operator = CalculatorButton.valueOf(button);
        model.addBinaryOperator(operator);
        displayField.setText(model.getDisplay());
        displayHistory.setText(model.getCurrentExpression());
    }

    @FXML
    private void handleUnaryOperationAction(ActionEvent event) {
        String operator = ((Button) event.getSource()).getText();
        model.addUnaryOperator(operator);
        displayField.setText(model.getDisplay());
        displayHistory.setText(model.getCurrentExpression());
    }

    //todo add EQUAL handler

    @FXML
    private void handleClearAction(ActionEvent event) {
        model.clearEntry();
        displayField.setText(model.getDisplay());
    }

    @FXML
    private void handleClearAllAction(ActionEvent event) {
        //todo add Clear All in model
        model = new Model();
        displayField.setText(model.getDisplay());
        displayHistory.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayField.setText(model.getDisplay());
    }
}
