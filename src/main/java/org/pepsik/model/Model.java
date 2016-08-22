package org.pepsik.model;

import org.pepsik.controller.exception.DivideByZeroException;
import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;

import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ZERO;
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
     * Operand after calculation unary operation
     */
    private BigDecimal calculatedOperand;

    /**
     * Calculator memory
     */
    private BigDecimal memory;

    /**
     * Result of last complete stage
     */
    private BigDecimal result = ZERO;

    /**
     * Calculation scale
     */
    public static final int SCALE = 1_000;

    /**
     * Divisor for percent calculation
     */
    private static final BigDecimal DIVISOR = new BigDecimal(100);

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
            currentStage.clearUnaryOperators();
        }

        currentStage.setOperand(number);
    }

    /**
     * Adds binary operator to expression and show show expression on display field
     *
     * @param inputOperator input binary operator
     */
    public void addBinaryOperator(BinaryOperation inputOperator) throws DivideByZeroException {
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
    public void addUnaryOperator(UnaryOperation operator) throws DivideByZeroException {
        currentStage.addUnaryOperator(operator);
        BigDecimal operand = currentStage.getOperand();

        // operator - empty ; operand - empty
        // operator - exist; operand - empty
        if (operand == null) {
            currentStage.setOperand(result);
        }

        // operator - empty ; operand - exist
        //operator - exist;  operand - exist
        calculatedOperand = calculateUnary();
    }

    /**
     * Add to calculator memory
     */
    public void addToMemory() {
        if (memory == null) {
            memory = ZERO;
        }

        BigDecimal toMemory = result;
        BigDecimal operand = currentStage.getOperand();
        if (operand != null) {
            toMemory = operand;
        }

        memory = memory.add(toMemory);
    }

    /**
     * Subtract from calculator memory
     */
    public void subtractFromMemory() {
        if (memory == null) {
            memory = ZERO;
        }

        BigDecimal toMemory = result;
        BigDecimal operand = currentStage.getOperand();
        if (operand != null) {
            toMemory = operand;
        }

        memory = memory.subtract(toMemory);
    }

    /**
     * Save to calculator memory
     */
    public void saveMemory() {
        BigDecimal toMemory = result;
        BigDecimal operand = currentStage.getOperand();
        if (operand != null) {
            toMemory = operand;
        }
        memory = toMemory;
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
        return calculatedOperand;
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

            if (operand != null && operator != null) {
                return stage;
            }
        }

        if (history.isEmpty()) {
            Stage stage = new Stage();
            stage.setOperand(ZERO);
            return stage;
        } else {
            //get last stage in previous expression
            return history.get(history.size() - 1).getLast();
        }
    }

    /**
     * Calculates result of active stage and adds to expression history
     */
    private void calculateBinary() throws DivideByZeroException {
        BigDecimal operand = calculateUnary();
        BinaryOperation binaryOperator = currentStage.getBinaryOperator();

        try {
            result = binaryOperator.execute(result, operand);
        } catch (ArithmeticException ex) {
            if (ex.getMessage().equals("BigInteger divide by zero")) {
                currentExpression = new ArrayDeque<>();
                currentStage = new Stage();
                throw new DivideByZeroException(ex.getMessage());
            } else {
                throw ex;
            }

        }

        lastBinaryStage = currentStage;
    }

    /**
     * Calculates unary operation in active stage
     */
    private BigDecimal calculateUnary() throws DivideByZeroException {
        BigDecimal temp = currentStage.getOperand();

        try {
            for (UnaryOperation unary : currentStage.getUnaryOperators()) {
                if (unary.equals(PERCENT)) {
                    temp = result.multiply(temp.divide(DIVISOR, Model.SCALE * 2, BigDecimal.ROUND_HALF_UP));
                } else {
                    temp = unary.execute(temp);
                }
            }
        } catch (ArithmeticException ex) {
            if (ex.getMessage().equals("BigInteger divide by zero")) {
                throw new DivideByZeroException(ex.getMessage());
            } else {
                throw ex;
            }

        }

        return temp;
    }

    /**
     * Calculates EQUAL operation and adds to expression history.
     */
    private void calculateEqual() throws DivideByZeroException {
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
            currentStage.setOperand(getLastStage().getOperand());
            currentExpression.addFirst(currentStage);

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
