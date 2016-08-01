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
        String digit = ((Button) event.getSource()).getText();
        model.addInputDigit(digit);
        displayField.setText(model.getDisplay());
    }

    @FXML
    private void handleOperationAction(ActionEvent event) {
        String operation = ((Button) event.getSource()).getText();
        model.addBinaryOperator(operation);
        displayField.setText(model.getDisplay());
        displayHistory.setText(model.getLastExpression());
    }

    @FXML
    private void handleUnaryOperationAction(ActionEvent event) {
        String operator = ((Button) event.getSource()).getText();
        model.addUnaryOperator(operator);
        displayField.setText(model.getDisplay());
        displayHistory.setText(model.getLastExpression());
    }

    @FXML
    private void handleClearEntryAction(ActionEvent event) {
        model.clearEntry();
        displayField.setText(model.getDisplay());
    }

    @FXML
    private void handleClearAllAction(ActionEvent event) {
        displayField.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayField.setText(model.getDisplay());
    }
}
