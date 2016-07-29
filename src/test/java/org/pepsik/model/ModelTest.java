package org.pepsik.model;

import org.junit.Test;

import static java.lang.Integer.MAX_VALUE;
import static org.junit.Assert.*;

/**
 * Created by pepsik on 7/27/2016.
 */
public class ModelTest {

    private static final double DELTA = 0.0001;

    private Model model;

    @Test
    public void validValues() {

        // -----------Integer INPUT only---------
        //test set operand with new obj
        setInput(0, 0);
        setInput(1, 1);
        setInput(2, 2);
        setInput(9, 9);
        setInput(10, 1, 0);
        setInput(20, 2, 0);
        setInput(90, 9, 0);
        setInput(123456, 1, 2, 3, 4, 5, 6);

        setInputOnCommonObj(0, 0);
        setInputOnCommonObj(1, 1);
        setInputOnCommonObj(2, 2);
        setInputOnCommonObj(9, 9);
        setInputOnCommonObj(10, 1, 0);
        setInputOnCommonObj(20, 2, 0);
        setInputOnCommonObj(90, 9, 0);
        setInputOnCommonObj(123456, 1, 2, 3, 4, 5, 6);
        setInputOnCommonObj(0, 0);
        setInputOnCommonObj(1, 1);
        setInputOnCommonObj(0, 0);

        //test sum 2 numbers
        operateOnNewObj(0, 0, "+", 0);
        operateOnNewObj(1, 1, "+", 0);
        operateOnNewObj(99, 99, "+", 0);
        operateOnNewObj(1, 0, "+", 1);
        operateOnNewObj(99, 0, "+", 99);
        operateOnNewObj(2, 1, "+", 1);
        operateOnNewObj(4, 1, "+", 3);
        operateOnNewObj(10, 7, "+", 3);
        operateOnNewObj(150, 100, "+", 50);
        operateOnNewObj(150, 50, "+", 100);

        operateOnNewObj(MAX_VALUE - 1, MAX_VALUE / 2, "+", MAX_VALUE / 2);
        operateOnNewObj(MAX_VALUE, MAX_VALUE, "+", 0);
        operateOnNewObj(MAX_VALUE, 0, "+", MAX_VALUE);

        model = new Model();
        operate(0, 0, "+", 0);
        operate(1, 1, "+", 0);
        operate(99, 99, "+", 0);
        operate(1, 0, "+", 1);
        operate(99, 0, "+", 99);
        operate(2, 1, "+", 1);
        operate(4, 1, "+", 3);
        operate(10, 7, "+", 3);
        operate(150, 100, "+", 50);
        operate(150, 50, "+", 100);

        operate(MAX_VALUE - 1, MAX_VALUE / 2, "+", MAX_VALUE / 2);
        operate(MAX_VALUE, MAX_VALUE, "+", 0);
        operate(MAX_VALUE, 0, "+", MAX_VALUE);

        //test subtract 2 numbers
        operateOnNewObj(0, 0, "-", 0);
        operateOnNewObj(1, 1, "-", 0);
        operateOnNewObj(99, 99, "-", 0);
        operateOnNewObj(-1, 0, "-", 1);
        operateOnNewObj(-99, 0, "-", 99);
        operateOnNewObj(0, 1, "-", 1);
        operateOnNewObj(-2, 1, "-", 3);
        operateOnNewObj(4, 7, "-", 3);
        operateOnNewObj(50, 100, "-", 50);
        operateOnNewObj(-50, 50, "-", 100);

        operateOnNewObj(0, MAX_VALUE / 2, "-", MAX_VALUE / 2);
        operateOnNewObj(MAX_VALUE, MAX_VALUE, "-", 0);
        operateOnNewObj(-MAX_VALUE, 0, "-", MAX_VALUE);

        model = new Model();
        operate(0, 0, "-", 0);
        operate(1, 1, "-", 0);
        operate(99, 99, "-", 0);
        operate(-1, 0, "-", 1);
        operate(-99, 0, "-", 99);
        operate(0, 1, "-", 1);
        operate(-2, 1, "-", 3);
        operate(4, 7, "-", 3);
        operate(50, 100, "-", 50);
        operate(-50, 50, "-", 100);

        operate(0, MAX_VALUE / 2, "-", MAX_VALUE / 2);
        operate(MAX_VALUE, MAX_VALUE, "-", 0);
        operate(-MAX_VALUE, 0, "-", MAX_VALUE);

        //test multiply 2 numbers
        operateOnNewObj(0, 0, "*", 0);
        operateOnNewObj(0, 1, "*", 0);
        operateOnNewObj(0, 99, "*", 0);
        operateOnNewObj(0, 0, "*", 1);
        operateOnNewObj(0, 0, "*", 99);
        operateOnNewObj(1, 1, "*", 1);
        operateOnNewObj(3, 1, "*", 3);
        operateOnNewObj(21, 7, "*", 3);
        operateOnNewObj(5000, 100, "*", 50);
        operateOnNewObj(5000, 50, "*", 100);

        operateOnNewObj(0, MAX_VALUE / 2, "-", MAX_VALUE / 2);
        operateOnNewObj(MAX_VALUE, MAX_VALUE, "-", 0);
        operateOnNewObj(-MAX_VALUE, 0, "-", MAX_VALUE);

        model = new Model();
        operate(0, 0, "*", 0);
        operate(0, 1, "*", 0);
        operate(0, 99, "*", 0);
        operate(0, 0, "*", 1);
        operate(0, 0, "*", 99);
        operate(1, 1, "*", 1);
        operate(3, 1, "*", 3);
        operate(21, 7, "*", 3);
        operate(5000, 100, "*", 50);
        operate(5000, 50, "*", 100);

        operate(0, MAX_VALUE / 2, "-", MAX_VALUE / 2);
        operate(MAX_VALUE, MAX_VALUE, "-", 0);
        operate(-MAX_VALUE, 0, "-", MAX_VALUE);

        //test divide 2 numbers
        operateOnNewObj(Double.NaN, 0, "/", 0);
        operateOnNewObj(Double.POSITIVE_INFINITY, 1, "/", 0);
        operateOnNewObj(Double.POSITIVE_INFINITY, 99, "/", 0);
        operateOnNewObj(0, 0, "/", 1);
        operateOnNewObj(0, 0, "/", 99);
        operateOnNewObj(1, 1, "/", 1);
        operateOnNewObj(0.3333, 1, "/", 3);
        operateOnNewObj(2.3333, 7, "/", 3);
        operateOnNewObj(2, 100, "/", 50);
        operateOnNewObj(0.5, 50, "/", 100);

        operateOnNewObj(1, MAX_VALUE / 2, "/", MAX_VALUE / 2);
        operateOnNewObj(Double.POSITIVE_INFINITY, MAX_VALUE, "/", 0);
        operateOnNewObj(0, 0, "/", MAX_VALUE);

        model = new Model();
        operate(Double.NaN, 0, "/", 0);
        operate(Double.POSITIVE_INFINITY, 1, "/", 0);
        operate(Double.POSITIVE_INFINITY, 99, "/", 0);
        operate(0, 0, "/", 1);
        operate(0, 0, "/", 99);
        operate(1, 1, "/", 1);
        operate(0.3333, 1, "/", 3);
        operate(2.3333, 7, "/", 3);
        operate(2, 100, "/", 50);
        operate(0.5, 50, "/", 100);

        operate(1, MAX_VALUE / 2, "/", MAX_VALUE / 2);
        operate(Double.POSITIVE_INFINITY, MAX_VALUE, "/", 0);
        operate(0, 0, "/", MAX_VALUE);
        // ----------- END Integer INPUT ---------
//
//        operateOnNewObj(0, "+", "=");
//        operateOnNewObj(0, "-", "=");
//        operateOnNewObj(0, "*", "=");
//        operateOnNewObj(Double.NaN, "/", "=");

//        model = new Model();
//        operate(0, "+", "=");
//        operate(0, "-", "=");
//        operate(0, "*", "=");
//        operate(0, "+", "=");
//        operate(0, "-", "=");
//        operate(0, "*", "=");
//        operate(Double.NaN, "/", "=");
//        operate(Double.NaN, "*", "=");
//        operate(Double.NaN, "+", "=");
//        operate(Double.NaN, "-", "=");

        operateOnNewObj(0, 0, "=");
        operateOnNewObj(1, 1, "=");
        operateOnNewObj(5, 5, "=");
        operateOnNewObj(999, 999, "=");
        operateOnNewObj(MAX_VALUE, MAX_VALUE, "=");

        operateOnNewObj(-1, -1, "=");
        operateOnNewObj(-5, -5, "=");
        operateOnNewObj(-50, -50, "=");
        operateOnNewObj(-MAX_VALUE, -MAX_VALUE, "=");

        operateOnNewObj(0, 0, "=");
        operateOnNewObj(0, 0, "=", "=", "=");
        operateOnNewObj(0, 0, "=", "=", "=", "=", "=", "=", "=", "=");
        operateOnNewObj(1, 1, "=");
        operateOnNewObj(1, 1, "=", "=", "=", "=", "=", "=", "=", "=", "=", "=");
        operateOnNewObj(5, 5, "=");
        operateOnNewObj(5, 5, "=", "=", "=", "=", "=");
        operateOnNewObj(999, 999, "=");
        operateOnNewObj(999, 999, "=", "=", "=", "=");
        operateOnNewObj(MAX_VALUE, MAX_VALUE, "=");
        operateOnNewObj(MAX_VALUE, MAX_VALUE, "=", "=", "=", "=", "=");

        model = new Model();
        operate(0, 0, "=");
        operate(0, 0, "=", "=", "=");
        operate(0, 0, "=", "=", "=", "=", "=", "=", "=", "=");
        operate(1, 1, "=");
        operate(1, 1, "=", "=", "=", "=", "=", "=", "=", "=", "=", "=");
        operate(5, 5, "=");
        operate(5, 5, "=", "=", "=", "=", "=");
        operate(999, 999, "=");
        operate(999, 999, "=", "=", "=", "=");
        operate(MAX_VALUE, MAX_VALUE, "=");
        operate(MAX_VALUE, MAX_VALUE, "=", "=", "=", "=", "=");

        operateOnNewObj(1, 1, "=", "+");
        operateOnNewObj(2, 1, "=", "+", "=");
        operateOnNewObj(4, 1, "=", "+", "=", "+", "=");
        operateOnNewObj(8, 1, "=", "+", "=", "+", "=", "+", "=");
        operateOnNewObj(16, 1, "=", "+", "=", "+", "=", "+", "=", "+", "=");

        operateOnNewObj(-1, -1, "=", "+");
        operateOnNewObj(-2, -1, "=", "+", "=");
        operateOnNewObj(-4, -1, "=", "+", "=", "+", "=");
        operateOnNewObj(-8, -1, "=", "+", "=", "+", "=", "+", "=");
        operateOnNewObj(-16, -1, "=", "+", "=", "+", "=", "+", "=", "+", "=");

        operateOnNewObj(1, 1, "=", "-");
        operateOnNewObj(0, 1, "=", "-", "=");
        operateOnNewObj(0, 1, "=", "-", "=", "-", "=");
        operateOnNewObj(0, 1, "=", "-", "=", "-", "=", "-", "=", "-", "=");

        operateOnNewObj(-1, -1, "=", "-");
        operateOnNewObj(0, -1, "=", "-", "=");
        operateOnNewObj(0, 1, "=", "-", "=", "-", "=");
        operateOnNewObj(0, 1, "=", "-", "=", "-", "=", "-", "=", "-", "=");

        operateOnNewObj(10, 10, "=", "-");
        operateOnNewObj(0, 10, "=", "-", "=");
        operateOnNewObj(0, 10, "=", "-", "=", "-", "=");
        operateOnNewObj(0, 10, "=", "-", "=", "-", "=", "-", "=", "-", "=");

        operateOnNewObj(15, 5, "+", "=", "=");
        operateOnNewObj(25, 5, "+", "=", "=", "=", "=");
        operateOnNewObj(45, 5, "+", "=", "=", "=", "=", "=", "=", "=", "=");
        operateOnNewObj(70, 5, "+", "=", "=", "=", "=", "=", "=", "+", "=");

        operateOnNewObj(15, 5, "+", "=", "=");
        operateOnNewObj(25, 5, "+", "=", "=", "=", "=");
        operateOnNewObj(45, 5, "+", "=", "=", "=", "=", "=", "=", "=", "=");
        operateOnNewObj(0, 5, "+", "=", "=", "=", "=", "=", "=", "-", "=");

        model = new Model();
        operate(1, 1, "=", "+");
        operate(4, 1, "=", "+", "=");
        operate(12, 1, "=", "+", "=", "+", "=");
        operate(56, 1, "=", "+", "=", "+", "=", "+", "=");
        operate(29, 1, "=", "+");

        operate(28, -1, "=", "+");
        operate(54, -1, "=", "+", "=");

        model = new Model();
        operate(-1, -1, "=", "+");
        operate(-4, -1, "=", "+", "=");
        operate(-12, -1, "=", "+", "=", "+", "=");
        operate(-56, -1, "=", "+", "=", "+", "=", "+", "=");
        operate(-464, -1, "=", "+", "=", "+", "=", "+", "=", "+", "=");

        model = new Model();
        operate(1, 1, "=", "-");
        operate(0, 1, "=", "-", "=");
        operate(0, 1, "=", "-", "=", "-", "=");
        operate(0, 1, "=", "-", "=", "-", "=", "-", "=", "-", "=");

        operate(-1, -1, "=", "-");
        operate(0, -1, "=", "-", "=");
        operate(0, 1, "=", "-", "=", "-", "=");
        operate(0, 1, "=", "-", "=", "-", "=", "-", "=", "-", "=");

        operate(10, 10, "=", "-");
        operate(0, 10, "=", "-", "=");
        operate(0, 10, "=", "-", "=", "-", "=");
        operate(0, 10, "=", "-", "=", "-", "=", "-", "=", "-", "=");

        operate(15, 5, "+", "=", "=");
        operate(25, 5, "+", "=", "=", "=", "=");
        operate(45, 5, "+", "=", "=", "=", "=", "=", "=", "=", "=");
        operate(70, 5, "+", "=", "=", "=", "=", "=", "=", "+", "=");

        operate(15, 5, "+", "=", "=");
        operate(25, 5, "+", "=", "=", "=", "=");
        operate(45, 5, "+", "=", "=", "=", "=", "=", "=", "=", "=");
        operate(0, 5, "+", "=", "=", "=", "=", "=", "=", "-", "=");

        operate(10, 5, "+", "+", "=");
        operate(68, 34, "+", "+", "+", "+", "+", "+", "=");
        operate(-16, -8, "+", "+", "+", "+", "+", "+", "+", "+", "=");

        //---
        model = new Model();
        operate(8, "3+5=");
        operate(8, "3+5=");
        operate(100, "31+5/2/9+1*11+67=");
        operate(167, "=");
        operate(301, "= =");
        operate(222, "= = - 11 /2 + 5 = =");
        operate(0, "+ 1 = + = + = - =");
        operate(-4, "- 1 = + = + =");
        operate(-1.3333, "/3+");
        operate(-4, "*3=");

        model = new Model();
        operate(11, "*3++++++++++11=");
        operate(44, "*3++++++++++11=");

        model = new Model();
        operate(9.5, "+3/2+1*3+2=2");
    }

    private void setInput(int expected, int... values) {
        model = new Model();

        for (int value : values) {
            model.addInputDigit(String.valueOf(value));
        }

        assertEquals(String.valueOf(expected), model.getDisplay());
    }

    private void operate(double expected, String str) {
        String data = str.replaceAll("\\s+", "");
        String number = "";
        String operator = "";

        for (int i = 0; i < data.length(); i++) {
            char symbol = data.charAt(i);

            if (Character.isDigit(symbol)) {
                number += symbol;
            } else {
                operator = String.valueOf(symbol);

                if (!number.equals("")) {
                    model.addInputDigit(number);
                }

                model.addOperator(operator);
                number = "";
            }
        }

        assertEquals(expected, Double.valueOf(model.getDisplay()), DELTA);
    }

    private void setInputOnCommonObj(double expected, int... values) {
        model.clearEntry();

        for (int value : values) {
            model.addInputDigit(String.valueOf(value));
        }

        if (expected % 1 == 0) {
            assertEquals(String.valueOf((int) expected), model.getDisplay());
        } else {
            assertEquals(String.valueOf(expected), model.getDisplay());
        }
    }

    private void operateOnNewObj(double expected, double value1, String operator, double value2) {
        model = new Model();
        operate(expected, value1, operator, value2);
    }

    private void operate(double expected, double value1, String operator, double value2) {
        model.addInputDigit(String.valueOf(value1));
        model.addOperator(operator);
        model.addInputDigit(String.valueOf(value2));
        model.addOperator("=");

        assertEquals(expected, Double.parseDouble(model.getDisplay()), DELTA);
    }

//    private void operateOnNewObj(double expected, String... operators) {
//        model = new Model();
//        operate(expected, operators);
//    }

//    private void operate(double expected, String... operators) {
//        for (String operator : operators) {
//            model.addOperator(operator);
//        }
//
//        assertEquals(expected, Double.parseDouble(model.getDisplay()), DELTA);
//    }

    private void operateOnNewObj(double expected, int input, String... operators) {
        model = new Model();
        operate(expected, input, operators);
    }

    private void operate(double expected, int input, String... operators) {
        model.addInputDigit(String.valueOf(input));
        for (String operator : operators) {
            model.addOperator(operator);
        }

        assertEquals(expected, Double.parseDouble(model.getDisplay()), DELTA);
    }
}