package org.pepsik.model;

import java.util.Deque;
import java.util.Iterator;
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

        // create new stage with operator
        if (activeStage == null) {
            activeStage = new Stage();
            activeStage.setBinaryOperator(operator);
            return;
        }

        //if get Stage only with operand add to history (its input first stage)
        if (activeStage.getBinaryOperator().equals(EMPTY) && !activeStage.getOperand().equals(EMPTY)) {
            activeStage.setResultOperation(activeStage.getOperand());
            history.addLast(activeStage);
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

    private void calculateBinary() {
//        List<String> unaryOperators = activeStage.getUnaryOperators();

//        for (String unaryOperator : unaryOperators) {
//            String result = doOperation(unaryOperator, rightOperand);
//            activeStage.setOperand(result);
//        }

        final String rightOperand = activeStage.getOperand(); //todo rename operand to activeStageoperand
        String binaryOperator = activeStage.getBinaryOperator();

        //throw if get Stage without operator or operand
        if (binaryOperator.equals(EMPTY) || rightOperand.equals(EMPTY)) {
            throw new IllegalStateException("Unexpected state! " + activeStage);
        }

        String leftOperand;
        //create non input first Stage with ZERO if history empty
        if (history.isEmpty()) {
            Stage firstStage = new Stage();
            firstStage.setOperand(ZERO);
            firstStage.setResultOperation(ZERO);
            history.addFirst(firstStage);
            leftOperand = ZERO;
        } else { //get last stage for left operand
            Stage lastCompletedStage = history.getLast();
            leftOperand = lastCompletedStage.getResultOperation();
        }

        String result = doOperation(binaryOperator, leftOperand, rightOperand);
        activeStage.setResultOperation(result);
        displayField = result;
        history.addLast(activeStage);
        activeStage = null;
    }

    private void calculateEqual() {
        final String binaryOperator = activeStage.getBinaryOperator(); //todo: add final to local fields
        final String activeStageOperand = activeStage.getOperand();     //todo: hide in if?

        // operator - exist;  operand - exist
        if (!binaryOperator.equals(EMPTY) && !activeStageOperand.equals(EMPTY)) {
            calculateBinary();

            //after calculation add result stage to history with only operand (this stage represent EQUAL operation)
            Stage lastStage = history.getLast();
            Stage stage = new Stage();
            stage.setOperand(lastStage.getResultOperation());
            stage.setResultOperation(lastStage.getResultOperation());
            history.addLast(stage);

            displayField = stage.getResultOperation();
            activeStage = null;
            return;
        }

        // operator - exist; operand - empty
        if (!binaryOperator.equals(EMPTY) && activeStageOperand.equals(EMPTY)) {
            if (history.isEmpty()) {
                activeStage.setOperand(ZERO);
                activeStage.setResultOperation(ZERO);
                calculateBinary();
            } else {
                Stage lastCompleteStage = history.getLast();
                activeStage.setOperand(lastCompleteStage.getOperand());
                calculateBinary();
            }

            //add result stage to history
            Stage lastStage = history.getLast();
            Stage stage = new Stage();
            stage.setOperand(lastStage.getResultOperation());
            stage.setResultOperation(lastStage.getResultOperation());
            history.addLast(stage);

            displayField = stage.getResultOperation();
            activeStage = null;
            return;
        }

        // operator - empty ; operand - exist
        if (binaryOperator.equals(EMPTY) && !activeStageOperand.equals(EMPTY)) {
            if (getBinaryOperationStage() != null) {
                Stage stage = getBinaryOperationStage();
                activeStage.setResultOperation(activeStageOperand);
                history.addLast(activeStage);

                Stage clone = new Stage();
                clone.setOperand(stage.getOperand());
                clone.setBinaryOperator(stage.getBinaryOperator());
                activeStage = clone;
                calculateBinary();

                Stage result = new Stage();
                Stage last = history.getLast();
                result.setOperand(last.getResultOperation());
                result.setResultOperation(last.getResultOperation());
                history.addLast(result);

                displayField = result.getResultOperation();
                activeStage = null;
                return;
            } else { //if history is empty or binary operation stage not found then copy and add active Stage
                activeStage.setResultOperation(activeStageOperand);
                history.addLast(activeStage);

                //copy and add result stage to history
                Stage stage = new Stage();
                stage.setOperand(activeStageOperand);
                stage.setResultOperation(activeStageOperand);
                history.addLast(stage);

                displayField = stage.getResultOperation();
                activeStage = null;
                return;
            }
        }

        // operator - empty ; operand - empty
        if (binaryOperator.equals(EMPTY) && activeStageOperand.equals(EMPTY)) {
            if (history.isEmpty()) {
                activeStage.setOperand(ZERO);
                activeStage.setResultOperation(ZERO);

                Stage firstStage = new Stage();
                firstStage.setOperand(ZERO);
                firstStage.setResultOperation(ZERO);
                history.addFirst(firstStage);

                displayField = ZERO;

                history.addLast(activeStage);
                activeStage = null;
                return;
            }

            if (getBinaryOperationStage() != null) {
                Stage stage = getBinaryOperationStage();
                activeStage.setOperand(stage.getOperand());
                //activeStage.setResultOperation(stage.getResultOperation());
                activeStage.setBinaryOperator(stage.getBinaryOperator());

                calculateBinary();

                Stage lastStage = history.getLast();
                Stage result = new Stage();
                result.setOperand(lastStage.getResultOperation());
                result.setResultOperation(lastStage.getResultOperation());
                history.addLast(result);

                displayField = result.getResultOperation();
                activeStage = null;
            } else {
                Stage last = history.getLast();
                activeStage.setOperand(last.getResultOperation());
                activeStage.setResultOperation(last.getResultOperation());
                history.addLast(activeStage);

                displayField = activeStage.getResultOperation();
                activeStage = null;
            }
        }
    }

    private Stage getBinaryOperationStage() {
        Iterator<Stage> iterator = history.descendingIterator();
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
