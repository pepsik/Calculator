package org.pepsik.model;

import org.junit.Test;
import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;
import org.pepsik.util.TextFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Integer.MAX_VALUE;
import static org.junit.Assert.*;

public class ModelTest {
    /**
     * Delta for double compare
     */
    private static final double DELTA = 0.00001;
    /**
     * Count for iterated tests
     */
    private static final int ITERATE_COUNT = 100;

    /**
     * Test model
     */
    private Model model;
    private Random r = new Random();

    @Test
    public void testExpression() {
        // -----------SUM TESTS------------
        // -----------SUM 2 VALUES---------
        //each operation on new model obj
        assertResult(0, "0 + 0 =");
        assertResult(1, "1 + 0 =");
        assertResult(1, "0 + 1 =");
        assertResult(2, "2 + 0 =");
        assertResult(3, "3 + 0 =");
        assertResult(4, "4 + 0 =");
        assertResult(5, "5 + 0 =");
        assertResult(6, "6 + 0 =");
        assertResult(7, "7 + 0 =");
        assertResult(8, "8 + 0 =");
        assertResult(9, "9 + 0 =");
        assertResult(10, "10 + 0 =");
        assertResult(99, "99 + 0 =");
        assertResult(100, "100 + 0 =");
        assertResult(MAX_VALUE, "2147483647 + 0 =");
        for (int i = 0; i < ITERATE_COUNT; i++) {
            int number = r.nextInt(MAX_VALUE);
            assertResult(number, number + "+ 0 =");
        }

        assertResult(1, "0 + 1 =");
        assertResult(2, "0 + 2 =");
        assertResult(3, "0 + 3 =");
        assertResult(4, "0 + 4 =");
        assertResult(5, "0 + 5 =");
        assertResult(6, "0 + 6 =");
        assertResult(7, "0 + 7 =");
        assertResult(8, "0 + 8 =");
        assertResult(9, "0 + 9 =");
        assertResult(10, "0 + 10 =");
        assertResult(99, "0 + 99 =");
        assertResult(100, "0 + 100 =");
        assertResult(MAX_VALUE, "0 + 2147483647 =");
        for (int i = 0; i < ITERATE_COUNT; i++) {
            int number = r.nextInt(MAX_VALUE);
            assertResult(number, "0 +" + number + "=");
        }

        assertResult(2, "1 + 1 =");
        assertResult(3, "2 + 1 =");
        assertResult(4, "3 + 1 =");
        assertResult(5, "4 + 1 =");
        assertResult(6, "5 + 1 =");
        assertResult(7, "6 + 1 =");
        assertResult(8, "7 + 1 =");
        assertResult(9, "8 + 1 =");
        assertResult(10, "9 + 1 =");
        assertResult(11, "10 + 1 =");
        for (int i = 0; i < ITERATE_COUNT; i++) {
            int number = r.nextInt(MAX_VALUE);
            assertResult(number, number + "+ 0 =");
        }

        assertResult(2, "1 + 1 =");
        assertResult(3, "1 + 2 =");
        assertResult(4, "1 + 3 =");
        assertResult(5, "1 + 4 =");
        assertResult(6, "1 + 5 =");
        assertResult(7, "1 + 6 =");
        assertResult(8, "1 + 7 =");
        assertResult(9, "1 + 8 =");
        assertResult(10, "1 + 9 =");
        assertResult(11, "1 + 10 =");
        for (int i = 0; i < ITERATE_COUNT; i++) {
            int number = r.nextInt(MAX_VALUE) - 1;
            assertResult(number + 1, "1 +" + number + "=");
        }


        assertResult(10, "7 + 3 =");
        assertResult(150, "50 + 100 =");
        assertResult(150, "100 + 50 =");
        assertResult(1048, "1024 + 24 =");
        assertResult(10000, "2500 + 7500 =");
        for (int i = 0; i < ITERATE_COUNT; i++) {
            int number1 = r.nextInt(MAX_VALUE / 2);
            int number2 = r.nextInt(MAX_VALUE / 2);

            assertResult(number1 + number2, number1 + "+" + number2 + "=");
            assertResult(number1 * 2, number1 + "+" + number1 + "=");
            assertResult(number2 * 2, number2 + "+" + number2 + "=");
        }

        assertResult(MAX_VALUE - 1, MAX_VALUE / 2 + "+" + MAX_VALUE / 2 + "=");
        assertResult(MAX_VALUE, MAX_VALUE + "+" + 0 + "=");
        assertResult(MAX_VALUE, 0 + "+" + MAX_VALUE + "=");

        //each operation common model obj
        assertResult(0, "0 + 0 =");
        assertResultWithoutClear(1, "1 + 0 =");
        assertResultWithoutClear(99, "99 + 0 =");
        assertResultWithoutClear(1, "0 + 1 =");
        assertResultWithoutClear(99, "0 + 99 =");
        assertResultWithoutClear(2, "1 + 1 =");
        assertResultWithoutClear(4, "1 + 3 =");
        assertResultWithoutClear(10, "7 + 3 =");
        assertResultWithoutClear(150, "100 + 50 =");
        assertResultWithoutClear(150, "50 + 100 =");

        operate(MAX_VALUE - 1, MAX_VALUE / 2, "+", MAX_VALUE / 2);
        operate(MAX_VALUE, MAX_VALUE, "+", 0);
        operate(MAX_VALUE, 0, "+", MAX_VALUE);

        assertResult(2, "1 + 1 =");
        assertResult(3, "1 + 2 =");
        assertResult(4, "1 + 3 =");
        assertResult(5, "1 + 4 =");
        assertResult(6, "1 + 5 =");
        assertResult(7, "1 + 6 =");
        assertResult(8, "1 + 7 =");
        assertResult(9, "1 + 8 =");
        assertResult(10, "1 + 9 =");
        assertResult(11, "1 + 10 =");
        for (int i = 0; i < ITERATE_COUNT; i++) {
            int number = r.nextInt(MAX_VALUE) - 1;
            assertResult(number + 1, "1 +" + number + "=");
        }

        //test subtract 2 numbers
        assertResult(0, 0, "-", 0);
        assertResult(1, 1, "-", 0);
        assertResult(99, 99, "-", 0);
        assertResult(-1, 0, "-", 1);
        assertResult(-99, 0, "-", 99);
        assertResult(0, 1, "-", 1);
        assertResult(-2, 1, "-", 3);
        assertResult(4, 7, "-", 3);
        assertResult(50, 100, "-", 50);
        assertResult(-50, 50, "-", 100);

        assertResult(0, MAX_VALUE / 2, "-", MAX_VALUE / 2);
        assertResult(MAX_VALUE, MAX_VALUE, "-", 0);
        assertResult(-MAX_VALUE, 0, "-", MAX_VALUE);

        assertResult(0, 0, "-", 0);
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

        assertResult(-20, "10 - 10 - 10 - 10 =");
        assertResultWithoutClear(-52, " - 10 - 10 - 10 -2 =");

        //test multiply 2 numbers
        assertResult(0, 0, "*", 0);
        assertResult(0, 1, "*", 0);
        assertResult(0, 99, "*", 0);
        assertResult(0, 0, "*", 1);
        assertResult(0, 0, "*", 99);
        assertResult(1, 1, "*", 1);
        assertResult(3, 1, "*", 3);
        assertResult(21, 7, "*", 3);
        assertResult(5000, 100, "*", 50);
        assertResult(5000, 50, "*", 100);

        assertResult(0, MAX_VALUE / 2, "-", MAX_VALUE / 2);
        assertResult(MAX_VALUE, MAX_VALUE, "-", 0);
        assertResult(-MAX_VALUE, 0, "-", MAX_VALUE);

        assertResult(0, 0, "*", 0);
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
        assertResult(0, 0, "/", 1);
        assertResult(0, 0, "/", 99);
        assertResult(1, 1, "/", 1);
        assertResult(0.3333333333333333, 1, "/", 3);
        assertResult(2.3333333333333335, 7, "/", 3);
        assertResult(2, 100, "/", 50);
        assertResult(0.5, 50, "/", 100);

        operate(2.3333333333333335, 7, "/", 3);
        assertResultWithoutClear(7, "*3 =");

        assertResult(1, MAX_VALUE / 2, "/", MAX_VALUE / 2);
        assertResult(0, 0, "/", MAX_VALUE);

        operate(0, 0, "/", 1);
        operate(0, 0, "/", 99);
        operate(1, 1, "/", 1);
        operate(0.3333333333333333, 1, "/", 3);
        operate(2.3333333333333333, 7, "/", 3);
        operate(2, 100, "/", 50);
        operate(0.5, 50, "/", 100);

        operate(1, MAX_VALUE / 2, "/", MAX_VALUE / 2);
        operate(0, 0, "/", MAX_VALUE);
        // ----------- END Integer INPUT ---------
    }

    @Test
    public void testRandomOperationCombination() {
        //equals and binary combo
        assertResult(0, "+=");
        assertResult(0, "-=");
        assertResult(0, "*=");

        assertResult(0, "+=");
        assertResult(0, "-=");
        assertResult(0, "*=");
        assertResult(0, "+=");
        assertResult(0, "-=");
        assertResult(0, "*=");

        //equals
        assertResult(0, "0 =");
        assertResult(1, "1 =");
        assertResult(5, "5 =");
        assertResult(999, "999 =");
        assertResult(MAX_VALUE, MAX_VALUE + "=");

        assertResult(-1, "negate(1)=");
        assertResult(-5, "negate(5)=");
        assertResult(-50, "negate(50)=");
        assertResult(MAX_VALUE, MAX_VALUE + "=");

        assertResult(0, "0 =");
        assertResult(0, "0 = = =");
        assertResult(0, "0 = = = = = = = =");
        assertResult(1, "1 =");
        assertResult(1, "1 = = = = = = = = = =");
        assertResult(5, "5 =");
        assertResult(5, "5 = = = = =");
        assertResult(999, "999 =");
        assertResult(999, "999 = = = =");
        assertResult(MAX_VALUE, MAX_VALUE + "=");
        assertResult(MAX_VALUE, MAX_VALUE + "= = = = =");

        assertResult(0, "0 =");
        assertResult(0, "0 = = =");
        assertResult(0, "0 = = = = = =");
        assertResult(1, "1 =");
        assertResult(1, "1 = = = = = = =");
        assertResult(5, "5 =");
        assertResult(5, "5 = = = = =");
        assertResult(999, "999 =");
        assertResult(999, "999 = = = =");
        assertResult(MAX_VALUE, MAX_VALUE + "=");
        assertResult(MAX_VALUE, MAX_VALUE + "= = = =");

        //equals and subtract combo
        assertResult(1, "1  = +");
        assertResult(2, "1 = + =");
        assertResult(4, "1 = + = + =");
        assertResult(8, "1 = + = + = + =");
        assertResult(16, "1 = + = + = + = + =");

        assertResult(-1, "negate(1) = +");
        assertResult(-2, "negate(1) = + =");
        assertResult(-4, "negate(1) =+=+=");
        assertResult(-8, "negate(1) =+=+=+=");
        assertResult(-16, "negate(1) =+=+=+=+=");

        assertResult(1, "1 =-");
        assertResult(0, "1 =-=");
        assertResult(0, "1 =-=-=");
        assertResult(0, "1 =-=-=-=-=");

        assertResult(-1, "negate(1) =-");
        assertResult(0, "negate(1) =-=");
        assertResult(0, "1 =-=-=");
        assertResult(0, "1 =-=-=-=-=");
        assertResult(10, "10 =-");
        assertResult(0, "10 =-=");
        assertResult(0, "10 =-=-=");
        assertResult(0, "10 =-=-=-=-=");

        assertResult(15, "5 +==");
        assertResult(25, "5 +====");
        assertResult(45, "5 +========");
        assertResult(70, "5 +======+=");

        assertResult(15, "5 +==");
        assertResult(25, "5 +====");
        assertResult(45, "5 +========");
        assertResult(0, "5 +======-=");

        assertResult(1, "1 = +");
        assertResultWithoutClear(4, "1 =+=");
        assertResultWithoutClear(12, "1 =+=+=");
        assertResultWithoutClear(56, "1 =+=+=+=");
        assertResultWithoutClear(29, "1 = +");
        assertResultWithoutClear(28, "negate(1) = +");
        assertResultWithoutClear(54, "negate(1) =+=");

        assertResult(-1, "negate(1) = +");
        assertResultWithoutClear(-4, "negate(1) =+=");
        assertResultWithoutClear(-12, "negate(1) =+=+=");
        assertResultWithoutClear(-56, "negate(1) =+=+=+=");
        assertResultWithoutClear(-464, "negate(1) =+=+=+=+=");

        assertResult(1, "1 =-");
        assertResult(0, "1 =-=");
        assertResult(0, "1 =-=-=");
        assertResult(0, "1 =-=-=-=-=");

        assertResult(-1, "negate(1) =-");
        assertResult(0, "negate(1) =-=");
        assertResult(0, "1 =-=-=");
        assertResult(0, "1 =-=-=-=-=");

        assertResult(10, "10 =-");
        assertResult(0, "10 =-=");
        assertResult(0, "10 =-=-=");
        assertResult(0, "10 =-=-=-=-=");

        assertResult(15, "5 +==");
        assertResult(25, "5 +====");
        assertResult(45, "5 +========");
        assertResult(70, "5 +======+=");

        assertResult(15, "5 +==");
        assertResult(25, "5 +====");
        assertResult(45, "5+========");
        assertResult(0, "5+======-=");

        assertResult(10, "5 ++=");
        assertResult(68, "34 ++++++=");
        assertResult(-16, "negate(8)++++++++=");

        assertResult(8, "3+5=");
        assertResultWithoutClear(8, "3+5=");
        assertResultWithoutClear(100, "31+5/2/9+1*11+67=");
        assertResultWithoutClear(167, "=");
        assertResultWithoutClear(301, "= =");
        assertResultWithoutClear(222, "= = - 11 /2 + 5 = =");
        assertResultWithoutClear(0, "+ 1 = + = + = - =");
        assertResultWithoutClear(-4, "- 1 = + = + =");
        assertResultWithoutClear(-1.3333333333333333, "/3+");
        assertResultWithoutClear(-4, "*3=");

        assertResult(11, "*3++++++++++11=");
        assertResultWithoutClear(44, "*3++++++++++11=");

        assertResult(9.5, "+3/2+1*3+2=");
        assertResultWithoutClear(4, "2=");
    }

    @Test
    public void testDivideByZeroOperation() {
        //----- DIVIDE BY ZERO -----
        assertException("0/0 =");
        assertException("/0=");
        assertException("99/0=");
        assertException("fraction(negate(0) = ");
        assertException("/=");
        assertException(MAX_VALUE + "/0=");
        assertException(MAX_VALUE + "/0=");
        assertException("/=");
        assertException("0/0=");
        assertException("1/ 0=");
        assertException("99/0=");
        assertException("fraction(0) = ");
    }

    @Test
    public void testUnaryOperation() {
        //---------------TEST UNARY OPERATORS------------
        // ----- NEGATE -----
        assertResult(0, "negate(0) = ");
        assertResult(0, "negate(negate(0) = ");
        assertResult(0, "negate(negate(negate(negate(0) = ");
        assertResult(-1, "negate(1) = ");
        assertResult(1, "negate(negate(1) = ");
        assertResult(-1, "negate(negate(negate(1) = ");
        assertResult(-9, "negate(9) = ");
        assertResult(9, "negate(negate(9) = ");
        assertResult(0, "negate() =");

        assertResult(0, "negate()");

        assertResult(0, "negate() = ");
        assertResult(0, "negate(negate() = ");
        assertResult(0, "negate(negate() = ");
        assertResult(0, "negate(negate() = ");
        assertResult(0, "negate(negate(negate(negate() = ");

        assertResult(-9, "negate(9) = ");
        assertResult(0, "negate() + negate() = ");
        assertResult(0, "negate(negate() + negate() = ");
        assertResult(0, "negate(negate() + negate(negate(negate() = ");
        assertResult(0, "negate(negate() + negate(negate(negate() = ");
        assertResult(-7, "negate(9) + 2 = ");
        assertResult(11, "negate(negate(9) + 2 = ");

        assertResult(-1, "negate(1) = ");
        assertResult(-9, "negate(9) =");
        assertResult(-4, "negate(1) + negate(3) = ");
        assertResult(9, "negate(negate(11) + negate(2) = ");
        assertResult(0, "negate(negate(4) + negate(negate(negate(4) = ");
        assertResult(0, "negate(negate(2) + negate(negate(negate(2) = ");
        assertResult(-7, "negate(9) + 2 =");

        assertResult(2, "sqrt(9)sqrt(4)=");
        assertResult(12, "sqrt(9)sqrt(4)sqrt(144)=");
        assertResult(32, "sqrt(9)sqrt(4)sqrt(144) + 20=");
        assertResult(22, "sqrt(9)sqrt(4)sqrt(144) + sqrt(100)=");
        // -----END NEGATE -----

        //---- SQRT ---
        assertResult(3, "sqrt(9) = ");
        assertResult(9, "sqrt(81) = ");
        assertResult(3, "sqrt(sqrt(81) = ");
        assertResult(9, "sqrt(81) = ");

        assertResult(3, "sqrt(sqrt(81) + negate(0) = ");
        assertResult(3, "sqrt(sqrt(81) + 0 = ");
        assertResult(4, "sqrt(sqrt(81) + 1 = ");
        assertResult(5, "sqrt(sqrt(81) + 2 = ");
        assertResult(7.5, "sqrt(sqrt(81) + 2 + 10/2 = ");
        assertResult(10, "sqrt(sqrt(81) + 2*2 = ");

        assertResult(3, "sqrt(sqrt(81) - 0 = ");
        assertResult(2, "sqrt(sqrt(81) - 1 = ");
        assertResult(2, "sqrt(sqrt(81) - 2*2 = ");
        assertResult(1, "sqrt(sqrt(81) - 2 = ");
        assertResult(0, "sqrt(sqrt(81) - 3 = ");
        assertResult(-1, "sqrt(sqrt(81) - 4 = ");
        assertResult(-4.5, "sqrt(sqrt(81) - 2 - 10/2 = ");

        assertResult(-6, "sqrt(sqrt(81) * negate(2) = ");
        assertResult(-3, "sqrt(sqrt(81) * negate(1) = ");
        assertResult(0, "sqrt(sqrt(81) * 0 = ");
        assertResult(3, "sqrt(sqrt(81) * 1 = ");
        assertResult(6, "sqrt(sqrt(81) * 2 = ");
        assertResult(12, "sqrt(sqrt(81) * 2*2 = ");

        assertResult(1.5, "sqrt(sqrt(81) / 2 = ");
        assertResult(3, "sqrt(sqrt(81) / 2*2 = ");

        assertResult(5, "sqrt(sqrt(81) + sqrt(4) = ");
        assertResult(5, " + sqrt(sqrt(81) + sqrt(4) = ");

        assertResult(3, "sqrt(56)sqrt(9)=");
        //---- END SQRT ---

        //-----SQUARE -----
        assertResult(9, "square(3) = ");
        assertResult(81, "square(square(3) = ");
        assertResult(83, "square(square(3) + 2 = ");
        assertResult(79, "square(square(3) - 2 = ");

        assertResult(0, "square(0) = ");
        assertResult(0, "square(square(0) = ");
        assertResult(0, "square(square(square(square(0) = ");
        assertResult(1, "square(1) = ");
        assertResult(1, "square(square(1) = ");
        assertResult(1, "square(square(square(1) = ");
        assertResult(81, "square(9) = ");
        assertResult(6561, "square(square(9) = ");

        assertResult(0, "square() = square()");
        assertResult(0, "square(square() = square()");
        assertResult(0, "square(square() = square(square()");
        assertResult(0, "square(square() = square(square(square()");
        assertResult(0, "square(square(square(square() = square(square(square()");

        assertResult(81, "square(9) = ");
        assertResult(4, "square(2) = ");
        assertResult(0, "square() + square() = ");
        assertResult(0, "square(square() + square() = ");
        assertResult(0, "square(square() + square(square(square() =");
        assertResult(0, "square(square() + square(square(square() = ");
        assertResult(6, "square(2) + 2 = ");
        assertResult(3, "square(1) + 2 = ");
        assertResult(2, "square(square(0) + 2 = ");
        assertResult(18, "square(square(2) + 2 = ");

        assertResult(9, "square(56)square(9)square(3)=");
        //--------- END SQUARE --------

        assertResult(1.7320508075688772, "sqrt(3) = ");
        assertResultWithoutClear(3, "square() = ");

        //--------- FRACTION --------
        assertResult(0.1, "fraction(10) = ");
        assertResult(0.02, "fraction(50) = ");
        assertResult(2, "fraction(fraction(2) = ");
        assertResult(0.02, "fraction(50) = ");
        assertResult(0.02, "fraction(50) = ");

        assertResult(-0.1, "fraction(negate(10) = ");
        assertResult(-0.02, "fraction(negate(50) = ");
        assertResult(-2, "fraction(fraction(negate(2) = ");
        assertResult(-0.02, "fraction(negate(50) = ");

        assertResult(1, "fraction(fraction(1) + negate(0) = ");
        assertResult(3, "fraction(fraction(2) + 1 = ");
        assertResult(2.33333333, "fraction(3) + 2 = ");
        assertResult(6.05, "fraction(10) + 2 + 10/2 = ");
        assertResult(4.5, "fraction(4) + 2*2 = ");

        assertResult(1, "fraction(fraction(1) - 0 = ");
        assertResult(0, "fraction(fraction(1) - 1 = ");
        assertResult(-2, "fraction(fraction(1) - 2*2 = ");
        assertResult(-1, "fraction(fraction(1) - 2 = ");
        assertResult(-2, "fraction(fraction(1) - 3 = ");
        assertResult(-3, "fraction(fraction(1) - 4 = ");
        assertResult(-5.5, "fraction(fraction(1) - 2 - 10/2 = ");

        assertResult(-0.4, "fraction(fraction(fraction(5) * negate(2) = ");
        assertResult(-0.05, "fraction(fraction(fraction(20) * negate(1) = ");
        assertResult(0, "fraction(fraction(fraction(100) * 0 = ");
        assertResult(0.01, "fraction(fraction(fraction(100) * 1 = ");
        assertResult(0.66666, "fraction(fraction(fraction(3) * 2 = ");
        assertResult(1.33333, "fraction(fraction(fraction(3) * 2*2 = ");

        assertResult(0.0151515151515152, "fraction(33) / 2 = ");
        assertResult(0.0303030303030303, "   fraction(33) / 2*2 = ");

        assertResult(1.5, "fraction(fraction(1) + fraction(2) = ");
        assertResult(-1.5, " + negate(fraction(fraction(1) + negate(fraction(2) = ");
        //--------- END FRACTION --------

        assertResult(0, "percent()=");
        assertResult(0, "percent(percent()=");
        assertResult(0, "percent(percent(percent()=");
        assertResult(0, "percent(0)=");
        assertResult(0, "percent(percent(0)=");
        assertResult(0, "percent(percent(percent(percent(percent(0)=");
        assertResult(0, "percent(1)=");
        assertResult(0, "percent(percent(1)=");
        assertResult(0, "percent(percent(percent(percent(percent(percent(percent(1)=");
        assertResult(0, "percent(100)=");
        assertResult(0, "percent(percent(percent(percent(percent(percent(percent(percent(percent(100)=");
        assertResult(0, "percent(99999)=");
        assertResult(0, "percent(percent(percent(percent(percent(percent(percent(percent(percent(percent(percent(percent(99999)=");

        assertResult(120, "100 + percent(20) =");
        assertResult(62.5, "50 + percent(percent(50) =");
        assertResult(37515600, "1000 + percent(percent(percent(50) + percent(20) + percent(percent(100) =");
        assertResult(20.0428112112, "10 + percent(0.2) + percent(100) + percent(percent(0.07) =");
        assertResult(0.000007, "10 + percent(0.2) / percent(100) * percent(percent(0.07) =");
        assertOperand(1.004004, "10 + percent(0.2) = percent()");

        assertResult(0, "percent(56)percent(9)percent(3)=");
    }

    @Test
    public void testMemoryOperation() {
        //--------- MEMORY ----------
        assertResult(5, "5 + 10");
        model.addToMemory();
        assertResultWithoutClear(15, " + ");
        model.getMemory();
        assertResultWithoutClear(25, " = ");

        // add
        model.getMemory();
        assertResultWithoutClear(15, " + 5 =");
        model.addToMemory();
        model.addToMemory();
        model.addToMemory();
        model.getMemory();
        assertResultWithoutClear(50, " - 5 = 50");

        // get
        assertResult(0, "");
        model.addToMemory();
        model.getMemory();
        assertResultWithoutClear(0, "");
        model.getMemory();
        assertResultWithoutClear(0, "");
        model.getMemory();
        assertResultWithoutClear(0, "");

        //add get
        assertResultWithoutClear(1, "1 = ");
        model.addToMemory();
        model.getMemory();
        assertResultWithoutClear(1, "");
        assertResultWithoutClear(355, "333 + 22 =");
        model.getMemory();
        assertResultWithoutClear(0.2, " + 0 / 5 = ");

        //set
        assertResult(54, "52 + 2 =");
        model.saveMemory();
        assertResultWithoutClear(54, " + 0 =");
        model.saveMemory();
        model.getMemory();
        assertResultWithoutClear(54, " - 0 =");
        model.addToMemory();
        model.addToMemory();
        model.addToMemory();
        model.getMemory();
        model.getMemory();
        assertResultWithoutClear(216, " + 0  =");

        //clear
        assertResult(20, "20 = ");
        model.saveMemory();
        assertResultWithoutClear(30, " + 10 - ");
        model.getMemory();
        assertResultWithoutClear(10, "=");
        model.clearMemory();
        assertResultWithoutClear(-10, "=");
        model.addToMemory();
        model.getMemory();
        assertResultWithoutClear(0, " + 10 =");
        //---------END MEMORY ----------
    }

    @Test
    public void testExpressionHistory() {
        model = new Model();
        parseAndExecute("10 - 5 * 2 - 10 * 2.5");
        assertEquals("10 - 5 * 2 - 10 * 2.5", TextFormatter.history(model.getCurrentExpression()));
    }

    private void assertResult(double expected, String str) {
        model = new Model();
        parseAndExecute(str);

        assertEquals(expected, model.getResult().doubleValue(), DELTA);
    }

    private void assertResultWithoutClear(double expected, String str) {
        parseAndExecute(str);

        assertEquals(expected, model.getResult().doubleValue(), DELTA);
    }

    private void assertOperand(double expected, String str) {
        model = new Model();
        parseAndExecute(str);

        assertEquals(expected, model.getOperand().doubleValue(), DELTA);
    }

    private void assertException(String str) {
        model = new Model();
        try {
            parseAndExecute(str);
            fail("MUST FAIL!");
        } catch (ArithmeticException e) {
            /*expected*/
        }
    }

    private void parseAndExecute(String str) {
        String data = str.replaceAll("\\s+", "");

        StringBuilder operatorSB = new StringBuilder();
        StringBuilder numberSB = new StringBuilder();
        List<UnaryOperation> listUnary = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            char symbol = data.charAt(i);

            if (Character.isDigit(symbol)) {
                numberSB.append(new BigDecimal(String.valueOf(symbol)));
                model.addNumber(new BigDecimal(numberSB.toString()));
                continue;
            }

            if (symbol == '.') {
                numberSB.append(".");
                continue;
            }

            if (BinaryOperation.find(symbol) != null) {
                if (numberSB.length() > 0) {
                    model.addNumber(new BigDecimal(numberSB.toString()));
                }

                model.addBinaryOperator(BinaryOperation.find(symbol));
                numberSB = new StringBuilder();
                operatorSB = new StringBuilder();
                continue;
            }

            if (Character.isAlphabetic(symbol)) {
                operatorSB.append(symbol);
                continue;
            }

            if (symbol == '(') {
                listUnary.add(UnaryOperation.valueOf(operatorSB.toString().toUpperCase()));
                operatorSB = new StringBuilder();
                continue;
            }

            if (symbol == ')') {
                if (numberSB.length() > 0) {
                    model.addNumber(new BigDecimal(numberSB.toString()));
                    numberSB = new StringBuilder();
                }

                for (UnaryOperation operation : listUnary) {
                    model.addUnaryOperator(operation);
                }
                listUnary.clear();
                continue;
            }

            throw new RuntimeException("UNEXPECTED symbol " + symbol);
        }
    }

    private void assertResult(double expected, double value1, String operator, double value2) {
        model = new Model();
        operate(expected, value1, operator, value2);
    }

    private void operate(double expected, double value1, String operator, double value2) {
        model.addNumber(BigDecimal.valueOf(value1));
        model.addBinaryOperator(BinaryOperation.find(operator.charAt(0)));
        model.addNumber(BigDecimal.valueOf(value2));
        model.addBinaryOperator(BinaryOperation.EQUAL);

        assertEquals(expected, model.getResult().doubleValue(), DELTA);
    }
}