package org.pepsik.model;

import com.sun.org.apache.xpath.internal.operations.Minus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pepsik on 7/27/2016.
 */
public class Stage {

    private static final String EMPTY = " ";
    private static final String ZERO = "0";
    public static final String POINT = ".";
    public static final String OCTOTHORPE = "±";
    public static final String MINUS = "-";

    private String binaryOperator = EMPTY;
    private List<String> unaryOperators = new ArrayList<String>();
    private String operand = EMPTY;
    private String resultOperation = EMPTY; //todo: migrate to model?

    //todo create constructors with operand or operator? to avoid invalid state with empty operator and operand


    public Stage() {
    }

    public Stage(Stage stage) {
        operand = stage.getOperand();
        binaryOperator = stage.getBinaryOperator();
    }

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

    public void addDigitToOperand(String input) {
        if (input.equals(POINT)) {
            if (operand.contains(EMPTY)) {
                operand = ZERO + POINT;
                return;
            }

            if (!operand.contains(POINT)) {
                operand += input;
                return;
            }
        }

        if (operand.equals(ZERO) || operand.equals(EMPTY)) {
            operand = input;
        } else {
            operand += input;
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
        checkValues();
    }

    private void checkValues() {
        operand = operand.replaceAll("(\\.|(\\.(\\d*[1-9])?))0+\\b", "$2");
        resultOperation = resultOperation.replaceAll("(\\.|(\\.(\\d*[1-9])?))0+\\b", "$2");
    }

    @Override
    public String toString() {
        return binaryOperator + operand;
    }
}
