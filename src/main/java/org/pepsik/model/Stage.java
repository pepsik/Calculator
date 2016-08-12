package org.pepsik.model;

import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represent a stage of binary operation. Stage consist of binary operator, operand, unary operations.
 * <p>
 * <p>1) E.g. expression 5+4=9 will be as sequence of stages - {5},{+,4},{=,9} where first argument in Stage{} is BinaryOperation and second is BigDecimal operand.
 * <p>The first stage {empty,5} don't have an operator then result of this stage result = 5. <p>The second stage do binary ADD where left operand is result = 5 of last stage {5}
 * and right operand of operation is second field of stage 4 ({+, 4}). <p>The result will be stored in Model and used for next stages.
 * When last stage with EQUAL operator found than operand of this stage will be the result of all expression in e.g. {=,9}.
 * <p>
 * <p>2) E.g. expression 5+(-√9)=2 will be stored in model in sequence of stages {5},{+, negate(sqrt(9)},{=,2}.<p> First stage 5 and result = 5.
 * <p>The second stage consist additional unary operators (-, √), they operate with
 * current stage operand (9) before binary operation(+). <p>After unary operations second stage {+, negate(sqrt(9)} will be like {+,-3} and result = 2 will be stored in model.
 * When encounter an EQUAL operator, then expression ends and result stage looks {=, 2}.
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
        unaryOperators = new ArrayList<>();
    }
}
