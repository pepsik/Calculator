package org.pepsik.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import org.pepsik.model.operation.Constant;
import org.pepsik.model.operation.UnaryOperation;
import org.pepsik.controller.util.TextFormatter;
import org.pepsik.view.UIChanger;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

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
        //todo exlude to field
        if (noError) {
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
                model.addBinaryOperator(BinaryOperation.find(operator));//todo replace with mapping

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

                checksLimit(model.getOperand());

                displayField.setText(TextFormatter.display(model.getOperand(), scale));
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
     * Handles Clear entry event
     *
     * @param event clear event
     */
    @FXML
    private void handleClearAction(ActionEvent event) {
        CalculatorButton.valueOf((Button) event.getSource());

        model.clearEntry();
        InputNumber.clearInput();
        displayField.setText(ZERO.toString());

        if (!noError) {
            displayHistory.setText("");
        } else {
            displayHistory.setText(TextFormatter.history(model.getCurrentExpression(), model.getOperand()));
        }

        noError = true;
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

        displayField.setText("0");
        displayHistory.setText("");
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
                displayField.setText("0");
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
        UIChanger.disableMemoryClearAndRecallButton(false);
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
        UIChanger.disableMemoryClearAndRecallButton(false);
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
        UIChanger.disableMemoryClearAndRecallButton(false);
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
        UIChanger.disableMemoryClearAndRecallButton(true);
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

    private void checksLimit(BigDecimal bg) {
        if (bg.compareTo(ZERO) != 0) {
            //lower limit
            if (bg.abs().movePointRight(Constant.SCALE / 2).compareTo(BigDecimal.ONE) < 0) {
                throw new RuntimeException("Limit is reached!");
            }
            //upper limit
            if (bg.precision() - bg.scale() > Constant.SCALE / 2) {
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
        CalculatorButton.setButtons(root);
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

    public void setStageAndSetupListeners(Stage stage) {
        stage.setTitle("Calculator");

        //Init max, min, pref sizes
        setUpApplicationSizes(stage);

        //Setup all node button to enums
        setUpButtons(displayField.getParent());

        //Init listeners for shortcut keys
        initKeyboardShortcutListeners(displayField.getScene());

        //Init listeners for resizing button
        initResizeListeners(displayField.getScene());

        displayField.setText("0");
    }
}
