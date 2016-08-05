package org.pepsik.model;

import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;
import org.pepsik.util.TextFormatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static org.pepsik.model.operation.BinaryOperation.*;

/**
 * This class represents a calculator logic. It consist and operate with Stage class. Model creates chain of stages where
 * result of first stage used in second stage as left operand.
 */
public class Model {
    private static final String ZERO = "0";

    private Deque<Stage> currentExpression = new ArrayDeque<>();
    private List<Deque<Stage>> history = new ArrayList<>();
    private BigDecimal memory = new BigDecimal(ZERO);

    private Stage currentStage = new Stage();
    private Stage lastBinaryStage;

    /**
     * Adds input digit or point to active stage and show expression on display field
     *
     * @param number input digit or point
     */
    public void addNumber(BigDecimal number) {
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

        final BigDecimal operand = currentStage.getOperand();
        final BinaryOperation binaryOperator = currentStage.getBinaryOperator();

        //operator - exist;  operand - empty
        if (binaryOperator != null && operand == null) {
            currentStage.setBinaryOperator(inputOperator);
            return;
        }

        //operator - exist;  operand - exist
        if (binaryOperator != null && operand != null) {
            calculateBinary();
        }

        //operator - empty;  operand - exist
        if (binaryOperator == null && operand != null) {
            currentStage.setResultOperation(calculateUnary());
            currentExpression.addFirst(currentStage);
            currentStage = new Stage();
        }

        //operator - empty;  operand - empty
        if (binaryOperator == null && operand == null) {
            currentExpression.addFirst(getLastCompleteStage());
        }

        currentStage.setBinaryOperator(inputOperator);
        currentExpression.addLast(currentStage);
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

    /**
     * Calculates result of active stage and adds to expression history
     */
    private void calculateBinary() {
        final BigDecimal rightOperand = calculateUnary();
        final BinaryOperation binaryOperator = currentStage.getBinaryOperator();

        //gets last stage to get left operand (result of last completed stage)
        BigDecimal leftOperand = getLastCompleteStage().getResultOperation();
        BigDecimal result = binaryOperator.execute(leftOperand, rightOperand);

        currentStage.setResultOperation(result);
        lastBinaryStage = currentStage;

        //after calculation erase current stage
        currentStage = new Stage();
    }

    /**
     * Calculates unary operation in active stage
     */
    private BigDecimal calculateUnary() {
        final BigDecimal operand = currentStage.getOperand();
        BigDecimal temp = operand;

        // operator - empty ; operand - empty
        // operator - exist; operand - empty
        if (operand == null) {
            BigDecimal result = getLastCompleteStage().getResultOperation();
            currentStage.setOperand(result);
            temp = result;
        }

        // operator - empty ; operand - exist
        //operator - exist;  operand - exist
        for (UnaryOperation unary : currentStage.getUnaryOperators()) {
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
            currentStage.setOperand(getLastCompleteStage().getResultOperation());
            calculateBinary();
        }

        // operator - exist;  operand - exist
        if (binaryOperator != null && operand != null) {
            calculateBinary();
        }

        // operator - empty ; operand - exist
        if (binaryOperator == null && operand != null) {
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
        if (binaryOperator == null && operand == null) {
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

    public BigDecimal getOperand() {
        return calculateUnary();
    }

    public BigDecimal getResult() {
        return getLastCompleteStage().getResultOperation();
    }

    /**
     * Finds and returns last complete stage in current expression or history. Otherwise returns stage with ZERO operand and result.
     *
     * @return last complete stage
     */
    private Stage getLastCompleteStage() {
        Iterator<Stage> iterator = currentExpression.descendingIterator();
        while (iterator.hasNext()) {
            Stage stage = iterator.next();
            if (stage.getResultOperation() != null) {
                return stage;
            }
        }

        if (history.isEmpty()) {
            Stage stage = new Stage();
            stage.setOperand(new BigDecimal(ZERO));
            stage.setResultOperation(new BigDecimal(ZERO));
            return stage;
        } else {
            return history.get(history.size() - 1).getLast();
        }
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
     * Clear stage operand and show on display field
     */
    public void clearEntry() {
        currentStage.setOperand(new BigDecimal(ZERO));
    }

    public void backspace() {
        BigDecimal operand = currentStage.getOperand();

        if (operand != null) {
            currentStage.setOperand(operand.divide(new BigDecimal(10), RoundingMode.DOWN));
        } else {
            currentStage.setOperand(getLastCompleteStage().getResultOperation().divide(new BigDecimal(10), RoundingMode.DOWN));
        }
    }

    public void addToMemory() {
        if (memory == null) {
            memory = new BigDecimal("0");
        }

        if (currentStage.getOperand() != null) {
            memory = memory.add(currentStage.getOperand());
        } else {
            memory = memory.add(getLastCompleteStage().getResultOperation());
        }
    }

    public void substructFromMemory() {
        if (currentStage.getOperand() == null) {
            memory = new BigDecimal(ZERO);
        }

        if (currentStage.getOperand() != null) {
            memory = memory.add(currentStage.getOperand());
        } else {
            memory = memory.add(getLastCompleteStage().getResultOperation());
        }
    }

    public void saveMemory() {
        if (currentStage.getOperand() != null) {
            memory = currentStage.getOperand();
        } else {
            memory = getLastCompleteStage().getResultOperation();
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
}
