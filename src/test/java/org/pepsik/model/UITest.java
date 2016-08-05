package org.pepsik.model;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.pepsik.MainApp;
import org.pepsik.model.helper.JavaFXThreadingRule;
import org.pepsik.model.helper.UITestButton;
import org.pepsik.model.operation.BinaryOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static java.lang.Integer.MAX_VALUE;
import static org.junit.Assert.assertEquals;
import static org.pepsik.model.helper.UITestButton.*;

public class UITest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    private static Stage stage;
    private static Label display;
    private static Label history;

    @BeforeClass
    public static void initJFX() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        Thread t = new Thread(() -> {
            new JFXPanel();
            Platform.runLater(() -> {
                stage = new Stage();
                try {
                    new MainApp().start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            countDownLatch.countDown();
        });
        t.start();
        countDownLatch.await();

        Thread.sleep(5000);

        UITestButton.setScene(stage.getScene());
        display = (Label) stage.getScene().lookup("#display");
        history = (Label) stage.getScene().lookup("#history");
    }

    @AfterClass
    public static void close() {
        Platform.exit();
    }

    @Test
    public void InputTest() throws InterruptedException {
        NUMBER_1.fire();
        NUMBER_1.fire();
        NUMBER_1.fire();

        testOperation(0, "");
        testOperation(0, "0");
        testOperation(1, "1");
        testOperation(2, "2");
        testOperation(3, "3");
        testOperation(4, "4");
        testOperation(5, "5");
        testOperation(6, "6");
        testOperation(7, "7");
        testOperation(8, "8");
        testOperation(9, "9");
        testOperation(10, "10");
        testOperation(99, "99");
        testOperation(100, "100");
        testOperation(111, "111");
        testOperation(123, "12 3");
        testOperation(999, "999");
        testOperation(1000, "1000");
        testOperation(123456, "1 2 3 4 5 6");
        testOperation(1234567890, "1 2 3 4 5 6 7 8 9 0");
        testOperation(1234567890, "1 2 3 4 5 6 7 8 9 0");

        testOperation("1.0", "1.0");
        testOperation(9.9, "9.9");
        testOperation("1.00", "1.00");
        testOperation(1.11, "1.11");
        testOperation(11.1, "11.1");
        testOperation(12.3, "12.3");
        testOperation(9.99, "9.99");
        testOperation("10.00", "10.00");
        testOperation("123456.", "1 2 3 4 5 6.");
        testOperation("0.1234567890", ".1 2 3 4 5 6 7 8 9 0");

        testOperation("1111111111111111", "1111111111111111");

        testOperation("1111111111111111", "111111111111111111231231313");
        testOperation("9999999999999999", "99999999999999999999999999999");

        testOperation(0, "00");
        testOperation(0, "0 0 0 0 0 ");
        testOperation(1, "01");
        testOperation(2, "002");
        testOperation(3, "000000000000003");
        testOperation(4, "004");
        testOperation(5, "00005");
        testOperation(6, "06");
        testOperation(7, "0000000000000000000000000007");
        testOperation(8, "0008");
        testOperation(9, "00000000009");
        testOperation(10, "00000010");
        testOperation(12, "0000000012");
        testOperation(90, "0090");
        testOperation(120, "00000000120");
        testOperation(100120, "0000000000000000000100120");
        // -----------END INPUT TESTS---------

        // -----------SUM TESTS------------
        // -----------SUM 2 VALUES---------
        //each operation on new model obj
        testOperation(0, "0 + 0 =");
        testOperation(1, "1 + 0 =");
        testOperation(1, "0 + 1 =");
        testOperation(2, "2 + 0 =");
        testOperation(3, "3 + 0 =");
        testOperation(4, "4 + 0 =");
        testOperation(5, "5 + 0 =");
        testOperation(6, "6 + 0 =");
        testOperation(7, "7 + 0 =");
        testOperation(8, "8 + 0 =");
        testOperation(9, "9 + 0 =");
        testOperation(10, "10 + 0 =");
        testOperation(99, "99 + 0 =");
        testOperation(100, "100 + 0 =");
        testOperation(MAX_VALUE, "2147483647 + 0 =");

        testOperation(1, "0 + 1 =");
        testOperation(2, "0 + 2 =");
        testOperation(3, "0 + 3 =");
        testOperation(4, "0 + 4 =");
        testOperation(5, "0 + 5 =");
        testOperation(6, "0 + 6 =");
        testOperation(7, "0 + 7 =");
        testOperation(8, "0 + 8 =");
        testOperation(9, "0 + 9 =");
        testOperation(10, "0 + 10 =");
        testOperation(99, "0 + 99 =");
        testOperation(100, "0 + 100 =");
        testOperation(MAX_VALUE, "0 + 2147483647 =");

        testOperation(2, "1 + 1 =");
        testOperation(3, "2 + 1 =");
        testOperation(4, "3 + 1 =");
        testOperation(5, "4 + 1 =");
        testOperation(6, "5 + 1 =");
        testOperation(7, "6 + 1 =");
        testOperation(8, "7 + 1 =");
        testOperation(9, "8 + 1 =");
        testOperation(10, "9 + 1 =");
        testOperation(11, "10 + 1 =");

        testOperation(2, "1 + 1 =");
        testOperation(3, "1 + 2 =");
        testOperation(4, "1 + 3 =");
        testOperation(5, "1 + 4 =");
        testOperation(6, "1 + 5 =");
        testOperation(7, "1 + 6 =");
        testOperation(8, "1 + 7 =");
        testOperation(9, "1 + 8 =");
        testOperation(10, "1 + 9 =");
        testOperation(11, "1 + 10 =");


        testOperation(10, "7 + 3 =");
        testOperation(150, "50 + 100 =");
        testOperation(150, "100 + 50 =");
        testOperation(1048, "1024 + 24 =");
        testOperation(10000, "2500 + 7500 =");

        testOperation(MAX_VALUE - 1, MAX_VALUE / 2 + "+" + MAX_VALUE / 2 + "=");
        testOperation(MAX_VALUE, MAX_VALUE + "+" + 0 + "=");
        testOperation(MAX_VALUE, 0 + "+" + MAX_VALUE + "=");

        //each operation common model obj
        testOperation(0, "0 + 0 =");
        testOperation(1, "1 + 0 =");
        testOperation(99, "99 + 0 =");
        testOperation(1, "0 + 1 =");
        testOperation(99, "0 + 99 =");
        testOperation(2, "1 + 1 =");
        testOperation(4, "1 + 3 =");
        testOperation(10, "7 + 3 =");
        testOperation(150, "100 + 50 =");
        testOperation(150, "50 + 100 =");

        testOperation(2, "1 + 1 =");
        testOperation(3, "1 + 2 =");
        testOperation(4, "1 + 3 =");
        testOperation(5, "1 + 4 =");
        testOperation(6, "1 + 5 =");
        testOperation(7, "1 + 6 =");
        testOperation(8, "1 + 7 =");
        testOperation(9, "1 + 8 =");
        testOperation(10, "1 + 9 =");
        testOperation(11, "1 + 10 =");

        //test subtract 2 numbers
        testOperation(0, "0 - 0 =");
        testOperation(1, "1 - 0 =");
        testOperation(99, "99 - 0 =");
        testOperation(-1, "0 - 1 =");
        testOperation(-99, "0 - 99 =");
        testOperation(0, "1 - 1 =");
        testOperation(-2, " 1 - 3 =");
        testOperation(4, "7 -3 =");
        testOperation(50, "100 - 50 =");
        testOperation(-50, "50 - 100 =");

        testOperation(-20, "10 - 10 - 10 - 10 =");
        testOperation(-32, " - 10 - 10 - 10 -2 =");

        //test multiply 2 numbers
        testOperation(0, "0 * 0 =");
        testOperation(0, "1 * 0 =");
        testOperation(0, "99 * 0 =");
        testOperation(0, "0 * 1 =");
        testOperation(0, "0 * 99 =");
        testOperation(1, "1 * 1 =");
        testOperation(3, "1 * 3 =");
        testOperation(21, "7 * 3 =");
        testOperation(5000, "100 * 50 =");
        testOperation(5000, "50 * 100 =");

        //test divide 2 numbers
        testOperation(0, "0 / 1 =");
        testOperation(0, "0 / 99 =");
        testOperation(1, "1 / 1 =");
        testOperation("0.3333333333333333", "1 / 3 =");
        testOperation("2.3333333333333333", " 7 / 3 =");
        testOperation(2, "100 / 50 =");
        testOperation(0.5, "50 / 100 =");
        // ----------- END OPERATION WITH 2 VALUES ---------

        testOperation(0, "+=");
        testOperation(0, "-=");
        testOperation(0, "*=");

        testOperation(0, "+=");
        testOperation(0, "-=");
        testOperation(0, "*=");
        testOperation(0, "+=");
        testOperation(0, "-=");
        testOperation(0, "*=");

        //equals
        testOperation(0, "0 =");
        testOperation(1, "1 =");
        testOperation(5, "5 =");
        testOperation(999, "999 =");
        testOperation(MAX_VALUE, MAX_VALUE + "=");

        testOperation(-1, "negate(1)=");
        testOperation(-5, "negate(5)=");
        testOperation(-50, "negate(50)=");
        testOperation(-MAX_VALUE, -MAX_VALUE + "=");

        testOperation(0, "0 =");
        testOperation(0, "0 = = =");
        testOperation(0, "0 = = = = = = = =");
        testOperation(1, "1 =");
        testOperation(1, "1 = = = = = = = = = =");
        testOperation(5, "5 =");
        testOperation(5, "5 = = = = =");
        testOperation(999, "999 =");
        testOperation(999, "999 = = = =");
        testOperation(MAX_VALUE, MAX_VALUE + "=");
        testOperation(MAX_VALUE, MAX_VALUE + "= = = = =");

        testOperation(0, "0 =");
        testOperation(0, "0 = = =");
        testOperation(0, "0 = = = = = =");
        testOperation(1, "1 =");
        testOperation(1, "1 = = = = = = =");
        testOperation(5, "5 =");
        testOperation(5, "5 = = = = =");
        testOperation(999, "999 =");
        testOperation(999, "999 = = = =");
        testOperation(MAX_VALUE, MAX_VALUE + "=");
        testOperation(MAX_VALUE, MAX_VALUE + "= = = =");

        //equals and subtract combo
        testOperation(1, "1  = +");
        testOperation(2, "1 = + =");
        testOperation(4, "1 = + = + =");
        testOperation(8, "1 = + = + = + =");
        testOperation(16, "1 = + = + = + = + =");

        testOperation(-1, "negate(1) = +");
        testOperation(-2, "negate(1) = + =");
        testOperation(-4, "negate(1) =+=+=");
        testOperation(-8, "negate(1) =+=+=+=");
        testOperation(-16, "negate(1) =+=+=+=+=");

        testOperation(1, "1 =-");
        testOperation(0, "1 =-=");
        testOperation(0, "1 =-=-=");
        testOperation(0, "1 =-=-=-=-=");

        testOperation(-1, "negate(1) =-");
        testOperation(0, "negate(1) =-=");
        testOperation(0, "1 =-=-=");
        testOperation(0, "1 =-=-=-=-=");
        testOperation(10, "10 =-");
        testOperation(0, "10 =-=");
        testOperation(0, "10 =-=-=");
        testOperation(0, "10 =-=-=-=-=");

        testOperation(15, "5 +==");
        testOperation(25, "5 +====");
        testOperation(45, "5 +========");
        testOperation(70, "5 +======+=");

        testOperation(15, "5 +==");
        testOperation(25, "5 +====");
        testOperation(45, "5 +========");
        testOperation(0, "5 +======-=");

        //common model
        testOperation(1, "1 = +");
        testOperationOnCommonModel(4, "1 =+=");
        testOperationOnCommonModel(12, "1 =+=+=");
        testOperationOnCommonModel(56, "1 =+=+=+=");
        testOperationOnCommonModel(29, "1 = +");
        testOperationOnCommonModel(28, "negate(1) = +");
        testOperationOnCommonModel(54, "negate(1) =+=");

        testOperation(-1, "negate(1) = +");
        testOperationOnCommonModel(-4, "negate(1) =+=");
        testOperationOnCommonModel(-12, "negate(1) =+=+=");
        testOperationOnCommonModel(-56, "negate(1) =+=+=+=");
        testOperationOnCommonModel(-464, "negate(1) =+=+=+=+=");

        testOperation(1, "1 =-");
        testOperation(0, "1 =-=");
        testOperation(0, "1 =-=-=");
        testOperation(0, "1 =-=-=-=-=");

        testOperation(-1, "negate(1) =-");
        testOperation(0, "negate(1) =-=");
        testOperation(0, "1 =-=-=");
        testOperation(0, "1 =-=-=-=-=");

        testOperation(10, "10 =-");
        testOperation(0, "10 =-=");
        testOperation(0, "10 =-=-=");
        testOperation(0, "10 =-=-=-=-=");

        testOperation(15, "5 +==");
        testOperation(25, "5 +====");
        testOperation(45, "5 +========");
        testOperation(70, "5 +======+=");

        testOperation(15, "5 +==");
        testOperation(25, "5 +====");
        testOperation(45, "5+========");
        testOperation(0, "5+======-=");

        testOperation(10, "5 ++=");
        testOperation(68, "34 ++++++=");
        testOperation(-16, "negate(8)++++++++=");

        testOperationOnCommonModel(8, "3+5=");
        testOperationOnCommonModel(8, "3+5=");
        testOperationOnCommonModel(100, "31+5/2/9+1*11+67=");
        testOperationOnCommonModel(167, "=");
        testOperationOnCommonModel(301, "= =");
        testOperationOnCommonModel(222, "= = - 11 /2 + 5 = =");
        testOperationOnCommonModel(0, "+ 1 = + = + = - =");
        testOperationOnCommonModel(-4, "- 1 = + = + =");
        testOperationOnCommonModel("-1.3333333333333333", "/3+");
        testOperationOnCommonModel(-4, "*3=");

        testOperation(11, "*3++++++++++11=");
        testOperationOnCommonModel(44, "*3++++++++++11=");

        testOperation(9.5, "+3/2+1*3+2=");
        testOperationOnCommonModel(4, "2=");

        //---------------TEST UNARY OPERATORS------------
        // ----- NEGATE -----
        testOperation(0, "negate(0) = ");
        testOperation(0, "negate(negate(0) = ");
        testOperation(0, "negate(negate(negate(negate(0) = ");
        testOperation(-1, "negate(1) = ");
        testOperation(1, "negate(negate(1) = ");
        testOperation(-1, "negate(negate(negate(1) = ");
        testOperation(-9, "negate(9) = ");
        testOperation(9, "negate(negate(9) = ");
        testOperation(0, "negate() =");

        testOperation(0, "negate()");

        testOperation(0, "negate() = ");
        testOperation(0, "negate(negate() = ");
        testOperation(0, "negate(negate() = ");
        testOperation(0, "negate(negate() = ");
        testOperation(0, "negate(negate(negate(negate() = ");

        testOperation(-9, "negate(9) = ");
        testOperation(0, "negate() + negate() = ");
        testOperation(0, "negate(negate() + negate() = ");
        testOperation(0, "negate(negate() + negate(negate(negate() = ");
        testOperation(0, "negate(negate() + negate(negate(negate() = ");
        testOperation(-7, "negate(9) + 2 = ");
        testOperation(11, "negate(negate(9) + 2 = ");

        testOperation(-1, "negate(1) = ");
        testOperation(-9, "negate(9) =");
        testOperation(-4, "negate(1) + negate(3) = ");
        testOperation(9, "negate(negate(11) + negate(2) = ");
        testOperation(0, "negate(negate(4) + negate(negate(negate(4) = ");
        testOperation(0, "negate(negate(2) + negate(negate(negate(2) = ");
        testOperation(-7, "negate(9) + 2 =");
        // -----END NEGATE -----

        //---- SQRT ---
        testOperation(3, "sqrt(9) = ");
        testOperation(9, "sqrt(81) = ");
        testOperation(3, "sqrt(sqrt(81) = ");
        testOperation(9, "sqrt(81) = ");

        testOperation(3, "sqrt(sqrt(81) + negate(0) = ");
        testOperation(3, "sqrt(sqrt(81) + 0 = ");
        testOperation(4, "sqrt(sqrt(81) + 1 = ");
        testOperation(5, "sqrt(sqrt(81) + 2 = ");
        testOperation(7.5, "sqrt(sqrt(81) + 2 + 10/2 = ");
        testOperation(10, "sqrt(sqrt(81) + 2*2 = ");

        testOperation(3, "sqrt(sqrt(81) - 0 = ");
        testOperation(2, "sqrt(sqrt(81) - 1 = ");
        testOperation(2, "sqrt(sqrt(81) - 2*2 = ");
        testOperation(1, "sqrt(sqrt(81) - 2 = ");
        testOperation(0, "sqrt(sqrt(81) - 3 = ");
        testOperation(-1, "sqrt(sqrt(81) - 4 = ");
        testOperation(-4.5, "sqrt(sqrt(81) - 2 - 10/2 = ");

        testOperation(-6, "sqrt(sqrt(81) * negate(2) = ");
        testOperation(-3, "sqrt(sqrt(81) * negate(1) = ");
        testOperation(0, "sqrt(sqrt(81) * 0 = ");
        testOperation(3, "sqrt(sqrt(81) * 1 = ");
        testOperation(6, "sqrt(sqrt(81) * 2 = ");
        testOperation(12, "sqrt(sqrt(81) * 2*2 = ");

        testOperation(1.5, "sqrt(sqrt(81) / 2 = ");
        testOperation(3, "sqrt(sqrt(81) / 2*2 = ");

        testOperation(5, "sqrt(sqrt(81) + sqrt(4) = ");
        testOperation(5, " + sqrt(sqrt(81) + sqrt(4) = ");
        //---- END SQRT ---

        //-----SQUARE -----
        testOperation(9, "square(3) = ");
        testOperation(81, "square(square(3) = ");
        testOperation(83, "square(square(3) + 2 = ");
        testOperation(79, "square(square(3) - 2 = ");

        testOperation(0, "square(0) = ");
        testOperation(0, "square(square(0) = ");
        testOperation(0, "square(square(square(square(0) = ");
        testOperation(1, "square(1) = ");
        testOperation(1, "square(square(1) = ");
        testOperation(1, "square(square(square(1) = ");
        testOperation(81, "square(9) = ");
        testOperation(6561, "square(square(9) = ");

        testOperation(0, "square() = square()");
        testOperation(0, "square(square() = square()");
        testOperation(0, "square(square() = square(square()");
        testOperation(0, "square(square() = square(square(square()");
        testOperation(0, "square(square(square(square() = square(square(square()");

        testOperation(81, "square(9) = ");
        testOperation(4, "square(2) = ");
        testOperation(0, "square() + square() = ");
        testOperation(0, "square(square() + square() = ");
        testOperation(0, "square(square() + square(square(square() =");
        testOperation(0, "square(square() + square(square(square() = ");
        testOperation(6, "square(2) + 2 = ");
        testOperation(3, "square(1) + 2 = ");
        testOperation(2, "square(square(0) + 2 = ");
        testOperation(18, "square(square(2) + 2 = ");
        //--------- END SQUARE --------

        testOperation(1.7320508075688772, "sqrt(3) = ");
        testOperationOnCommonModel(3, "square() = ");

        //--------- FRACTION --------
        testOperation(0.1, "fraction(10) = ");
        testOperation(0.02, "fraction(50) = ");
        testOperation(2, "fraction(fraction(2) = ");
        testOperation(0.02, "fraction(50) = ");
        testOperation(0.02, "fraction(50) = ");

        testOperation(-0.1, "fraction(negate(10) = ");
        testOperation(-0.02, "fraction(negate(50) = ");
        testOperation(-2, "fraction(fraction(negate(2) = ");
        testOperation(-0.02, "fraction(negate(50) = ");

        testOperation(1, "fraction(fraction(1) + negate(0) = ");
        testOperation(3, "fraction(fraction(2) + 1 = ");
        testOperation("2.3333333333333333", "fraction(3) + 2 = ");
        testOperation(6.05, "fraction(10) + 2 + 10/2 = ");
        testOperation(4.5, "fraction(4) + 2*2 = ");

        testOperation(1, "fraction(fraction(1) - 0 = ");
        testOperation(0, "fraction(fraction(1) - 1 = ");
        testOperation(-2, "fraction(fraction(1) - 2*2 = ");
        testOperation(-1, "fraction(fraction(1) - 2 = ");
        testOperation(-2, "fraction(fraction(1) - 3 = ");
        testOperation(-3, "fraction(fraction(1) - 4 = ");
        testOperation(-5.5, "fraction(fraction(1) - 2 - 10/2 = ");

        testOperation(-0.4, "fraction(fraction(fraction(5) * negate(2) = ");
        testOperation(-0.05, "fraction(fraction(fraction(20) * negate(1) = ");
        testOperation(0, "fraction(fraction(fraction(100) * 0 = ");
        testOperation(0.01, "fraction(fraction(fraction(100) * 1 = ");
        testOperation("0.6666666666666667", "fraction(fraction(fraction(3) * 2 = ");
        testOperation("1.3333333333333334", "fraction(fraction(fraction(3) * 2*2 = ");

        testOperation(0.0151515151515152, "fraction(33) / 2 = ");
        testOperation(0.0303030303030303, "   fraction(33) / 2*2 = ");

        testOperation(1.5, "fraction(fraction(1) + fraction(2) = ");
        testOperation(-1.5, " + negate(fraction(fraction(1) + negate(fraction(2) = ");
        testOperation(10, "sqrt(sqrt(81) + 2*2 = ");
        //--------- END FRACTION --------

        //BIG EXPRESSION
        testOperation(-12, "12 + 24 - sqrt(9) - square(3) /2 * negate(1) = ");
        testOperation(-72, "12 * 12 - sqrt(81) + square(3) /2 * negate(1) = ");
        testOperation(111.9, "12 + 1 + 43 * 2 - fraction(10) = ");

        //Test font Resize
        stage.setWidth(1000);
        stage.setHeight(1000);

        testResizeButton("18px", UITestButton.NUMBER_1);
        testResizeButton("18px", UITestButton.NUMBER_2);
        testResizeButton("18px", UITestButton.NUMBER_3);
        testResizeButton("18px", UITestButton.NUMBER_4);
        testResizeButton("18px", UITestButton.NUMBER_5);
        testResizeButton("18px", UITestButton.NUMBER_6);
        testResizeButton("18px", UITestButton.NUMBER_7);
        testResizeButton("18px", UITestButton.NUMBER_8);
        testResizeButton("18px", UITestButton.NUMBER_9);
        testResizeButton("18px", UITestButton.NUMBER_0);

        testResizeButton("16px", UITestButton.ADD);
        testResizeButton("16px", UITestButton.SUBTRACT);
        testResizeButton("16px", UITestButton.MULTIPLY);
        testResizeButton("16px", UITestButton.DIVIDE);

        testResizeButton("14px", UITestButton.SQUARE);
        testResizeButton("14px", UITestButton.SQUARE_ROOT);
        testResizeButton("14px", UITestButton.FRACTION);
        testResizeButton("14px", UITestButton.PERCENT);
        testResizeButton("16px", UITestButton.NEGATE);

        testResizeButton("16px", UITestButton.EQUAL);
        testResizeButton("18px", UITestButton.POINT);

        testResizeButton("14px", UITestButton.CLEAR_ALL);
        testResizeButton("14px", UITestButton.CLEAR_ENTRY);

        testResizeButton("14px", UITestButton.BACKSPACE);

        testResizeButton("12px", UITestButton.MEMORY_ADD);
        testResizeButton("12px", UITestButton.MEMORY_SUBTRACT);
        testResizeButton("12px", UITestButton.MEMORY_CLEAR);
        testResizeButton("12px", UITestButton.MEMORY_RECALL);
        testResizeButton("12px", UITestButton.MEMORY_SAVE);

        //Test display resize


        //Test History
        testHistory("5 + 1 -");
        testHistory("4 - 1 /");
        testHistory("3 * square(3)");
        testHistory("2 / 4 +");
        testHistory("1 + 5 -");
        testHistory("0 - 6 *");

        testHistory("5 + 6 + square(7)");
        testHistory("124 - 6 / sqrt(7)");
        testHistory("3 + negate(233) + fraction(7)");
        testHistory("1 * negate(6) + fraction(71)");
        testHistory("0 + negate(1231) / negate(negate(6) + sqrt(square(71)");

        //Memory
        CLEAR_ALL.fire();
        parseAndExecute("5 + 12");
        MEMORY_SAVE.fire();
        MEMORY_ADD.fire();
        parseAndExecute("=");
        MEMORY_RECALL.fire();
        testOperationOnCommonModel(12, " - 12 =");

        CLEAR_ALL.fire();
        parseAndExecute("12*3=");
        MEMORY_SAVE.fire();
        MEMORY_ADD.fire();
        MEMORY_ADD.fire();
        MEMORY_ADD.fire();
        MEMORY_RECALL.fire();
        testOperationOnCommonModel(12, "/12=");

        //Backspace
        testBackspace("1234", "1234567", 3);
        testBackspace("0", "1234567", 7);
        testBackspace("0", "1234567", 12);
        testBackspace("53", "12 + 538 ", 1);
        testBackspace("1", "12 * 2 - 111 ", 2);
        testBackspace("0", "12 * 2 - 111 + 123 ", 3);
    }

    private void testOperation(String expected, String input) {
        CLEAR_ALL.fire();
        operate(expected, input);
    }

    private void testOperation(double expected, String input) {
        CLEAR_ALL.fire();
        operate(String.valueOf(expected), input);
    }

    private void testOperation(int expected, String input) {
        CLEAR_ALL.fire();
        operate(String.valueOf(expected), input);
    }

    private void testOperationOnCommonModel(double expected, String input) {
        operate(String.valueOf(expected), input);
    }

    private void testOperationOnCommonModel(String expected, String input) {
        operate(expected, input);
    }

    private void testOperationOnCommonModel(int expected, String input) {
        operate(String.valueOf(expected), input);
    }


    private void operate(String expected, String input) {
        parseAndExecute(input);

        assertEquals(expected, display.getText());
    }

    private void parseAndExecute(String input) {
        String data = input.replaceAll("\\s+", "");

        StringBuilder sb = new StringBuilder();
        List<String> listUnary = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            String symbol = data.substring(i, i + 1);

            if (StringUtils.isNumeric(symbol)) {
                UITestButton.getButton(symbol).fire();
                continue;
            }

            if (StringUtils.isAlpha(symbol)) {
                sb.append(symbol);
                continue;
            }

            if (symbol.equals(".")) {
                UITestButton.getButton(symbol).fire();
                continue;
            }

            if (BinaryOperation.isExist(symbol)) {
                UITestButton.getButton(symbol).fire();
                continue;
            }

            if (symbol.equals("(")) {
                listUnary.add(UITestButton.isExist(sb.toString()));
                sb = new StringBuilder();
                continue;
            }

            if (symbol.equals(")")) {
                for (String operation : listUnary) {
                    UITestButton.valueOf(operation.toUpperCase()).fire();
                }

                listUnary.clear();
                continue;
            }

            throw new IllegalStateException("FAIL" + symbol);
        }
    }

    private void testResizeButton(String expected, UITestButton button) throws InterruptedException {

        assertEquals("-fx-font: " + expected + " arial;", button.getButton().getStyle());
    }

    private void testHistory(String expected) {
        CLEAR_ALL.fire();
        parseAndExecute(expected);

        assertEquals(expected, history.getText());
    }

    private void testBackspace(String expected, String expression, int coutBacksplace) {
        CLEAR_ALL.fire();
        parseAndExecute(expression);

        for (int i = 0; i < coutBacksplace; i++) {
            BACKSPACE.fire();
        }

        assertEquals(expected, display.getText());
    }

}