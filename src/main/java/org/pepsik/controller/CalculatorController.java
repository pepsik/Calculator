package org.pepsik.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.pepsik.controller.button.CalculatorButton;
import org.pepsik.controller.util.InputNumber;
import org.pepsik.model.Model;
import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.Constant;
import org.pepsik.model.operation.UnaryOperation;
import org.pepsik.controller.util.TextFormatter;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

/**
 * Controller for handle calculator events and display calculation results and history
 */
public class CalculatorController implements Initializable {

    /**
     * Used for display field
     */
    private static final String ZERO = "0";
    /**
     * Used for history field
     */
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
     * Block calculation button if error occurs
     * Can be removed by Clear, ClearALL button
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
        String number = CalculatorButton.valueOf((Button) event.getSource());

        if (noError) {
            InputNumber.addToInput(new BigDecimal(number));
            model.addNumber(InputNumber.getInput());

            displayField.setText(TextFormatter.formatInputNumber());

        }
    }

    /**
     * Handles point event
     *
     * @param event point event
     */
    @FXML
    private void handlePointAction(ActionEvent event) {
        CalculatorButton.valueOf((Button) event.getSource());
        DecimalFormat f = new DecimalFormat(); //todo exlude to field

        if (noError) {
            f.applyPattern("###,###.#################");

            if (!InputNumber.isPointSet()) {
                InputNumber.addPoint();
            }

            displayField.setText(TextFormatter.formatInputNumber());
        }
    }

    /**
     * Handles binary operation event
     *
     * @param event binary event
     */
    @FXML
    private void handleBinaryOperationAction(ActionEvent event) {
        String operator = CalculatorButton.valueOf((Button) event.getSource());
        int scale = 16; //scale for binary operations display output

        if (noError) {
            try {
                InputNumber.clearInput();
                model.addBinaryOperator(BinaryOperation.find(operator.charAt(0)));//todo replace with mapping

                checksLimit(model.getResult());

                displayField.setText(TextFormatter.display(model.getResult(), scale));
                displayHistory.setText(TextFormatter.history(model.getCurrentExpression(), model.getOperand()));
            } catch (ArithmeticException e) {
                noError = false;

                displayField.setText("Cannot divide by zero");
            } catch (RuntimeException ex) {
                noError = false;

                displayField.setText("Limit reached!");
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
        String operator = CalculatorButton.valueOf((Button) event.getSource());
        int scale = 15;// scale for unary operations display output

        if (noError) {
            try {
                InputNumber.clearInput();
                model.addUnaryOperator(UnaryOperation.find(operator));

                displayField.setText(TextFormatter.display(model.getOperand(), scale));
                displayHistory.setText(TextFormatter.history(model.getCurrentExpression(), model.getOperand()));
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
        CalculatorButton.valueOf((Button) event.getSource());

        model.clearEntry();

        if (!noError) {
            displayHistory.setText("");
        } else {
            displayHistory.setText(TextFormatter.history(model.getCurrentExpression(), model.getOperand()));
        }

        InputNumber.clearInput();
        noError = true;
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
        CalculatorButton.valueOf((Button) event.getSource());

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
        CalculatorButton.valueOf((Button) event.getSource());

        if (noError) {
            model.addToMemory();
        }

        InputNumber.clearInput();
        setDisableMemoryClearAndRecallButton(false);
    }

    /**
     * Handles memory subtract event
     *
     * @param event memory subtract event
     */
    @FXML
    private void handleMemorySubtractAction(ActionEvent event) {
        CalculatorButton.valueOf((Button) event.getSource());

        if (noError) {
            model.subtractFromMemory();
        }

        InputNumber.clearInput();
        setDisableMemoryClearAndRecallButton(false);
    }

    /**
     * Handles memory save event
     *
     * @param event memory save event
     */
    @FXML
    private void handleMemorySaveAction(ActionEvent event) {
        CalculatorButton.valueOf((Button) event.getSource());

        if (noError) {
            model.saveMemory();
        }

        InputNumber.clearInput();
        setDisableMemoryClearAndRecallButton(false);
    }

    /**
     * Handles memory clear event
     *
     * @param event memory clear event
     */
    @FXML
    private void handleMemoryClearAction(ActionEvent event) {
        CalculatorButton.valueOf((Button) event.getSource());

        if (noError) {
            model.clearMemory();
        }

        InputNumber.clearInput();
        setDisableMemoryClearAndRecallButton(true);
    }

    /**
     * Handles memory recall event
     *
     * @param event memory recall event
     */
    @FXML
    private void handleMemoryRecallAction(ActionEvent event) {
        CalculatorButton.valueOf((Button) event.getSource());
        int scale = 16; //scale for output memory SCALE

        if (noError) {
            BigDecimal memory = model.getMemory();

            if (memory != null) {
                displayField.setText(TextFormatter.display(memory, scale));
            }
        }

        InputNumber.clearInput();
    }

    /**
     * Disable clear and recall buttons
     *
     * @param b true when disable
     */
    private void setDisableMemoryClearAndRecallButton(boolean b) {
        CalculatorButton.MEMORY_CLEAR.getButton().setDisable(b);
        CalculatorButton.MEMORY_RECALL.getButton().setDisable(b);
    }

    private void checksLimit(BigDecimal bg) {
        if (bg.compareTo(BigDecimal.ZERO) != 0) {
            //lower limit
            if (bg.abs().movePointRight(Constant.SCALE / 2).compareTo(BigDecimal.ONE) < 0) {
                System.out.println(bg);
                throw new RuntimeException("Limit is reached!");
            }
            //upper limit
            if (bg.precision() - bg.scale() > Constant.SCALE / 2) {
                throw new RuntimeException("Limit is reached!");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayField.setText(ZERO);
    }
}
