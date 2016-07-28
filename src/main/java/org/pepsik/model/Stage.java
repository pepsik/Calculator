package org.pepsik.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pepsik on 7/27/2016.
 */
public class Stage {
    private String binaryOperator = "";
    private List<String> unaryOperators = new ArrayList<String>();
    private String rightOperand = "";
    private String resultOperation;
    private boolean isComplete = false;

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

    public String getRightOperand() {
        return rightOperand;
    }

    public void addToRightOperand(String number) {
        StringBuilder sb = new StringBuilder(rightOperand);
        rightOperand = sb.append(number).toString();
    }

    public void setRightOperand(String rightOperand) {
        this.rightOperand = rightOperand;
    }

    public String getResultOperation() {
        return resultOperation;
    }

    public void setResultOperation(String resultOperation) {
        this.resultOperation = resultOperation;
    }
}
