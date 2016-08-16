package org.pepsik.model;

import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static org.pepsik.model.operation.BinaryOperation.*;
import static org.pepsik.model.operation.UnaryOperation.PERCENT;

/**
 * This class represents a calculator logic. It consist and operate with Stage class. Model creates chain of stages where
 * result of first stage used in second stage and last stage represent result of current expression.
 */
public class Model {

    /**
     * Expression represent sequence of stages
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
    private BigDecimal result = new BigDecimal(BigInteger.ZERO);

    /**
     * Calculator memory
     */
    private BigDecimal memory;

    public Model() {
        currentExpression.addLast(currentStage);
    }

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

            currentStage = new Stage();
            currentExpression.addLast(currentStage);
        }

        //found first stage
        //operator - empty;  operand - exist
        if (binaryOperator == null && operand != null) {
            result = calculateUnary();

            currentStage = new Stage();
            currentExpression.addLast(currentStage);
        }

        //operator - empty;  operand - empty
        if (binaryOperator == null && operand == null) {
            if (currentExpression.size() == 1) {
                currentStage.setOperand(getLastStage().getOperand());

                currentStage = new Stage();
                currentExpression.addLast(currentStage);
            }
        }

        currentStage.setBinaryOperator(inputOperator);
    }

    /**
     * Adds and calculates unary operator and show expression on display field
     *
     * @param operator unary operator
     */
    public void addUnaryOperator(UnaryOperation operator) {
        currentStage.addUnaryOperator(operator);
        BigDecimal operand = currentStage.getOperand();

        // operator - empty ; operand - empty
        // operator - exist; operand - empty
        if (operand == null) {
            currentStage.setOperand(result);
        }

        // operator - empty ; operand - exist
        //operator - exist;  operand - exist
        calculateUnary();
    }

    /**
     * Add to calculator memory
     */
    public void addToMemory() {
        if (memory == null) {
            memory = new BigDecimal("0");
        }

        BigDecimal r;
        if (currentStage.getOperand() != null) {
            r = currentStage.getOperand();
        } else {
            r = result;
        }
        memory = memory.add(r);
    }

    /**
     * Subtract from calculator memory
     */
    public void subtractFromMemory() {
        if (memory == null) {
            memory = new BigDecimal(BigInteger.ZERO);
        }

        BigDecimal r;
        if (currentStage.getOperand() != null) {
            r = currentStage.getOperand();
        } else {
            r = result;
        }
        memory = memory.subtract(r);
    }

    /**
     * Save to calculator memory
     */
    public void saveMemory() {
        if (currentStage.getOperand() != null) {
            memory = currentStage.getOperand();
        } else {
            memory = result;
        }
    }

    /**
     * Get from calculator memory
     *
     * @return stored SCALE
     */
    public BigDecimal getMemory() {
        if (memory != null) {
            currentStage.setOperand(memory);
            currentStage.clearUnaryOperators();
        }
        return memory;
    }

    /**
     * Get current operand of current stage
     *
     * @return current stage operand
     */
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

    /**
     * Get result of current expression
     *
     * @return expression result
     */
    public BigDecimal getResult() {
        return result;
    }

    /**
     * Finds and returns last complete stage in current expression or history. Otherwise returns stage with ZERO operand and result.
     *
     * @return last complete stage
     */
    private Stage getLastStage() {
        Iterator<Stage> descIterator = currentExpression.descendingIterator();

        while (descIterator.hasNext()) {
            Stage stage = descIterator.next();
            BinaryOperation operator = stage.getBinaryOperator();
            BigDecimal operand = stage.getOperand();

            if (operand != null && operator != null ) {
                return stage;
            }
        }

        if (history.isEmpty()) {
            Stage stage = new Stage();
            stage.setOperand(new BigDecimal(BigInteger.ZERO));
            return stage;
        } else {
            //get last stage in previous expression
            return history.get(history.size() - 1).getLast();
        }
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
            //clear stage and expression if get error and rethrow exception
            currentExpression = new ArrayDeque<>();
            currentStage = new Stage();
            throw e;
        }

        lastBinaryStage = currentStage;
    }

    /**
     * Calculates unary operation in active stage
     */
    private BigDecimal calculateUnary() {
        BigDecimal temp = currentStage.getOperand();

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
            Stage binaryStage = getLastStage();//clone
            Stage first = new Stage();//clone
            first.setOperand(binaryStage.getOperand());
            currentExpression.addFirst(first);

            if (lastBinaryStage != null) {
                currentStage = new Stage(lastBinaryStage); //clone
                currentExpression.addLast(currentStage);
                calculateBinary();
            }
        }

        currentStage = new Stage();
        currentStage.setBinaryOperator(EQUAL);
        currentStage.setOperand(result);
        currentExpression.addLast(currentStage);

        history.add(currentExpression);

        currentStage = new Stage();
        currentExpression = new ArrayDeque<>();
        currentExpression.addLast(currentStage);
    }

    /**
     * Clear stage operand and show on display field
     */
    public void clearEntry() {
        currentStage.setOperand(null);
        currentStage.clearUnaryOperators();
    }

    /**
     * Clear calculator memory
     */
    public void clearMemory() {
        memory = null;
    }

}
