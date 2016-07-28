package org.pepsik.model;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by pepsik on 7/27/2016.
 */
public class Model {
    private static final String SQUARE = "X2";
    private static final String SQUARE_ROOT = "V";
    private static final String PERCENT = "%";
    private static final String FRACTION = "1/X";
    private static final String EQUAL = "=";

    private static final String SUM = "+";
    private static final String SUBTRACT = "-";
    private static final String MULTIPLY = "*";
    private static final String DIVIDE = "/";

    private static final String EMPTY = "";
    private static final String ZERO = "0";

    private Deque<Stage> history = new LinkedList<>();
    private Stage activeStage;
    private String displayField;

    public void addInputDigit(String number) {
        if (activeStage == null) {
            activeStage = new Stage();
        }
        activeStage.addDigitToOperand(number);
        displayField = activeStage.getOperand();
    }

    public void addOperator(String operator) {
        if (operator.equals(EQUAL)) {
            if (activeStage == null) {
                activeStage = new Stage();
                calculateEqual();
            } else {
                calculateEqual();
            }
            return;
        }


//        if (isUnaryOperator(operator)) {
//            activeStage.addUnaryOperator(operator); //calculate unary
//            return;
//        }

//        if (isBinaryOperator(operator)) {


        if (activeStage == null) {
            activeStage = new Stage();
            activeStage.setBinaryOperator(operator);
            return;
        }

        if (activeStage.getOperand().equals(EMPTY)) {
            activeStage.setBinaryOperator(operator);
        } else {
            calculate();
            activeStage = new Stage();
            activeStage.setBinaryOperator(operator);
        }


//            return;
//        }

//        throw new IllegalArgumentException("Unknown operator! " + operator);
    }

    public String getDisplay() {
        return displayField;
    }

    private boolean isBinaryOperator(String operator) {
        switch (operator) {
            case SUM:
            case SUBTRACT:
            case MULTIPLY:
            case DIVIDE:
                return true;
        }
        return false;
    }

    private boolean isUnaryOperator(String operator) {
        switch (operator) {
            case SQUARE:
            case SQUARE_ROOT:
            case FRACTION:
            case PERCENT:
                return true;
        }
        return false;
    }

    public void clearEntry() { //todo: refactor
        if (activeStage == null) {
            return;
        } else {
            activeStage.setOperand(ZERO);
        }
    }

    private void calculate() {
//        List<String> unaryOperators = activeStage.getUnaryOperators();

//        for (String unaryOperator : unaryOperators) {
//            String result = doOperation(unaryOperator, rightOperand);
//            activeStage.setOperand(result);
//        }

        String rightOperand = activeStage.getOperand();
        String binaryOperator = activeStage.getBinaryOperator();

        // empty 5
        if (binaryOperator.equals(EMPTY)) {
            activeStage.setResultOperation(activeStage.getOperand());
            history.addLast(activeStage);
            activeStage = null;
            return;
        }

        String leftOperand = ZERO;
        if (!history.isEmpty()) {
            Stage lastCompletedStage = history.getLast();
            leftOperand = lastCompletedStage.getResultOperation();
        }

//        if (rightOperand.equals(EMPTY)) {
//            rightOperand = leftOperand;
//        }

        String result = doOperation(binaryOperator, leftOperand, rightOperand);
        activeStage.setResultOperation(result);
        displayField = result;
        history.addLast(activeStage);
        activeStage = null;
    }

    private void calculateEqual() {
        // "+" empty
        if (!activeStage.getBinaryOperator().equals(EMPTY) && activeStage.getOperand().equals(EMPTY)) {
            if (history.isEmpty()) {
                activeStage.setOperand(ZERO);
                activeStage.setResultOperation(ZERO);
            } else {
                Stage lastCompleteStage = history.getLast();

                String operator = activeStage.getBinaryOperator();
                String rightOperand = lastCompleteStage.getResultOperation();
                activeStage.setOperand(rightOperand);
                String leftOperand = rightOperand;

                String result = doOperation(operator, leftOperand, rightOperand);
                activeStage.setResultOperation(result);
            }

            displayField = activeStage.getResultOperation();

            history.addLast(activeStage);
            activeStage = null;
            return;
        }

        // "+" 5
        if (!activeStage.getBinaryOperator().equals(EMPTY) && !activeStage.getOperand().equals(EMPTY)) {
            Stage lastCompleteStage = history.getLast();

            String operator = activeStage.getBinaryOperator();
            String rightOperand = activeStage.getOperand();
            String leftOperand = lastCompleteStage.getOperand();

            String result = doOperation(operator, leftOperand, rightOperand);
            activeStage.setResultOperation(result);

            displayField = result;

            history.addLast(activeStage);
            activeStage = null;
            return;
        }

        //ignore operand if has lastStage, use lastStage operand
        // empty "5"
        if (activeStage.getBinaryOperator().equals(EMPTY) && !activeStage.getOperand().equals(EMPTY)) {
            if (history.isEmpty()) {
                String operand = activeStage.getOperand();
                activeStage.setResultOperation(operand);
            } else { // 2 situations : 1) lastStage has only operand, 2) last stage has operand and operator
                Stage lastCompleteStage = history.getLast();

                //1
                String operator = lastCompleteStage.getBinaryOperator();
                if (operator.equals(EMPTY)) {
                    activeStage.setResultOperation(activeStage.getOperand());
                    displayField = activeStage.getResultOperation();
                    history.addLast(activeStage);
                    activeStage = null;
                    return;
                }

                String rightOperand = lastCompleteStage.getOperand();
                String leftOperand = activeStage.getOperand();
                String result = doOperation(operator, leftOperand, rightOperand);
                activeStage.setResultOperation(result);
                activeStage.setBinaryOperator(operator);

                displayField = activeStage.getResultOperation();

                history.addLast(activeStage);
                activeStage = null;
                return;
            }
        }

        // empty empty
        if (activeStage.getBinaryOperator().equals(EMPTY) && activeStage.getOperand().equals(EMPTY)) {
            if (history.isEmpty()) {
                activeStage.setOperand(ZERO);
                activeStage.setResultOperation(ZERO);

                history.addLast(activeStage);
                activeStage = null;
            } else {
                Stage lastCompleteStage = history.getLast();

                String operator = lastCompleteStage.getBinaryOperator();

                if (operator.equals(EMPTY)) {
                    String result = lastCompleteStage.getResultOperation();
                    activeStage.setOperand(result);
                    activeStage.setResultOperation(result);
                    displayField = activeStage.getResultOperation();
                    history.addLast(activeStage);
                    activeStage = null;
                    return;
                }

                String leftOperand = lastCompleteStage.getOperand();
                String rightOperand = lastCompleteStage.getResultOperation();

                String result = doOperation(operator, leftOperand, rightOperand);
                activeStage.setOperand(leftOperand);
                activeStage.setBinaryOperator(operator);
                activeStage.setResultOperation(result);

                displayField = result;

                history.addLast(activeStage);
                activeStage = null;
            }
        }
    }

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
                Double lastResult = Double.valueOf(history.getLast().getResultOperation());
                number = lastResult * (number / 100);
                break;
            default:
                throw new IllegalArgumentException("Unknown unary operator! " + operator);
        }

        return String.valueOf(number);
    }

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
}
