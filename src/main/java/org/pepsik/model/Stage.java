package org.pepsik.model;

import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represent a stage of binary operation. Stage consist of binary operator, operand, unary operations (witch operate with operand) and result of binary operation.
 */
public class Stage {
    /**
     * binary operation
     */
    private BinaryOperation binaryOperator;
    /**
     * Unary operations
     */
    private List<UnaryOperation> unaryOperators = new ArrayList<>();
    /**
     * Operand as right operand in binary operation
     */
    private BigDecimal operand;

    public Stage() {
    }

    /**
     * Clone stage
     *
     * @param stage clone stage
     */
    public Stage(Stage stage) {
        operand = stage.getOperand();
        binaryOperator = stage.getBinaryOperator();
    }

    public void setBinaryOperator(BinaryOperation binaryOperator) {
        this.binaryOperator = binaryOperator;
    }

    public BinaryOperation getBinaryOperator() {
        return binaryOperator;
    }

    public void addUnaryOperator(UnaryOperation unary) {
        unaryOperators.add(unary);
    }

    public List<UnaryOperation> getUnaryOperators() {
        return unaryOperators;
    }

    public BigDecimal getOperand() {
        return operand;
    }

    public void setOperand(BigDecimal operand) {
        this.operand = operand;
    }

    public void clearUnaryOperators() {
        unaryOperators.clear();
    }
}
