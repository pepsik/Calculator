package org.pepsik.model;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by pepsik on 7/27/2016.
 */
public class Model {
    public static final String SQUARE = "X2";
    public static final String SQRT = "V";
    public static final String PERCENT = "%";
    public static final String FRACTION = "1/X";
    public static final String SUM = "+";
    public static final String SUBTRACT = "-";
    public static final String MULTIPLY = "*";
    public static final String DIVIDE = "/";

    private Deque<Stage> history = new LinkedList<>();
    private Stage activeStage;

    public void addInputNumber(String number) {
        if (activeStage == null) {
            activeStage = new Stage();
        }
        activeStage.addToRightOperand(number);
    }

    public void addOperator(String operator) {
        if (activeStage == null) {
            activeStage = new Stage();
        }

        //what operator is bi ot unar?
        if (isUnaryOperator(operator)) {
            activeStage.addUnaryOperator(operator);
            return;
        }

        if (isBinaryOperator(operator)) {
            if (activeStage.getRightOperand() != null) {
                calculateActiveStage();
                activeStage = new Stage();
                activeStage.setBinaryOperator(operator);
                return;
            }
            activeStage.setBinaryOperator(operator);
        }

        throw new IllegalArgumentException("Unknown operator! " + operator);
    }

    public String getInput() {
        return activeStage.getRightOperand();
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
            case SQRT:
            case FRACTION:
            case PERCENT:
                return true;
        }
        return false;
    }

    private void calculateActiveStage() {
        String rightOperand = activeStage.getRightOperand();//todo types
        List<String> unaryOperators = activeStage.getUnaryOperators();

        if (unaryOperators.size() != 0) {
            for (String unaryOperator : unaryOperators) {
                String result = doOperation(unaryOperator, rightOperand);
                activeStage.setRightOperand(result);
            }
        }

        String binaryOperator = activeStage.getBinaryOperator();

        if (binaryOperator.equals("")) {
            activeStage.setResultOperation(rightOperand);
        } else {
            Stage lastCompletedStage = history.getLast();
            String leftOperand = lastCompletedStage.getResultOperation();
            String result = doOperation(binaryOperator, leftOperand, rightOperand);
            activeStage.setResultOperation(result);
        }
    }

    private String doOperation(String operator, String operand) {
        Double number = Double.parseDouble(operand);

        switch (operator) {
            case SQUARE:
                number *= 2;
                break;
            case SQRT:
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
                throw new IllegalArgumentException("Unknown operator! " + operator);
        }

        return String.valueOf(number);
    }

    private String doOperation(String operator, String leftOperand, String rightOperand) {
        Double leftNumber = Double.parseDouble(leftOperand);
        Double rightNumber = Double.parseDouble(rightOperand);
        Double result;

        switch (operator) {
            case SUM:
                result = leftNumber + rightNumber;
                break;
            case SUBTRACT:
                result = leftNumber - rightNumber;
                break;
            case MULTIPLY:
                result = leftNumber * rightNumber;
                break;
            case DIVIDE:
                result = leftNumber / rightNumber;
                break;
            default:
                throw new IllegalArgumentException("Unknown operator! " + operator);
        }

        return String.valueOf(result);
    }
}
