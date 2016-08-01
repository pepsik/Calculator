package org.pepsik.model;

import org.junit.Test;

import java.util.Random;

import static java.lang.Integer.MAX_VALUE;
import static org.junit.Assert.*;

/**
 * Created by pepsik on 7/27/2016.
 */
public class ModelTest {

    private static final double DELTA = 0.0001;
    public static final int ITERATE_COUNT = 100;
    private static final String POINT = ".";

    private Model model;
    private Random r = new Random();

    @Test
    public void validValues() {

        // -----------INPUT TESTS---------
        operateOnNewObj(0, "");
        operateOnNewObj(0, "0");
        operateOnNewObj(1, "1");
        operateOnNewObj(2, "2");
        operateOnNewObj(3, "3");
        operateOnNewObj(4, "4");
        operateOnNewObj(5, "5");
        operateOnNewObj(6, "6");
        operateOnNewObj(7, "7");
        operateOnNewObj(8, "8");
        operateOnNewObj(9, "9");
        operateOnNewObj(10, "10");
        operateOnNewObj(99, "99");
        operateOnNewObj(100, "100");
        operateOnNewObj(111, "111");
        operateOnNewObj(123, "12 3");
        operateOnNewObj(999, "999");
        operateOnNewObj(1000, "1000");
        operateOnNewObj(123456, "1 2 3 4 5 6");
        operateOnNewObj(1234567890, "1 2 3 4 5 6 7 8 9 0");
        operateOnNewObj(1234567890, "1 2 3 4 5 6 7 8 9 0");
        for (int i = 0; i < ITERATE_COUNT; i++) { //todo: Long
            int random = r.nextInt(MAX_VALUE);
            operateOnNewObj(random, String.valueOf(random));
        }

        operateOnNewObj(1, "1.0");
        operateOnNewObj(9.9, "9.9");
        operateOnNewObj(1, "1.00");
        operateOnNewObj(1, "1.0.0");
        operateOnNewObj(1.11, "1.11");
        operateOnNewObj(1.11, "1.1.1");
        operateOnNewObj(11.1, "11.1");
        operateOnNewObj(12.3, "12.3");
        operateOnNewObj(9.99, "9.99");
        operateOnNewObj(10, "10.00");
        operateOnNewObj(123456, "1 2 3 4 5 6.");
        operateOnNewObj(1.234567890, "1 .2 .3 4 5. 6 .7 8. 9. 0");
        operateOnNewObj(0.1234567890, ".1 2 3 4 5 6 7 8 9 0");

        operateOnNewObj(0, "");
        operateOnNewObj(0, "0");
        operateOnNewObj(1, "1");
        operateOnNewObj(2, "2");
        operateOnNewObj(3, "3");
        operateOnNewObj(4, "4");
        operateOnNewObj(5, "5");
        operateOnNewObj(6, "6");
        operateOnNewObj(7, "7");
        operateOnNewObj(8, "8");
        operateOnNewObj(9, "9");
        operateOnNewObj(10, "10");
        operateOnNewObj(99, "99");
        operateOnNewObj(100, "100");
        operateOnNewObj(111, "111");
        operateOnNewObj(123, "12 3");
        operateOnNewObj(999, "999");
        operateOnNewObj(1000, "1000");
        operateOnNewObj(123456, "1 2 3 4 5 6");
        operateOnNewObj(1234567890, "1 2 3 4 5 6 7 8 9 0");
        operateOnNewObj(1234567890, "1 2 3 4 5 6 7 8 9 0");
        for (int i = 0; i < ITERATE_COUNT; i++) {
            int random = r.nextInt(MAX_VALUE);
            operateOnNewObj(random, String.valueOf(random));
        }

        //todo boundary values
        //todo negative numbers

        operateOnNewObj(0, "00");
        operateOnNewObj(0, "0 0 0 0 0 ");
        operateOnNewObj(1, "01");
        operateOnNewObj(2, "002");
        operateOnNewObj(3, "000000000000003");
        operateOnNewObj(4, "004");
        operateOnNewObj(5, "00005");
        operateOnNewObj(6, "06");
        operateOnNewObj(7, "0000000000000000000000000007");
        operateOnNewObj(8, "0008");
        operateOnNewObj(9, "00000000009");
        operateOnNewObj(10, "00000010");
        operateOnNewObj(12, "0000000012");
        operateOnNewObj(90, "0090");
        operateOnNewObj(120, "00000000120");
        operateOnNewObj(100120, "0000000000000000000100120");
        for (int i = 0; i < ITERATE_COUNT; i++) { //todo: Long
            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < ITERATE_COUNT; j++) {
                sb.append("0");
            }

            int random = r.nextInt(MAX_VALUE);
            sb.append(random);
            operateOnNewObj(random, sb.toString());
        }
        // -----------END INPUT TESTS---------


        // -----------SUM TESTS------------
        // -----------SUM 2 VALUES---------
        //each operation on new model obj
        operateOnNewObj(0, "0 + 0 =");
        operateOnNewObj(1, "1 + 0 =");
        operateOnNewObj(1, "0 + 1 =");
        operateOnNewObj(2, "2 + 0 =");
        operateOnNewObj(3, "3 + 0 =");
        operateOnNewObj(4, "4 + 0 =");
        operateOnNewObj(5, "5 + 0 =");
        operateOnNewObj(6, "6 + 0 =");
        operateOnNewObj(7, "7 + 0 =");
        operateOnNewObj(8, "8 + 0 =");
        operateOnNewObj(9, "9 + 0 =");
        operateOnNewObj(10, "10 + 0 =");
        operateOnNewObj(99, "99 + 0 =");
        operateOnNewObj(100, "100 + 0 =");
        operateOnNewObj(MAX_VALUE, "2147483647 + 0 =");
        for (int i = 0; i < ITERATE_COUNT; i++) {
            int number = r.nextInt(MAX_VALUE);
            operateOnNewObj(number, number + "+ 0 =");
        }

        operateOnNewObj(1, "0 + 1 =");
        operateOnNewObj(2, "0 + 2 =");
        operateOnNewObj(3, "0 + 3 =");
        operateOnNewObj(4, "0 + 4 =");
        operateOnNewObj(5, "0 + 5 =");
        operateOnNewObj(6, "0 + 6 =");
        operateOnNewObj(7, "0 + 7 =");
        operateOnNewObj(8, "0 + 8 =");
        operateOnNewObj(9, "0 + 9 =");
        operateOnNewObj(10, "0 + 10 =");
        operateOnNewObj(99, "0 + 99 =");
        operateOnNewObj(100, "0 + 100 =");
        operateOnNewObj(MAX_VALUE, "0 + 2147483647 =");
        for (int i = 0; i < ITERATE_COUNT; i++) {
            int number = r.nextInt(MAX_VALUE);
            operateOnNewObj(number, "0 +" + number + "=");
        }
        //todo boundary values
        //todo negative numbers

        operateOnNewObj(2, "1 + 1 =");
        operateOnNewObj(3, "2 + 1 =");
        operateOnNewObj(4, "3 + 1 =");
        operateOnNewObj(5, "4 + 1 =");
        operateOnNewObj(6, "5 + 1 =");
        operateOnNewObj(7, "6 + 1 =");
        operateOnNewObj(8, "7 + 1 =");
        operateOnNewObj(9, "8 + 1 =");
        operateOnNewObj(10, "9 + 1 =");
        operateOnNewObj(11, "10 + 1 =");
        for (int i = 0; i < ITERATE_COUNT; i++) {
            int number = r.nextInt(MAX_VALUE);
            operateOnNewObj(number, number + "+ 0 =");
        }

        operateOnNewObj(2, "1 + 1 =");
        operateOnNewObj(3, "1 + 2 =");
        operateOnNewObj(4, "1 + 3 =");
        operateOnNewObj(5, "1 + 4 =");
        operateOnNewObj(6, "1 + 5 =");
        operateOnNewObj(7, "1 + 6 =");
        operateOnNewObj(8, "1 + 7 =");
        operateOnNewObj(9, "1 + 8 =");
        operateOnNewObj(10, "1 + 9 =");
        operateOnNewObj(11, "1 + 10 =");
        for (int i = 0; i < ITERATE_COUNT; i++) {
            int number = r.nextInt(MAX_VALUE) - 1;
            operateOnNewObj(number + 1, "1 +" + number + "=");
        }


        operateOnNewObj(10, "7 + 3 =");
        operateOnNewObj(150, "50 + 100 =");
        operateOnNewObj(150, "100 + 50 =");
        operateOnNewObj(1048, "1024 + 24 =");
        operateOnNewObj(10000, "2500 + 7500 =");
        for (int i = 0; i < ITERATE_COUNT; i++) {
            int number1 = r.nextInt(MAX_VALUE / 2);
            int number2 = r.nextInt(MAX_VALUE / 2);

            operateOnNewObj(number1 + number2, number1 + "+" + number2 + "=");
            operateOnNewObj(number1 * 2, number1 + "+" + number1 + "=");
            operateOnNewObj(number2 * 2, number2 + "+" + number2 + "=");
        }

        operateOnNewObj(MAX_VALUE - 1, MAX_VALUE / 2 + "+" + MAX_VALUE / 2 + "=");
        operateOnNewObj(MAX_VALUE, MAX_VALUE + "+" + 0 + "=");
        operateOnNewObj(MAX_VALUE, 0 + "+" + MAX_VALUE + "=");

        //each operation common model obj
        model = new Model();
        operate(0, "0 + 0 =");
        operate(1, "1 + 0 =");
        operate(99, "99 + 0 =");
        operate(1, "0 + 1 =");
        operate(99, "0 + 99 =");
        operate(2, "1 + 1 =");
        operate(4, "1 + 3 =");
        operate(10, "7 + 3 =");
        operate(150, "100 + 50 =");
        operate(150, "50 + 100 =");

        operate(MAX_VALUE - 1, MAX_VALUE / 2, "+", MAX_VALUE / 2);
        operate(MAX_VALUE, MAX_VALUE, "+", 0);
        operate(MAX_VALUE, 0, "+", MAX_VALUE);

        operate(2, "1 + 1 =");
        operate(3, "1 + 2 =");
        operate(4, "1 + 3 =");
        operate(5, "1 + 4 =");
        operate(6, "1 + 5 =");
        operate(7, "1 + 6 =");
        operate(8, "1 + 7 =");
        operate(9, "1 + 8 =");
        operate(10, "1 + 9 =");
        operate(11, "1 + 10 =");
        for (int i = 0; i < ITERATE_COUNT; i++) {
            int number = r.nextInt(MAX_VALUE) - 1;
            operate(number + 1, "1 +" + number + "=");
        }

        //test subtract 2 numbers
        operateOnNewObj(0, 0, "−", 0);
        operateOnNewObj(1, 1, "−", 0);
        operateOnNewObj(99, 99, "−", 0);
        operateOnNewObj(-1, 0, "−", 1);
        operateOnNewObj(-99, 0, "−", 99);
        operateOnNewObj(0, 1, "−", 1);
        operateOnNewObj(-2, 1, "−", 3);
        operateOnNewObj(4, 7, "−", 3);
        operateOnNewObj(50, 100, "−", 50);
        operateOnNewObj(-50, 50, "−", 100);

        operateOnNewObj(0, MAX_VALUE / 2, "−", MAX_VALUE / 2);
        operateOnNewObj(MAX_VALUE, MAX_VALUE, "−", 0);
        operateOnNewObj(-MAX_VALUE, 0, "−", MAX_VALUE);

        model = new Model();
        operate(0, 0, "−", 0);
        operate(1, 1, "−", 0);
        operate(99, 99, "−", 0);
        operate(-1, 0, "−", 1);
        operate(-99, 0, "−", 99);
        operate(0, 1, "−", 1);
        operate(-2, 1, "−", 3);
        operate(4, 7, "−", 3);
        operate(50, 100, "−", 50);
        operate(-50, 50, "−", 100);

        operate(0, MAX_VALUE / 2, "−", MAX_VALUE / 2);
        operate(MAX_VALUE, MAX_VALUE, "−", 0);
        operate(-MAX_VALUE, 0, "−", MAX_VALUE);

        //test multiply 2 numbers
        operateOnNewObj(0, 0, "×", 0);
        operateOnNewObj(0, 1, "×", 0);
        operateOnNewObj(0, 99, "×", 0);
        operateOnNewObj(0, 0, "×", 1);
        operateOnNewObj(0, 0, "×", 99);
        operateOnNewObj(1, 1, "×", 1);
        operateOnNewObj(3, 1, "×", 3);
        operateOnNewObj(21, 7, "×", 3);
        operateOnNewObj(5000, 100, "×", 50);
        operateOnNewObj(5000, 50, "×", 100);

        operateOnNewObj(0, MAX_VALUE / 2, "−", MAX_VALUE / 2);
        operateOnNewObj(MAX_VALUE, MAX_VALUE, "−", 0);
        operateOnNewObj(-MAX_VALUE, 0, "−", MAX_VALUE);

        model = new Model();
        operate(0, 0, "×", 0);
        operate(0, 1, "×", 0);
        operate(0, 99, "×", 0);
        operate(0, 0, "×", 1);
        operate(0, 0, "×", 99);
        operate(1, 1, "×", 1);
        operate(3, 1, "×", 3);
        operate(21, 7, "×", 3);
        operate(5000, 100, "×", 50);
        operate(5000, 50, "×", 100);

        operate(0, MAX_VALUE / 2, "−", MAX_VALUE / 2);
        operate(MAX_VALUE, MAX_VALUE, "−", 0);
        operate(-MAX_VALUE, 0, "−", MAX_VALUE);

        //test divide 2 numbers
        operateOnNewObj(Double.NaN, 0, "÷", 0);
        operateOnNewObj(Double.POSITIVE_INFINITY, 1, "÷", 0);
        operateOnNewObj(Double.POSITIVE_INFINITY, 99, "÷", 0);
        operateOnNewObj(0, 0, "÷", 1);
        operateOnNewObj(0, 0, "÷", 99);
        operateOnNewObj(1, 1, "÷", 1);
        operateOnNewObj(0.3333, 1, "÷", 3);
        operateOnNewObj(2.3333, 7, "÷", 3);
        operateOnNewObj(2, 100, "÷", 50);
        operateOnNewObj(0.5, 50, "÷", 100);

        operateOnNewObj(1, MAX_VALUE / 2, "÷", MAX_VALUE / 2);
        operateOnNewObj(Double.POSITIVE_INFINITY, MAX_VALUE, "÷", 0);
        operateOnNewObj(0, 0, "÷", MAX_VALUE);

        model = new Model();
        operate(Double.NaN, 0, "÷", 0);
        operate(Double.POSITIVE_INFINITY, 1, "÷", 0);
        operate(Double.POSITIVE_INFINITY, 99, "÷", 0);
        operate(0, 0, "÷", 1);
        operate(0, 0, "÷", 99);
        operate(1, 1, "÷", 1);
        operate(0.3333, 1, "÷", 3);
        operate(2.3333, 7, "÷", 3);
        operate(2, 100, "÷", 50);
        operate(0.5, 50, "÷", 100);

        operate(1, MAX_VALUE / 2, "÷", MAX_VALUE / 2);
        operate(Double.POSITIVE_INFINITY, MAX_VALUE, "÷", 0);
        operate(0, 0, "÷", MAX_VALUE);
        // ----------- END Integer INPUT ---------

        operateOnNewObj(0, "+=");
        operateOnNewObj(0, "−=");
        operateOnNewObj(0, "×=");
        operateOnNewObj(Double.NaN, "÷=");

        model = new Model();
        operate(0, "+=");
        operate(0, "−=");
        operate(0, "×=");
        operate(0, "+=");
        operate(0, "−=");
        operate(0, "×=");
        operate(Double.NaN, "÷=");
        operate(Double.NaN, "×=");
        operate(Double.NaN, "+=");
        operate(Double.NaN, "−=");

        operateOnNewObj(0, "0 =");
        operateOnNewObj(1, "1 =");
        operateOnNewObj(5, "5 =");
        operateOnNewObj(999, "999 =");
        operateOnNewObj(MAX_VALUE, MAX_VALUE + "=");

//        operateOnNewObj(-1, "-1=");
//        operateOnNewObj(-5, "-5=");
//        operateOnNewObj(-50, "-50=");
//        operateOnNewObj(-MAX_VALUE, -MAX_VALUE + "=");

        operateOnNewObj(0, "0 =");
        operateOnNewObj(0, "0 = = =");
        operateOnNewObj(0, "0 = = = = = = = =");
        operateOnNewObj(1, "1 =");
        operateOnNewObj(1, "1 = = = = = = = = = =");
        operateOnNewObj(5, "5 =");
        operateOnNewObj(5, "5 = = = = =");
        operateOnNewObj(999, "999 =");
        operateOnNewObj(999, "999 = = = =");
        operateOnNewObj(MAX_VALUE, MAX_VALUE + "=");
        operateOnNewObj(MAX_VALUE, MAX_VALUE + "= = = = =");

        model = new Model();
        operate(0, "0 =");
        operate(0, "0 = = =");
        operate(0, "0 = = = = = =");
        operate(1, "1 =");
        operate(1, "1 = = = = = = =");
        operate(5, "5 =");
        operate(5, "5 = = = = =");
        operate(999, "999 =");
        operate(999, "999 = = = =");
        operate(MAX_VALUE, MAX_VALUE + "=");
        operate(MAX_VALUE, MAX_VALUE + "= = = =");

        operateOnNewObj(1, "1  = +");
        operateOnNewObj(2, "1 = + =");
        operateOnNewObj(4, "1 = + = + =");
        operateOnNewObj(8, "1 = + = + = + =");
        operateOnNewObj(16, "1 = + = + = + = + =");

//        operateOnNewObj(-1, "-1 = +");
//        operateOnNewObj(-2, "-1 = + =");
//        operateOnNewObj(-4, "-1 =+=+=");
//        operateOnNewObj(-8, "-1 =+=+=+=");
//        operateOnNewObj(-16, "-1 =+=+=+=+=");

        operateOnNewObj(1, "1 =−");
        operateOnNewObj(0, "1 =−=");
        operateOnNewObj(0, "1 =−=−=");
        operateOnNewObj(0, "1 =−=−=−=−=");

//        operateOnNewObj(-1, "-1 =−");
//        operateOnNewObj(0, "-1 =−=");
        operateOnNewObj(0, "1 =−=−=");
        operateOnNewObj(0, "1 =−=−=−=−=");
        operateOnNewObj(10, "10 =−");
        operateOnNewObj(0, "10 =−=");
        operateOnNewObj(0, "10 =−=−=");
        operateOnNewObj(0, "10 =−=−=−=−=");

        operateOnNewObj(15, "5 +==");
        operateOnNewObj(25, "5 +====");
        operateOnNewObj(45, "5 +========");
        operateOnNewObj(70, "5 +======+=");

        operateOnNewObj(15, "5 +==");
        operateOnNewObj(25, "5 +====");
        operateOnNewObj(45, "5 +========");
        operateOnNewObj(0, "5 +======−=");

        model = new Model();
        operate(1, "1 = +");
        operate(4, "1 =+=");
        operate(12, "1 =+=+=");
        operate(56, "1 =+=+=+=");
        operate(29, "1 = +");

//        operate(28, "-1 = +");
//        operate(54, "-1 =+=");

        model = new Model();
//        operate(-1, "-1 = +");
//        operate(-4, "-1 =+=");
//        operate(-12, "-1 =+=+=");
//        operate(-56, "-1 =+=+=+=");
//        operate(-464, "-1 =+=+=+=+=");

        model = new Model();
        operate(1, "1 =−");
        operate(0, "1 =−=");
        operate(0, "1 =−=−=");
        operate(0, "1 =−=−=−=−=");

//        operate(-1, "-1 =−");
//        operate(0, "-1 =−=");
        operate(0, "1 =−=−=");
        operate(0, "1 =−=−=−=−=");

        operate(10, "10 =−");
        operate(0, "10 =−=");
        operate(0, "10 =−=−=");
        operate(0, "10 =−=−=−=−=");

        operate(15, "5 +==");
        operate(25, "5 +====");
        operate(45, "5 +========");
        operate(70, "5 +======+=");

        operate(15, "5 +==");
        operate(25, "5 +====");
        operate(45, "5+========");
        operate(0, "5+======−=");

        operate(10, "5 ++=");
        operate(68, "34 ++++++=");
//        operate(-16, "-8 ++++++++=");

        //---
        model = new Model();
        operate(8, "3+5=");
        operate(8, "3+5=");
        operate(100, "31+5÷2÷9+1×11+67=");
        operate(167, "=");
        operate(301, "= =");
        operate(222, "= = − 11 ÷2 + 5 = =");
        operate(0, "+ 1 = + = + = − =");
        operate(-4, "− 1 = + = + =");
        operate(-1.3333, "÷3+");
        operate(-4, "×3=");

        model = new Model();
        operate(11, "×3++++++++++11=");
        operate(44, "×3++++++++++11=");

        model = new Model();
        operate(9.5, "+3÷2+1×3+2=");
        operate(4, "2=");

        //---------------TEST UNARY OPERATORS ------------
//        operateOnNewObj(3, "sqrt(9) = ");
    }

    private void operateOnNewObj(double expected, String str) {
        model = new Model();
        operate(expected, str);
    }

    private void operate(double expected, String str) {
        String data = str.replaceAll("\\s+", "");

        for (int i = 0; i < data.length(); i++) {
            char symbol = data.charAt(i);

            if (Character.isDigit(symbol)) {
                model.addInputDigit(String.valueOf(symbol));
            } else if (symbol == '.') {
                model.addInputPoint(String.valueOf(symbol));
            } else {
                model.addBinaryOperator(String.valueOf(symbol));
            }
        }

        assertEquals(expected, Double.valueOf(model.getDisplay()), DELTA);
    }

    private void operateOnNewObj(double expected, double value1, String operator, double value2) {
        model = new Model();
        operate(expected, value1, operator, value2);
    }

    private void operate(double expected, double value1, String operator, double value2) {
        model.addInputDigit(String.valueOf(value1));
        model.addBinaryOperator(operator);
        model.addInputDigit(String.valueOf(value2));
        model.addBinaryOperator("=");

        assertEquals(expected, Double.parseDouble(model.getDisplay()), DELTA);
    }
}