package org.pepsik.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pepsik on 7/27/2016.
 */
public class Stage {
    private String binaryOperator = "";
    private List<String> unaryOperators = new ArrayList<String>();
    private String operand = "";
    private String resultOperation = ""; //todo: migrate to model?

    //todo create constructors with operand or operator? to avoid invalid state with empty operator and operand

    public void setBinaryOperator(String binaryOperator) {
        this.binaryOperator = binaryOperator;
    }

    public String getBinaryOperator() {
        return binaryOperator;
    }

    public void addUnaryOperator(String unary) {
        unaryOperators.add(unary);
    }

    public List<String> getUnaryOperators() {
        return unaryOperators;
    }

    public String getOperand() {
        return operand;
    }

    public void addDigitToOperand(String number) {
        if (operand.equals("0")) {
            operand = number;
        } else {
            StringBuilder sb = new StringBuilder(operand);
            operand = sb.append(number).toString();
        }
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    public String getResultOperation() {
        return resultOperation;
    }

    public void setResultOperation(String resultOperation) {
        this.resultOperation = resultOperation;
    }

    @Override
    public String toString() {
        return binaryOperator + operand;
    }
}
