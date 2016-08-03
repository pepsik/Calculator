package org.pepsik.model;

import org.pepsik.util.HistoryFormatter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by pepsik on 7/27/2016.
 */
public class Model {
    private static final String SQUARE = "x²";
    private static final String SQUARE_ROOT = "√";
    private static final String PERCENT = "%";
    private static final String FRACTION = "1/x";
    private static final String NEGATE = "+-";

    private static final String SUM = "+";
    private static final String SUBTRACT = "-";
    private static final String MULTIPLY = "*";
    private static final String DIVIDE = "/";
    private static final String EQUAL = "=";

    private static final String EMPTY = " ";
    private static final String ZERO = "0";

    private Deque<Stage> currentExpression = new ArrayDeque<>();
    private List<Deque<Stage>> history = new ArrayList<>();
    private Stage currentStage = new Stage();
    private Stage lastBinaryStage;
    private String displayField = ZERO;

    private BigDecimal memory = new BigDecimal(BigInteger.ZERO);


    /**
     * Adds input digit or point to active stage and show expression on display field
     *
     * @param number input digit or point
     */
    public void addInputDigit(String number) {
        currentStage.addDigitToOperand(number);

        displayField = currentStage.getOperand();
    }

    public void addInputPoint(String point) {
        currentStage.addPointToOperand(point);

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
            displayField = getLastCompleteStage().getResultOperation();
            return;
        }

        final String operand = currentStage.getOperand();
        final String binaryOperator = currentStage.getBinaryOperator();

        //operator - exist;  operand - empty
        if (!binaryOperator.equals(EMPTY) && operand.equals(EMPTY)) {
            currentStage.setBinaryOperator(inputOperator);
            displayField = getLastCompleteStage().getResultOperation();
            return;
        }

        //operator - exist;  operand - exist
        if (!binaryOperator.equals(EMPTY) && !operand.equals(EMPTY)) {
            calculateBinary();
        }

        //operator - empty;  operand - exist
        if (binaryOperator.equals(EMPTY) && !operand.equals(EMPTY)) {
            currentStage.setResultOperation(calculateUnary());
            currentExpression.addFirst(currentStage);
            currentStage = new Stage();
        }

        //operator - empty;  operand - empty
        if (binaryOperator.equals(EMPTY) && operand.equals(EMPTY)) {
            currentExpression.addFirst(getLastCompleteStage());
        }

        currentStage.setBinaryOperator(inputOperator);
        currentExpression.addLast(currentStage);

        displayField = getLastCompleteStage().getResultOperation();
    }

    /**
     * Adds and calculates unary operator and show expression on display field
     *
     * @param operator unary operator
     */
    public void addUnaryOperator(String operator) {
        currentStage.addUnaryOperator(operator);

        displayField = calculateUnary();
    }

    /**
     * Calculates result of active stage and adds to expression history
     */
    private void calculateBinary() {
        final String rightOperand = calculateUnary();
        final String binaryOperator = currentStage.getBinaryOperator();

        //gets last stage to get left operand (result of last completed stage)
        String leftOperand = getLastCompleteStage().getResultOperation();
        String result = doBinaryOperation(binaryOperator, leftOperand, rightOperand);

        currentStage.setResultOperation(result);
        lastBinaryStage = currentStage;

        //after calculation erase current stage
        currentStage = new Stage();
    }

    /**
     * Calculates unary operation in active stage
     */
    private String calculateUnary() {
        final String operand = currentStage.getOperand();
        String temp = operand;

        // operator - empty ; operand - empty
        // operator - exist; operand - empty
        if (operand.equals(EMPTY)) {
            String result = getLastCompleteStage().getResultOperation();
            currentStage.setOperand(result);
            temp = result;
        }

        // operator - empty ; operand - exist
        //operator - exist;  operand - exist
        for (String unary : currentStage.getUnaryOperators()) {
            temp = doUnaryOperation(unary, temp);
        }

        return temp;
    }

    /**
     * Calculates EQUAL operation and adds to expression history.
     */
    private void calculateEqual() {
        final String operand = currentStage.getOperand();
        final String binaryOperator = currentStage.getBinaryOperator();

        // operator - exist; operand - empty
        if (!binaryOperator.equals(EMPTY) && operand.equals(EMPTY)) {
            currentStage.setOperand(getLastCompleteStage().getResultOperation());
            calculateBinary();
        }

        // operator - exist;  operand - exist
        if (!binaryOperator.equals(EMPTY) && !operand.equals(EMPTY)) {
            calculateBinary();
        }

        // operator - empty ; operand - exist
        if (binaryOperator.equals(EMPTY) && !operand.equals(EMPTY)) {
            if (lastBinaryStage != null) {
                currentStage.setResultOperation(calculateUnary());
                currentExpression.addLast(currentStage);

                //clone
                currentStage = new Stage(lastBinaryStage);
                currentExpression.addLast(currentStage);
                calculateBinary();
            } else {
                currentStage.setResultOperation(calculateUnary());
                currentExpression.addLast(currentStage);
                currentStage = new Stage();
            }
        }

        // operator - empty ; operand - empty
        if (binaryOperator.equals(EMPTY) && operand.equals(EMPTY)) {
            Stage binaryStage = new Stage(getLastCompleteStage());//clone
            currentExpression.addFirst(binaryStage);

            if (lastBinaryStage != null) {
                currentStage = new Stage(lastBinaryStage); //clone
                currentExpression.addLast(currentStage);
                calculateBinary();
            }
        }

        Stage last = getLastCompleteStage();
        currentStage.setBinaryOperator(EQUAL);
        currentStage.setOperand(last.getResultOperation());
        currentStage.setResultOperation(last.getResultOperation());

        currentExpression.addLast(currentStage);
        history.add(currentExpression);

        currentExpression = new ArrayDeque<>();
        currentStage = new Stage();
    }

    /**
     * Finds and returns last complete stage in current expression or history. Otherwise returns stage with ZERO operand and result.
     *
     * @return last complete stage
     */
    private Stage getLastCompleteStage() {
        //todo simplify or use field variable for last complete stage
        Iterator<Stage> iterator = currentExpression.descendingIterator();
        while (iterator.hasNext()) {
            Stage stage = iterator.next();
            if (!stage.getResultOperation().equals(EMPTY)) {
                return stage;
            }
        }

        if (history.isEmpty()) {
            Stage stage = new Stage();
            stage.setOperand(ZERO);
            stage.setResultOperation(ZERO);
            return stage;
        } else {
            return history.get(history.size() - 1).getLast();
        }
    }

    /**
     * Calculates unary operation
     *
     * @param operator unary operator
     * @param operand  operand deal with
     * @return operation result
     */
    private String doUnaryOperation(String operator, String operand) {
        Double number = Double.parseDouble(operand);

        switch (operator) {
            case SQUARE:
                number *= number;
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
            case NEGATE:
                number = -number;
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
    private String doBinaryOperation(String operator, String leftOperand, String rightOperand) {
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
        return HistoryFormatter.format(currentExpression);
    }

    /**
     * Clear stage operand and show on display field
     */
    public void clearEntry() {
        currentStage.setOperand(ZERO);

        displayField = ZERO;
    }

    public void backspace() {
        String operand = currentStage.getOperand();

        if (operand.length() > 1) {
            currentStage.setOperand(operand.substring(0, operand.length() - 1));
            displayField = currentStage.getOperand();
            return;
        }

        if (operand.length() == 1){
            currentStage.setOperand(ZERO);
            displayField = currentStage.getOperand();
        }
    }


    public void addToMemory() {
        if (memory == null) {
            memory = new BigDecimal("0");
        }

        if (!currentStage.getOperand().equals(EMPTY)) {
            memory = memory.add(new BigDecimal(currentStage.getOperand()));
        } else {
            memory = memory.add(new BigDecimal(getLastCompleteStage().getResultOperation()));
        }
    }

    public void substructFromMemory(BigDecimal value) {
        if (memory == null || currentStage.getOperand().equals(EMPTY)) {
            memory = new BigDecimal("0");
        }

        if (!currentStage.getOperand().equals(EMPTY)) {
            memory = memory.add(new BigDecimal(currentStage.getOperand()));
        } else {
            memory = memory.add(new BigDecimal(getLastCompleteStage().getResultOperation()));
        }
    }

    public void setMemory() {
        if (!currentStage.getOperand().equals(EMPTY)) {
            memory = new BigDecimal(currentStage.getOperand());
        } else {
            memory = new BigDecimal(getLastCompleteStage().getResultOperation());
        }
    }

    public void getMemory() {
        if (memory != null) {
            currentStage.setOperand(memory.toString());
            currentStage.clearUnaryOperators();

            displayField = memory.toString();
        }
    }

    public void clearMemory() {
        memory = null;
    }
}
