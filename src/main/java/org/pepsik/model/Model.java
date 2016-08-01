package org.pepsik.model;

import org.pepsik.util.HistoryFormatter;

import java.util.*;

/**
 * Created by pepsik on 7/27/2016.
 */
public class Model {
    private static final String SQUARE = "X2";
    private static final String SQUARE_ROOT = "√";
    private static final String PERCENT = "%";
    private static final String FRACTION = "1/X";
    private static final String EQUAL = "=";

    private static final String SUM = "+";
    private static final String SUBTRACT = "−";
    private static final String MULTIPLY = "×";
    private static final String DIVIDE = "÷";

    private static final String EMPTY = " ";
    private static final String ZERO = "0";

    private Deque<Stage> currentExpression = new ArrayDeque<>();
    private List<Deque<Stage>> history = new ArrayList<>();
    private Stage currentStage = new Stage();
    private Stage lastBinaryStage;
    private String displayField = ZERO;

    /**
     * Adds input digit or point to active stage and show expression on display field
     *
     * @param number input digit or point
     */
    public void addInputDigit(String number) {
        currentStage.addDigitToOperand(number);

        displayField = currentStage.getOperand();
    }

    /**
     * Adds binary operator to expression and show show expression on display field
     *
     * @param inputOperator input binary operator
     */
    public void addBinaryOperator(String inputOperator) {
        //EQUAL operator is unique and calculated separately
        if (inputOperator.equals(EQUAL)) {
            calculateEqual();
            displayField = getLastStage().getResultOperation();
            return;
        }

        final String operand = currentStage.getOperand();
        final String binaryOperator = currentStage.getBinaryOperator();

        //Calculates if stage consist operator and operand {+, 7}
        if (!binaryOperator.equals(EMPTY) && !operand.equals(EMPTY)) {
            calculateBinary();
        }

        //If active stage consist only operand {empty, 5} then adds it as first stage in expression
        if (binaryOperator.equals(EMPTY) && !operand.equals(EMPTY)) {
            currentStage.setResultOperation(operand);
            currentExpression.addFirst(currentStage);
            currentStage = new Stage(); //todo add to expression
        }

        //in other cases adds operator to active stage
        currentStage.setBinaryOperator(inputOperator);

        displayField = getLastStage().getResultOperation();
    }

    /**
     * Adds and calculates unary operator and show expression on display field
     *
     * @param operator unary operator
     */
    public void addUnaryOperator(String operator) {
        currentStage.addUnaryOperator(operator);
        calculateUnary();

//        displayField = currentStage;
    }

    /**
     * Calculates result of active stage and adds to expression history
     */
    private void calculateBinary() {
        final String rightOperand = currentStage.getOperand();
        final String binaryOperator = currentStage.getBinaryOperator();

        //throw if get Stage without operator or operand
        if (binaryOperator.equals(EMPTY) || rightOperand.equals(EMPTY)) {
            throw new IllegalStateException("Unexpected state! " + currentStage);
        }

        //gets last stage to get left operand (result of last completed stage)
        String leftOperand = getLastStage().getResultOperation();
        String result = doOperation(binaryOperator, leftOperand, rightOperand);
        currentStage.setResultOperation(result);

        currentExpression.addLast(currentStage);
        lastBinaryStage = currentStage;
        currentStage = new Stage();
    }

    /**
     * Calculates unary operation in active stage
     */
    private void calculateUnary() {
        final String operand = currentStage.getOperand();
        final String binaryOperator = currentStage.getBinaryOperator();

        // operator - exist;  operand - exist
        if (!binaryOperator.equals(EMPTY) && !operand.equals(EMPTY)) {
            String temp = currentStage.getOperand();

            for (String unary : currentStage.getUnaryOperators()) {
                temp = doOperation(unary, temp);
            }

            displayField = temp;
            return;
        }

        // operator - exist; operand - empty
        if (!binaryOperator.equals(EMPTY) && operand.equals(EMPTY)) {
            if (currentExpression.isEmpty()) {

            } else {

            }

            return;
        }

        // operator - empty ; operand - exist
        if (binaryOperator.equals(EMPTY) && !operand.equals(EMPTY)) {
            if (lastBinaryStage != null) {

                return;
            } else { //if currentExpression is empty or binary operation stage not found then copy and add active Stage

                return;
            }
        }

        // operator - empty ; operand - empty
        if (binaryOperator.equals(EMPTY) && operand.equals(EMPTY)) {
            if (currentExpression.isEmpty()) {

                return;
            }

            if (lastBinaryStage != null) {

            } else {

            }
        }
    }

    /**
     * Calculates EQUAL operation and adds to expression history.
     */
    private void calculateEqual() {
        final String operand = currentStage.getOperand();     //todo: hide in if?
        final String binaryOperator = currentStage.getBinaryOperator(); //todo need final?
        final Stage result = new Stage(); //result stage
        result.setBinaryOperator(EQUAL);

        // operator - exist; operand - empty
        if (!binaryOperator.equals(EMPTY) && operand.equals(EMPTY)) {
            currentStage.setOperand(getLastStage().getResultOperation());
            calculateBinary();
        }

        // operator - exist;  operand - exist
        if (!binaryOperator.equals(EMPTY) && !operand.equals(EMPTY)) {
            calculateBinary();
        }

        // operator - empty ; operand - exist
        if (binaryOperator.equals(EMPTY) && !operand.equals(EMPTY)) {
            if (lastBinaryStage != null) {
                currentStage.setResultOperation(operand);
                currentExpression.addLast(currentStage);

                Stage binaryStage = lastBinaryStage;
                currentStage = new Stage(binaryStage);
                calculateBinary();
            } else {
                currentStage.setResultOperation(operand);
                currentExpression.addLast(currentStage);
            }
        }

        // operator - empty ; operand - empty
        if (binaryOperator.equals(EMPTY) && operand.equals(EMPTY)) {
            currentExpression.addFirst(getLastStage());

            if (lastBinaryStage != null) {
                currentStage.setOperand(lastBinaryStage.getOperand());
                currentStage.setBinaryOperator(lastBinaryStage.getBinaryOperator());
                calculateBinary();
            }
        }

        Stage last = getLastStage();
        currentStage = new Stage();
        currentStage.setOperand(last.getResultOperation());
        currentStage.setResultOperation(last.getResultOperation());

        currentExpression.addLast(currentStage);
        history.add(currentExpression);
        currentExpression = new ArrayDeque<>();
        currentStage = new Stage();
    }

    private Stage getLastStage() {
        if (currentExpression.isEmpty()) {
            if (history.size() > 0) {
                return history.get(history.size() - 1).getLast();
            } else {
                Stage stage = new Stage();
                stage.setOperand(ZERO);
                stage.setResultOperation(ZERO);
                return stage;
            }
        } else {
            return currentExpression.getLast();
        }
    }

    /**
     * Calculates unary operation
     *
     * @param operator unary operator
     * @param operand  operand deal with
     * @return operation result
     */
    private String doOperation(String operator, String operand) {
        Double number = Double.parseDouble(operand);

        switch (operator) {
            case SQUARE:
                number *= 2;
                break;
            case SQUARE_ROOT:
                number = Math.sqrt(number);
                break;
            case FRACTION:
                number = 1 / number;
                break;
            case PERCENT:
                Double lastResult = Double.valueOf(currentExpression.getLast().getResultOperation());
                number = lastResult * (number / 100);
                break;
            default:
                throw new IllegalArgumentException("Unknown unary operator! " + operator);
        }

        return String.valueOf(number);
    }

    /**
     * Calculates binary operation
     *
     * @param operator     binary operator
     * @param leftOperand  left operand of operation
     * @param rightOperand right operand of operation
     * @return result of operation
     */
    private String doOperation(String operator, String leftOperand, String rightOperand) {
        Double left = Double.parseDouble(leftOperand);
        Double right = Double.parseDouble(rightOperand);
        Double result;

        switch (operator) {
            case SUM:
                result = left + right;
                break;
            case SUBTRACT:
                result = left - right;
                break;
            case MULTIPLY:
                result = left * right;
                break;
            case DIVIDE:
                result = left / right;
                break;
            default:
                throw new IllegalArgumentException("Unknown binary operator! " + operator);
        }

        return String.valueOf(result);
    }

    /**
     * Abstraction of calculator display
     *
     * @return calculator display view
     */
    public String getDisplay() {
        return displayField;
    }

    /**
     * Current expression history
     *
     * @return calculator history view
     */
    public String getCurrentExpression() {
        //todo history display
        return HistoryFormatter.format(currentExpression);

    }

    /**
     * Clear stage operand and show on display field
     */
    public void clearEntry() {
        currentStage.setOperand(ZERO);

        displayField = ZERO;
    }
}
