package org.pepsik.model;

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

    private Deque<Stage> lastHistory = new LinkedList<>();
    private List<Deque<Stage>> allHistory = new ArrayList<>();
    private Stage activeStage;
    private String displayField = ZERO;

    public void addInputDigit(String number) {
        if (activeStage == null) {
            activeStage = new Stage();
        }

        activeStage.addDigitToOperand(number);
        displayField = activeStage.getOperand();
    }

    public void addBinaryOperator(String operator) {
        if (operator.equals(EQUAL)) {
            if (activeStage == null) {
                activeStage = new Stage();
                calculateEqual();
            } else {
                calculateEqual();
            }
            return;
        }

        // create new stage with operator
        if (activeStage == null) {
            activeStage = new Stage();
            activeStage.setBinaryOperator(operator);
            return;
        }

        //if Stage consists only operand then add it to lastHistory (its first stage)
        if (activeStage.getBinaryOperator().equals(EMPTY) && !activeStage.getOperand().equals(EMPTY)) {
            activeStage.setResultOperation(activeStage.getOperand());
            lastHistory.addLast(activeStage);
            activeStage = new Stage();
        }

        //stage with operator
        if (activeStage.getOperand().equals(EMPTY)) {
            activeStage.setBinaryOperator(operator);
        } else { //stage with operator and operand
            calculateBinary();
            activeStage = new Stage();
            activeStage.setBinaryOperator(operator);
        }
    }

    public void addUnaryOperator(String operator) {
        if (activeStage == null) {
            activeStage = new Stage();
        }

        activeStage.addUnaryOperator(operator);
        calculateUnary();

    }

    public String getDisplay() {
        return displayField;
    }

    public String getLastHistory() {
        return lastHistory.toString();
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
        if (activeStage != null) {
            activeStage.setOperand(ZERO);
        }

        displayField = ZERO;
    }

    private void calculateBinary() {
        final String activeStageOperand = activeStage.getOperand(); //todo rename operand to activeStageoperand
        final String binaryOperator = activeStage.getBinaryOperator();

        //throw if get Stage without operator or operand
        if (binaryOperator.equals(EMPTY) || activeStageOperand.equals(EMPTY)) {
            throw new IllegalStateException("Unexpected state! " + activeStage);
        }

        String leftOperand;
        //create non input first Stage with ZERO if lastHistory empty
        if (lastHistory.isEmpty()) {
            Stage firstStage = new Stage();
            firstStage.setOperand(ZERO);
            firstStage.setResultOperation(ZERO);
            lastHistory.addFirst(firstStage);
            leftOperand = ZERO;
        } else { //get last stage for left operand
            Stage lastCompletedStage = lastHistory.getLast();
            leftOperand = lastCompletedStage.getResultOperation();
        }

        String result = doOperation(binaryOperator, leftOperand, activeStageOperand);
        activeStage.setResultOperation(result);
        displayField = result;
        lastHistory.addLast(activeStage);
        activeStage = null;
    }

    private void calculateUnary() {
        final String binaryOperator = activeStage.getBinaryOperator();
        final String activeStageOperand = activeStage.getOperand();

        // operator - exist;  operand - exist
        if (!binaryOperator.equals(EMPTY) && !activeStageOperand.equals(EMPTY)) {
            String temp = activeStage.getOperand();

            for (String unary : activeStage.getUnaryOperators()) {
                temp = doOperation(unary, temp);
            }

            displayField = temp;
            return;
        }

        // operator - exist; operand - empty
        if (!binaryOperator.equals(EMPTY) && activeStageOperand.equals(EMPTY)) {
            if (lastHistory.isEmpty()) {

            } else {

            }

            return;
        }

        // operator - empty ; operand - exist
        if (binaryOperator.equals(EMPTY) && !activeStageOperand.equals(EMPTY)) {
            if (getLastBinaryOperationStage() != null) {

                return;
            } else { //if lastHistory is empty or binary operation stage not found then copy and add active Stage

                return;
            }
        }

        // operator - empty ; operand - empty
        if (binaryOperator.equals(EMPTY) && activeStageOperand.equals(EMPTY)) {
            if (lastHistory.isEmpty()) {

                return;
            }

            if (getLastBinaryOperationStage() != null) {

            } else {

            }
        }
    }

    private void calculateEqual() {
        final String binaryOperator = activeStage.getBinaryOperator(); //todo: add final to local fields
        final String activeStageOperand = activeStage.getOperand();     //todo: hide in if?

        // operator - exist;  operand - exist
        if (!binaryOperator.equals(EMPTY) && !activeStageOperand.equals(EMPTY)) {
            calculateBinary();

            //after calculation add result stage to lastHistory with only operand (this stage represent EQUAL operation)
            Stage lastStage = lastHistory.getLast();
            Stage result = new Stage();
            result.setOperand(lastStage.getResultOperation());
            result.setResultOperation(lastStage.getResultOperation());
            lastHistory.addLast(result);

            allHistory.add(lastHistory);
            lastHistory = new LinkedList<>();
            activeStage = new Stage();
            activeStage.setOperand(result.getOperand());
            activeStage.setResultOperation(result.getResultOperation());
            lastHistory.add(activeStage);

            displayField = activeStage.getResultOperation();
            return;
        }

        // operator - exist; operand - empty
        if (!binaryOperator.equals(EMPTY) && activeStageOperand.equals(EMPTY)) {
            if (lastHistory.isEmpty()) {
                activeStage.setOperand(ZERO);
                activeStage.setResultOperation(ZERO);
                calculateBinary();
            } else {
                Stage lastCompleteStage = lastHistory.getLast();
                activeStage.setOperand(lastCompleteStage.getOperand());
                calculateBinary();
            }

            //add result stage to lastHistory
            Stage lastStage = lastHistory.getLast();
            Stage result = new Stage();
            result.setOperand(lastStage.getResultOperation());
            result.setResultOperation(lastStage.getResultOperation());
            lastHistory.addLast(result);

            allHistory.add(lastHistory);
            lastHistory = new LinkedList<>();
            activeStage = new Stage();
            activeStage.setOperand(result.getOperand());
            activeStage.setResultOperation(result.getResultOperation());
            lastHistory.add(activeStage);

            displayField = activeStage.getResultOperation();
            return;
        }

        // operator - empty ; operand - exist
        if (binaryOperator.equals(EMPTY) && !activeStageOperand.equals(EMPTY)) {
            if (getLastBinaryOperationStage() != null) {
                Stage stage = getLastBinaryOperationStage();
                activeStage.setResultOperation(activeStageOperand);
                lastHistory.addLast(activeStage);

                Stage clone = new Stage();
                clone.setOperand(stage.getOperand());
                clone.setBinaryOperator(stage.getBinaryOperator());
                activeStage = clone;
                calculateBinary();

                Stage result = new Stage();
                Stage last = lastHistory.getLast();
                result.setOperand(last.getResultOperation());
                result.setResultOperation(last.getResultOperation());
                allHistory.add(lastHistory);
                lastHistory = new LinkedList<>();
                activeStage = new Stage();
                activeStage.setOperand(result.getOperand());
                activeStage.setResultOperation(result.getResultOperation());
                lastHistory.add(activeStage);

                displayField = activeStage.getResultOperation();
                return;
            } else { //if lastHistory is empty or binary operation stage not found then copy and add active Stage
                activeStage.setResultOperation(activeStageOperand);
                lastHistory.addLast(activeStage);

                //copy and add result stage to lastHistory
                Stage result = new Stage();
                result.setOperand(activeStageOperand);
                result.setResultOperation(activeStageOperand);
                allHistory.add(lastHistory);
                lastHistory = new LinkedList<>();
                activeStage = new Stage();
                activeStage.setOperand(result.getOperand());
                activeStage.setResultOperation(result.getResultOperation());
                lastHistory.add(activeStage);

                displayField = activeStage.getResultOperation();
                return;
            }
        }

        // operator - empty ; operand - empty
        if (binaryOperator.equals(EMPTY) && activeStageOperand.equals(EMPTY)) {
            if (lastHistory.isEmpty()) {
                activeStage.setOperand(ZERO);
                activeStage.setResultOperation(ZERO);

                Stage firstStage = new Stage();
                firstStage.setOperand(ZERO);
                firstStage.setResultOperation(ZERO);
                lastHistory.addFirst(firstStage);

                displayField = ZERO;

                lastHistory.addLast(activeStage);
                activeStage = null;
                return;
            }

            if (getLastBinaryOperationStage() != null) {
                Stage stage = getLastBinaryOperationStage();
                activeStage.setOperand(stage.getOperand());
                //activeStage.setResultOperation(stage.getResultOperation());
                activeStage.setBinaryOperator(stage.getBinaryOperator());

                calculateBinary();

                Stage lastStage = lastHistory.getLast();
                Stage result = new Stage();
                result.setOperand(lastStage.getResultOperation());
                result.setResultOperation(lastStage.getResultOperation());
                lastHistory.addLast(result);

                displayField = result.getResultOperation();
                activeStage = null;
            } else {
                Stage last = lastHistory.getLast();
                activeStage.setOperand(last.getResultOperation());
                activeStage.setResultOperation(last.getResultOperation());
                lastHistory.addLast(activeStage);

                displayField = activeStage.getResultOperation();
                activeStage = null;
            }
        }
    }

    private Stage getLastBinaryOperationStage() {
        Iterator<Stage> iterator = lastHistory.descendingIterator();
        while (iterator.hasNext()) {
            Stage stage = iterator.next();
            if (!stage.getBinaryOperator().equals(EMPTY)) {
                return stage;
            }
        }
        return null;
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
                Double lastResult = Double.valueOf(lastHistory.getLast().getResultOperation());
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
