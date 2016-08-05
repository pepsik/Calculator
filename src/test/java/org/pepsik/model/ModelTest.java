package org.pepsik.model;

import org.junit.Test;
import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.instanceOf;
import static java.lang.Integer.MAX_VALUE;
import static org.junit.Assert.*;

/**
 * Created by pepsik on 7/27/2016.
 */
public class ModelTest {
    private static final double DELTA = 0.00001;
    public static final int ITERATE_COUNT = 100;

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
        for (int i = 0; i < ITERATE_COUNT; i++) {
            int random = r.nextInt(MAX_VALUE);
            operateOnNewObj(random, String.valueOf(random));
        }

        operateOnNewObj(1, "1.0");
        operateOnNewObj(9.9, "9.9");
        operateOnNewObj(1, "1.00");
        operateOnNewObj(1.11, "1.11");
        operateOnNewObj(11.1, "11.1");
        operateOnNewObj(12.3, "12.3");
        operateOnNewObj(9.99, "9.99");
        operateOnNewObj(10, "10.00");
        operateOnNewObj(123456, "1 2 3 4 5 6.");
        operateOnNewObj(0.1234567890, ".1 2 3 4 5 6 7 8 9 0");

        operateOnNewObj(1111111111111111.0, "1111111111111111");

        operateOnNewObj(11111111111111111.0, "11111111111111111");

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
        for (int i = 0; i < ITERATE_COUNT; i++) {
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
        operateOnNewObj(0, "0 + 0 =");
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

        operateOnNewObj(0, 0, "-", 0);
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

        operateOnNewObj(-20, "10 - 10 - 10 - 10 =");
        operate(-52, " - 10 - 10 - 10 -2 =");

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

        operateOnNewObj(0, 0, "*", 0);
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
        operateOnNewObj(0, 0, "/", 1);
        operateOnNewObj(0, 0, "/", 99);
        operateOnNewObj(1, 1, "/", 1);
        operateOnNewObj(0.3333333333333333, 1, "/", 3);
        operateOnNewObj(2.3333333333333335, 7, "/", 3);
        operateOnNewObj(2, 100, "/", 50);
        operateOnNewObj(0.5, 50, "/", 100);

        operate(2.3333333333333335, 7, "/", 3);
        operate(7, "*3 =");

        operateOnNewObj(1, MAX_VALUE / 2, "/", MAX_VALUE / 2);
        operateOnNewObj(0, 0, "/", MAX_VALUE);

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

        //equals and binary combo
        operateOnNewObj(0, "+=");
        operateOnNewObj(0, "-=");
        operateOnNewObj(0, "*=");

        operateOnNewObj(0, "+=");
        operate(0, "-=");
        operate(0, "*=");
        operate(0, "+=");
        operate(0, "-=");
        operate(0, "*=");

        //equals
        operateOnNewObj(0, "0 =");
        operateOnNewObj(1, "1 =");
        operateOnNewObj(5, "5 =");
        operateOnNewObj(999, "999 =");
        operateOnNewObj(MAX_VALUE, MAX_VALUE + "=");

        operateOnNewObj(-1, "negate(1)=");
        operateOnNewObj(-5, "negate(5)=");
        operateOnNewObj(-50, "negate(50)=");
        operateOnNewObj(-MAX_VALUE, -MAX_VALUE + "=");

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

        operateOnNewObj(0, "0 =");
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

        //equals and subtract combo
        operateOnNewObj(1, "1  = +");
        operateOnNewObj(2, "1 = + =");
        operateOnNewObj(4, "1 = + = + =");
        operateOnNewObj(8, "1 = + = + = + =");
        operateOnNewObj(16, "1 = + = + = + = + =");

        operateOnNewObj(-1, "negate(1) = +");
        operateOnNewObj(-2, "negate(1) = + =");
        operateOnNewObj(-4, "negate(1) =+=+=");
        operateOnNewObj(-8, "negate(1) =+=+=+=");
        operateOnNewObj(-16, "negate(1) =+=+=+=+=");

        operateOnNewObj(1, "1 =-");
        operateOnNewObj(0, "1 =-=");
        operateOnNewObj(0, "1 =-=-=");
        operateOnNewObj(0, "1 =-=-=-=-=");

        operateOnNewObj(-1, "negate(1) =-");
        operateOnNewObj(0, "negate(1) =-=");
        operateOnNewObj(0, "1 =-=-=");
        operateOnNewObj(0, "1 =-=-=-=-=");
        operateOnNewObj(10, "10 =-");
        operateOnNewObj(0, "10 =-=");
        operateOnNewObj(0, "10 =-=-=");
        operateOnNewObj(0, "10 =-=-=-=-=");

        operateOnNewObj(15, "5 +==");
        operateOnNewObj(25, "5 +====");
        operateOnNewObj(45, "5 +========");
        operateOnNewObj(70, "5 +======+=");

        operateOnNewObj(15, "5 +==");
        operateOnNewObj(25, "5 +====");
        operateOnNewObj(45, "5 +========");
        operateOnNewObj(0, "5 +======-=");

        operateOnNewObj(1, "1 = +");
        operate(4, "1 =+=");
        operate(12, "1 =+=+=");
        operate(56, "1 =+=+=+=");
        operate(29, "1 = +");
        operate(28, "negate(1) = +");
        operate(54, "negate(1) =+=");

        operateOnNewObj(-1, "negate(1) = +");
        operate(-4, "negate(1) =+=");
        operate(-12, "negate(1) =+=+=");
        operate(-56, "negate(1) =+=+=+=");
        operate(-464, "negate(1) =+=+=+=+=");

        operateOnNewObj(1, "1 =-");
        operate(0, "1 =-=");
        operate(0, "1 =-=-=");
        operate(0, "1 =-=-=-=-=");

        operate(-1, "negate(1) =-");
        operate(0, "negate(1) =-=");
        operate(0, "1 =-=-=");
        operate(0, "1 =-=-=-=-=");

        operate(10, "10 =-");
        operate(0, "10 =-=");
        operate(0, "10 =-=-=");
        operate(0, "10 =-=-=-=-=");

        operate(15, "5 +==");
        operate(25, "5 +====");
        operate(45, "5 +========");
        operate(70, "5 +======+=");

        operate(15, "5 +==");
        operate(25, "5 +====");
        operate(45, "5+========");
        operate(0, "5+======-=");

        operate(10, "5 ++=");
        operate(68, "34 ++++++=");
        operate(-16, "negate(8)++++++++=");

        operateOnNewObj(8, "3+5=");
        operate(8, "3+5=");
        operate(100, "31+5/2/9+1*11+67=");
        operate(167, "=");
        operate(301, "= =");
        operate(222, "= = - 11 /2 + 5 = =");
        operate(0, "+ 1 = + = + = - =");
        operate(-4, "- 1 = + = + =");
        operate(-1.3333333333333333, "/3+");
        operate(-4, "*3=");

        operateOnNewObj(11, "*3++++++++++11=");
        operate(44, "*3++++++++++11=");

        operateOnNewObj(9.5, "+3/2+1*3+2=");
        operate(4, "2=");

        //---------------TEST UNARY OPERATORS------------
        // ----- NEGATE -----
        operateOnNewObj(0, "negate(0) = ");
        operateOnNewObj(0, "negate(negate(0) = ");
        operateOnNewObj(0, "negate(negate(negate(negate(0) = ");
        operateOnNewObj(-1, "negate(1) = ");
        operateOnNewObj(1, "negate(negate(1) = ");
        operateOnNewObj(-1, "negate(negate(negate(1) = ");
        operateOnNewObj(-9, "negate(9) = ");
        operateOnNewObj(9, "negate(negate(9) = ");
        operateOnNewObj(0, "negate() =");

        operateOnNewObj(0, "negate()");

        operateOnNewObj(0, "negate() = negate()");
        operateOnNewObj(0, "negate(negate() = negate()");
        operateOnNewObj(0, "negate(negate() = negate(negate()");
        operateOnNewObj(0, "negate(negate() = negate(negate(negate()");
        operateOnNewObj(0, "negate(negate(negate(negate() = negate(negate(negate()");

        operateOnNewObj(9, "negate(9) = negate()");
        operateOnNewObj(-9, "negate(9) = negate(negate()");
        operateOnNewObj(0, "negate() + negate() = negate()");
        operateOnNewObj(0, "negate(negate() + negate() = negate()");
        operateOnNewObj(0, "negate(negate() + negate(negate(negate() = negate()");
        operateOnNewObj(0, "negate(negate() + negate(negate(negate() = negate(negate(negate()");
        operateOnNewObj(7, "negate(9) + 2 = negate()");
        operateOnNewObj(-7, "negate(9) + 2 = negate(negate()");
        operateOnNewObj(11, "negate(negate(9) + 2 = negate(negate()");
        operateOnNewObj(-11, "negate(negate(9) + 2 = negate(negate(negate()");

        operateOnNewObj(-3, "negate(1) = negate(3)");
        operateOnNewObj(2, "negate(9) = negate(negate(2)");
        operateOnNewObj(4, "negate(1) + negate(3) = negate()");
        operateOnNewObj(-5, "negate(negate(11) + negate(2) = negate(5)");
        operateOnNewObj(-3, "negate(negate(4) + negate(negate(negate(4) = negate(3)");
        operateOnNewObj(-1, "negate(negate(2) + negate(negate(negate(2) = negate(negate(negate(1)");
        operateOnNewObj(-5, "negate(9) + 2 = negate(5)");
        // -----END NEGATE -----

        //---- SQRT ---
        operateOnNewObj(3, "sqrt(9) = ");
        operateOnNewObj(9, "sqrt(81) = ");
        operateOnNewObj(3, "sqrt(sqrt(81) = ");
        operateOnNewObj(3, "sqrt(81) = sqrt()");

        operateOnNewObj(3, "sqrt(sqrt(81) + negate(0) = ");
        operateOnNewObj(3, "sqrt(sqrt(81) + 0 = ");
        operateOnNewObj(4, "sqrt(sqrt(81) + 1 = ");
        operateOnNewObj(5, "sqrt(sqrt(81) + 2 = ");
        operateOnNewObj(7.5, "sqrt(sqrt(81) + 2 + 10/2 = ");
        operateOnNewObj(10, "sqrt(sqrt(81) + 2*2 = ");

        operateOnNewObj(3, "sqrt(sqrt(81) - 0 = ");
        operateOnNewObj(2, "sqrt(sqrt(81) - 1 = ");
        operateOnNewObj(2, "sqrt(sqrt(81) - 2*2 = ");
        operateOnNewObj(1, "sqrt(sqrt(81) - 2 = ");
        operateOnNewObj(0, "sqrt(sqrt(81) - 3 = ");
        operateOnNewObj(-1, "sqrt(sqrt(81) - 4 = ");
        operateOnNewObj(-4.5, "sqrt(sqrt(81) - 2 - 10/2 = ");

        operateOnNewObj(-6, "sqrt(sqrt(81) * negate(2) = ");
        operateOnNewObj(-3, "sqrt(sqrt(81) * negate(1) = ");
        operateOnNewObj(0, "sqrt(sqrt(81) * 0 = ");
        operateOnNewObj(3, "sqrt(sqrt(81) * 1 = ");
        operateOnNewObj(6, "sqrt(sqrt(81) * 2 = ");
        operateOnNewObj(12, "sqrt(sqrt(81) * 2*2 = ");

        operateOnNewObj(1.5, "sqrt(sqrt(81) / 2 = ");
        operateOnNewObj(3, "sqrt(sqrt(81) / 2*2 = ");

        operateOnNewObj(5, "sqrt(sqrt(81) + sqrt(4) = ");
        operateOnNewObj(5, " + sqrt(sqrt(81) + sqrt(4) = ");
        //---- END SQRT ---

        //-----SQUARE -----
        operateOnNewObj(9, "square(3) = ");
        operateOnNewObj(81, "square(square(3) = ");
        operateOnNewObj(83, "square(square(3) + 2 = ");
        operateOnNewObj(79, "square(square(3) - 2 = ");

        operateOnNewObj(0, "square(0) = ");
        operateOnNewObj(0, "square(square(0) = ");
        operateOnNewObj(0, "square(square(square(square(0) = ");
        operateOnNewObj(1, "square(1) = ");
        operateOnNewObj(1, "square(square(1) = ");
        operateOnNewObj(1, "square(square(square(1) = ");
        operateOnNewObj(81, "square(9) = ");
        operateOnNewObj(6561, "square(square(9) = ");

        operateOnNewObj(0, "square() = square()");
        operateOnNewObj(0, "square(square() = square()");
        operateOnNewObj(0, "square(square() = square(square()");
        operateOnNewObj(0, "square(square() = square(square(square()");
        operateOnNewObj(0, "square(square(square(square() = square(square(square()");

        operateOnNewObj(6561, "square(9) = square()");
        operateOnNewObj(256, "square(2) = square(square()");
        operateOnNewObj(0, "square() + square() = square()");
        operateOnNewObj(0, "square(square() + square() = square()");
        operateOnNewObj(0, "square(square() + square(square(square() = square()");
        operateOnNewObj(0, "square(square() + square(square(square() = square(square(square()");
        operateOnNewObj(36, "square(2) + 2 = square()");
        operateOnNewObj(81, "square(1) + 2 = square(square()");
        operateOnNewObj(16, "square(square(0) + 2 = square(square()");
        operateOnNewObj(11019960576.0, "square(square(2) + 2 = square(square(square()");
        //--------- END SQUARE --------

        operateOnNewObj(1.7320508075688772, "sqrt(3) = ");
        operate(3, "square() = ");

        //--------- FRACTION --------
        operateOnNewObj(0.1, "fraction(10) = ");
        operateOnNewObj(0.02, "fraction(50) = ");
        operateOnNewObj(2, "fraction(fraction(2) = ");
        operateOnNewObj(50, "fraction(50) = fraction()");
        operateOnNewObj(0.2, "fraction(50) = fraction(5)");

        operateOnNewObj(-0.1, "fraction(negate(10) = ");
        operateOnNewObj(-0.02, "fraction(negate(50) = ");
        operateOnNewObj(-2, "fraction(fraction(negate(2) = ");
        operateOnNewObj(-50, "fraction(negate(50) = fraction()");
        operateOnNewObj(0.2, "fraction(negate(50) = fraction(5)");
        operateOnNewObj(-0.2, "fraction(negate(50) = fraction(negate(5)");

        operateOnNewObj(1, "fraction(fraction(1) + negate(0) = ");
        operateOnNewObj(3, "fraction(fraction(2) + 1 = ");
        operateOnNewObj(2.33333333, "fraction(3) + 2 = ");
        operateOnNewObj(6.05, "fraction(10) + 2 + 10/2 = ");
        operateOnNewObj(4.5, "fraction(4) + 2*2 = ");

        operateOnNewObj(1, "fraction(fraction(1) - 0 = ");
        operateOnNewObj(0, "fraction(fraction(1) - 1 = ");
        operateOnNewObj(-2, "fraction(fraction(1) - 2*2 = ");
        operateOnNewObj(-1, "fraction(fraction(1) - 2 = ");
        operateOnNewObj(-2, "fraction(fraction(1) - 3 = ");
        operateOnNewObj(-3, "fraction(fraction(1) - 4 = ");
        operateOnNewObj(-5.5, "fraction(fraction(1) - 2 - 10/2 = ");

        operateOnNewObj(-0.4, "fraction(fraction(fraction(5) * negate(2) = ");
        operateOnNewObj(-0.05, "fraction(fraction(fraction(20) * negate(1) = ");
        operateOnNewObj(0, "fraction(fraction(fraction(100) * 0 = ");
        operateOnNewObj(0.01, "fraction(fraction(fraction(100) * 1 = ");
        operateOnNewObj(0.66666, "fraction(fraction(fraction(3) * 2 = ");
        operateOnNewObj(1.33333, "fraction(fraction(fraction(3) * 2*2 = ");

        operateOnNewObj(0.0151515151515152, "fraction(33) / 2 = ");
        operateOnNewObj(0.0303030303030303, "   fraction(33) / 2*2 = ");

        operateOnNewObj(1.5, "fraction(fraction(1) + fraction(2) = ");
        operateOnNewObj(-1.5, " + negate(fraction(fraction(1) + negate(fraction(2) = ");
        //--------- END FRACTION --------

        //--------- MEMORY ----------
        operateOnNewObj(10, "5 + 10");
        model.addToMemory();
        operate(15, " + ");
        model.getMemory();
        operate(25, " = ");

        // add
        model.getMemory();
        operate(10, "");
        model.addToMemory();
        model.addToMemory();
        model.addToMemory();
        model.getMemory();
        operate(40, "");

        // get
        operateOnNewObj(0, "");
        model.addToMemory();
        model.getMemory();
        operate(0, "");
        model.getMemory();
        operate(0, "");
        model.getMemory();
        operate(0, "");

        //add get
        operate(1, "1 = ");
        model.addToMemory();
        model.getMemory();
        operate(1, "");
        operate(355, "333 + 22 =");
        model.getMemory();
        operate(1, "");

        //set
        operateOnNewObj(54, "52 + 2 =");
        model.setMemory();
        operate(54, "");
        model.setMemory();
        model.getMemory();
        operate(54, "");
        model.addToMemory();
        model.addToMemory();
        model.addToMemory();
        model.getMemory();
        model.getMemory();
        operate(216, "");

        //clear
        operateOnNewObj(20, "20 = ");
        model.setMemory();
        operate(10, "10");
        model.getMemory();
        operate(20, "");
        model.clearMemory();
        operate(20, "=");
        operate(5, "5");
        model.addToMemory();
        model.getMemory();
        operate(5, "");
        //---------END MEMORY ----------

        //------ BACKSPACE ------
        operateOnNewObj(55, "55");
        model.backspace();
        operate(5, "");
        model.backspace();
        operate(0, "");
        model.backspace();
        operate(0, "");

        operate(5, "10 + 5");
        model.backspace();
        operate(10, "=");

        operate(5, "10 + 5");
        model.backspace();
        model.backspace();
        model.backspace();
        operate(10, "=");
        //-------- END BACKSPACE ------

        //----- DIVIDE BY ZERO -----
        operateOnNewObjEx(ArithmeticException.class, "0/0 =");
        operateOnNewObjEx(ArithmeticException.class, "/0=");
        operateOnNewObjEx(ArithmeticException.class, "99/0=");
        operateOnNewObjEx(ArithmeticException.class, "fraction(negate(0) = ");
        operateOnNewObjEx(ArithmeticException.class, "/=");
        operateOnNewObjEx(ArithmeticException.class, MAX_VALUE + "/0=");
        operateOnNewObjEx(ArithmeticException.class, MAX_VALUE + "/0=");
        operateOnNewObjEx(ArithmeticException.class, "/=");
        operateOnNewObjEx(ArithmeticException.class, "0/0=");
        operateOnNewObjEx(ArithmeticException.class, "1/ 0=");
        operateOnNewObjEx(ArithmeticException.class, "99/0=");
        operateOnNewObjEx(ArithmeticException.class, "fraction(0) = ");
    }

    private void operateOnNewObj(double expected, String str) {
        model = new Model();
        operate(expected, str);
    }

    private void operateOnNewObjEx(Class ex, String str) {
        model = new Model();
        try {
            operate(0, str);
            fail("MUST FAIL!");
        } catch (Exception e) {
            assertThat(e, instanceOf(ex));
        }
    }

    private void operate(double expected, String str) {
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

            if (BinaryOperation.isExist(String.valueOf(symbol))) {
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
                }

                for (UnaryOperation operation : listUnary) {
                    model.addUnaryOperator(operation);
                }
                listUnary.clear();
                continue;
            }

            throw new RuntimeException("UNEXPECTED symbol " + symbol);
        }

        assertEquals(expected, model.getDisplay().doubleValue(), DELTA);
    }

    private void operateOnNewObj(double expected, double value1, String operator, double value2) {
        model = new Model();
        operate(expected, value1, operator, value2);
    }

    private void operate(double expected, double value1, String operator, double value2) {
        model.addNumber(BigDecimal.valueOf(value1));
        model.addBinaryOperator(BinaryOperation.find(operator.charAt(0)));
        model.addNumber(BigDecimal.valueOf(value2));
        model.addBinaryOperator(BinaryOperation.EQUAL);

        assertEquals(expected, model.getDisplay().doubleValue(), DELTA);
    }
}