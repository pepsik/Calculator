package org.pepsik.model;

import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static org.pepsik.model.operation.BinaryOperation.*;
import static org.pepsik.model.operation.UnaryOperation.PERCENT;

/**
 * This class represents a calculator logic. It consist and operate with Stage class. Model creates chain of stages where
 * result of first stage used in second stage as left operand.
 */
public class Model {
    private static final String ZERO = "0";

    /**
     * Expression represents by sequence of stages
     */
    private Deque<Stage> currentExpression = new ArrayDeque<>();
    /**
     * History of last expressions
     */
    private List<Deque<Stage>> history = new ArrayList<>();

    /**
     * Current stage operate with
     */
    private Stage currentStage = new Stage();
    /**
     * Last binary stage
     */
    private Stage lastBinaryStage;

    /**
     * Result of last complete stage
     */
    private BigDecimal result = new BigDecimal(ZERO);
    /**
     * Memory which consist one number
     */
    private BigDecimal memory;

    /**
     * Adds input digit or point to active stage and show expression on display field
     *
     * @param number input digit or point
     */
    public void addNumber(BigDecimal number) {
        if (!currentStage.getUnaryOperators().isEmpty()) {
            calculateEqual();
        }

        currentStage.setOperand(number);
    }

    /**
     * Adds binary operator to expression and show show expression on display field
     *
     * @param inputOperator input binary operator
     */
    public void addBinaryOperator(BinaryOperation inputOperator) {
        //EQUAL operator is unique and calculated separately
        if (inputOperator.equals(EQUAL)) {
            calculateEqual();
            return;
        }

        BigDecimal operand = currentStage.getOperand();
        BinaryOperation binaryOperator = currentStage.getBinaryOperator();

        //operator - exist;  operand - exist
        if (binaryOperator != null && operand != null) {
            calculateBinary();
        }

        //operator - empty;  operand - exist
        if (binaryOperator == null && operand != null) {
            result = calculateUnary();
            currentExpression.addFirst(currentStage);
            currentStage = new Stage();
        }

        //operator - empty;  operand - empty
        if (binaryOperator == null && operand == null) {
            Stage first = new Stage();
            first.setOperand(getLastBinaryStage().getOperand());
            currentExpression.addFirst(first);
        }

        //operator - exist;  operand - empty
        if (binaryOperator != null && operand == null) {
            currentStage.setBinaryOperator(inputOperator);
        } else {
            currentStage.setBinaryOperator(inputOperator);
            currentExpression.addLast(currentStage);
        }
    }

    /**
     * Adds and calculates unary operator and show expression on display field
     *
     * @param operator unary operator
     */
    public void addUnaryOperator(UnaryOperation operator) {
        currentStage.addUnaryOperator(operator);
        calculateUnary();
    }

    public BigDecimal getOperand() {
        return calculateUnary();
    }

    /**
     * Current expression history
     *
     * @return calculator history view
     */
    public Deque<Stage> getCurrentExpression() {
        return currentExpression;
    }

    public BigDecimal getResult() {
        return result;
    }

    /**
     * Clear stage operand and show on display field
     */
    public void clearEntry() {
        currentStage.setOperand(null);
        currentStage.clearUnaryOperators();
    }

    public void addToMemory() {
        if (memory == null) {
            memory = new BigDecimal("0");
        }

        if (currentStage.getOperand() != null) {
            memory = memory.add(currentStage.getOperand());
        } else {
            memory = memory.add(result);
        }
    }

    public void subtractFromMemory() {
        if (memory == null) {
            memory = new BigDecimal(ZERO);
        }

        if (currentStage.getOperand() != null) {
            memory = memory.subtract(currentStage.getOperand());
        } else {
            memory = memory.subtract(result);
        }
    }

    public void saveMemory() {
        if (currentStage.getOperand() != null) {
            memory = currentStage.getOperand();
        } else {
            memory = result;
        }
    }

    public BigDecimal getMemory() {
        if (memory != null) {
            currentStage.setOperand(memory);
            currentStage.clearUnaryOperators();
        }
        return memory;
    }

    public void clearMemory() {
        memory = null;
    }

    /**
     * Calculates result of active stage and adds to expression history
     */
    private void calculateBinary() {
        BigDecimal operand = calculateUnary();
        BinaryOperation binaryOperator = currentStage.getBinaryOperator();

        try {
            result = binaryOperator.execute(result, operand);
        } catch (ArithmeticException e) {
            currentExpression = new ArrayDeque<>();
            currentStage = new Stage();
            throw e;
        }

        lastBinaryStage = currentStage;
        currentStage = new Stage();
    }

    /**
     * Calculates unary operation in active stage
     */
    private BigDecimal calculateUnary() {
        BigDecimal operand = currentStage.getOperand();
        BigDecimal temp = operand;

        // operator - empty ; operand - empty
        // operator - exist; operand - empty
        if (operand == null) {
            currentStage.setOperand(result);
            temp = result;
        }

        // operator - empty ; operand - exist
        //operator - exist;  operand - exist
        for (UnaryOperation unary : currentStage.getUnaryOperators()) {
            if (unary.equals(PERCENT)) {
                UnaryOperation.setOperand(result);
            }

            temp = unary.execute(temp);
        }

        return temp;
    }

    /**
     * Calculates EQUAL operation and adds to expression history.
     */
    private void calculateEqual() {
        final BigDecimal operand = currentStage.getOperand();
        final BinaryOperation binaryOperator = currentStage.getBinaryOperator();

        // operator - exist; operand - empty
        if (binaryOperator != null && operand == null) {
            currentStage.setOperand(result);
            calculateBinary();
        }

        // operator - exist;  operand - exist
        if (binaryOperator != null && operand != null) {
            calculateBinary();
        }

        // operator - empty ; operand - exist
        if (binaryOperator == null && operand != null) {
            result = calculateUnary();
            currentExpression.addLast(currentStage);

            if (lastBinaryStage != null) {
                //clone
                currentStage = new Stage(lastBinaryStage);
                currentExpression.addLast(currentStage);
                calculateBinary();
            } else {
                currentStage = new Stage();
            }
        }

        // operator - empty ; operand - empty
        if (binaryOperator == null && operand == null) {
            Stage binaryStage = new Stage(getLastBinaryStage());//clone
            currentExpression.addFirst(binaryStage);

            if (lastBinaryStage != null) {
                currentStage = new Stage(lastBinaryStage); //clone
                currentExpression.addLast(currentStage);
                calculateBinary();
            }
        }

        currentStage.setBinaryOperator(EQUAL);
        currentStage.setOperand(result);

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
    private Stage getLastBinaryStage() {
        Iterator<Stage> descIterator = currentExpression.descendingIterator();

        while (descIterator.hasNext()) {
            Stage stage = descIterator.next();
            BinaryOperation operator = stage.getBinaryOperator();
            BigDecimal operand = stage.getOperand();

            if (operand != null && operator != null) {
                return stage;
            }
        }

        if (history.isEmpty()) {
            Stage stage = new Stage();
            stage.setOperand(new BigDecimal(ZERO));
            return stage;
        } else {
            return history.get(history.size() - 1).getLast();
        }
    }
}
