package org.pepsik.model;

import com.sun.org.apache.xpath.internal.operations.Minus;
import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pepsik on 7/27/2016.
 */
public class Stage {
    private BinaryOperation binaryOperator;
    private List<UnaryOperation> unaryOperators = new ArrayList<>();
    private BigDecimal operand;
    private BigDecimal resultOperation;

    public Stage() {
    }

    //clone constr
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

    public BigDecimal getResultOperation() {
        return resultOperation;
    }

    public void setResultOperation(BigDecimal resultOperation) {
        this.resultOperation = resultOperation;
    }

    public void clearUnaryOperators() {
        unaryOperators.clear();
    }

    @Override
    public String toString() {
        return binaryOperator.getOperator() + operand;
    }
}
