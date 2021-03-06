package org.pepsik.model;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pepsik.MainApp;
import org.pepsik.controller.button.CalculatorButton;
import org.pepsik.model.helper.UITestButton;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.compare;
import static java.lang.Integer.MAX_VALUE;
import static javafx.scene.input.KeyCode.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.pepsik.model.helper.UITestButton.*;
import static org.pepsik.controller.UIChanger.BOUNDARY_HEIGHT;
import static org.pepsik.controller.UIChanger.BOUNDARY_WIDTH;
import static org.pepsik.controller.UIChanger.MULTIPLIER;

public class UITest {

    /**
     * Stage for init tests
     */
    private static Stage stage;
    /**
     * Represents calculator display
     */
    private static Label display;
    /**
     * Represents calculator history
     */
    private static Label history;

    /**
     * Used for format expected double SCALE in asserts
     */
    private NumberFormat formatter = new DecimalFormat("###,###.################");
    private int maxDisplayFont;
    private int minDisplayFont;

    @BeforeClass
    public static void initJFX() throws InterruptedException {
        Object sync = new Object();

        new JFXPanel();

        synchronized (sync) {
            Platform.runLater(() -> {
                synchronized (sync) {
                    stage = new Stage();
                    try {
                        new MainApp().start(stage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Scene scene = stage.getScene();
                    //setup calculator ui test button
                    UITestButton.setUIButtons();
                    display = (Label) scene.lookup("#display");
                    history = (Label) scene.lookup("#history");
                    sync.notify();
                }
            });

            sync.wait();
        }
    }

    @AfterClass
    public static void close() {
        Platform.exit();
    }

    @Test
    public void testNumberButton() throws InterruptedException {
        assertExpression(0, "");
        assertExpression(0, "0");
        assertExpression(1, "1");
        assertExpression(2, "2");
        assertExpression(3, "3");
        assertExpression(4, "4");
        assertExpression(5, "5");
        assertExpression(6, "6");
        assertExpression(7, "7");
        assertExpression(8, "8");
        assertExpression(9, "9");
        assertExpression(10, "10");
        assertExpression(99, "99");
        assertExpression(100, "100");
        assertExpression(111, "111");
        assertExpression(123, "12 3");
        assertExpression(999, "999");
        assertExpression(1000, "1000");
        assertExpression(123456, "1 2 3 4 5 6");
        assertExpression(123456, "1 2 3 4 5 6");
        assertExpression("12,345.6", "1 2 3 4 5. 6");
        assertExpression(1234567890, "1 2 3 4 5 6 7 8 9 0");
        assertExpression(1234567890, "1 2 3 4 5 6 7 8 9 0");

        assertExpression("1.0", "1.0");
        assertExpression(9.9, "9.9");
        assertExpression("1.00", "1.00");
        assertExpression("1.00", "1..00");
        assertExpression("1.00", "1.........00");
        assertExpression("1.00", "1.........0...0");
        assertExpression("1.00", "1.........0...0....");
        assertExpression(1.11, "1.11");
        assertExpression(11.1, "11.1");
        assertExpression(12.3, "12.3");
        assertExpression(9.99, "9.99");
        assertExpression("10.00", "10.00");
        assertExpression("123,456.", "1 2 3 4 5 6.");
        assertExpression("0.12", ".1 2");
        assertExpression("0.1234567890", ".1 2 3 4 5 6 7 8 9 0");
        assertExpression("0.0000000000000000", "0.000000000000000000100120");
        assertExpression("123,456,789,012,345.1", "123456789012345.123456");

        assertBackspace("0.0000000000000001", "0.0000000000000001");

        assertExpression("1,111,111,111,111,111", "1111111111111111");

        assertExpression("1,111,111,111,111,111", "111111111111111111231231313");
        assertExpression("9,999,999,999,999,999", "99999999999999999999999999999");

        assertExpression(0, "00");
        assertExpression(0, "0 0 0 0 0 ");
        assertExpression(1, "01");
        assertExpression(2, "002");
        assertExpression(3, "000000000000003");
        assertExpression(4, "004");
        assertExpression(5, "00005");
        assertExpression(6, "06");
        assertExpression(7, "0000000000000000000000000007");
        assertExpression(8, "0008");
        assertExpression(9, "00000000009");
        assertExpression(10, "00000010");
        assertExpression(12, "0000000012");
        assertExpression(90, "0090");
        assertExpression(120, "00000000120");
        assertExpression(100120, "0000000000000000000100120");
    }

    @Test
    public void testRandomOperationCombination() {
        assertExpression(0, "+=");
        assertExpression(0, "-=");
        assertExpression(0, "*=");

        assertExpression(0, "+=");
        assertExpression(0, "-=");
        assertExpression(0, "*=");
        assertExpression(0, "+=");
        assertExpression(0, "-=");
        assertExpression(0, "*=");

        //equals
        assertExpression(0, "0 =");
        assertExpression(1, "1 =");
        assertExpression(5, "5 =");
        assertExpression(999, "999 =");
        assertExpression(MAX_VALUE, MAX_VALUE + "=");

        assertExpression(-1, "negate(1)=");
        assertExpression(-5, "negate(5)=");
        assertExpression(-50, "negate(50)=");
        assertExpression(-MAX_VALUE, -MAX_VALUE + "=");

        assertExpression(0, "0 =");
        assertExpression(0, "0 = = =");
        assertExpression(0, "0 = = = = = = = =");
        assertExpression(1, "1 =");
        assertExpression(1, "1 = = = = = = = = = =");
        assertExpression(5, "5 =");
        assertExpression(5, "5 = = = = =");
        assertExpression(999, "999 =");
        assertExpression(999, "999 = = = =");
        assertExpression(MAX_VALUE, MAX_VALUE + "=");
        assertExpression(MAX_VALUE, MAX_VALUE + "= = = = =");

        assertExpression(0, "0 =");
        assertExpression(0, "0 = = =");
        assertExpression(0, "0 = = = = = =");
        assertExpression(1, "1 =");
        assertExpression(1, "1 = = = = = = =");
        assertExpression(5, "5 =");
        assertExpression(5, "5 = = = = =");
        assertExpression(999, "999 =");
        assertExpression(999, "999 = = = =");
        assertExpression(MAX_VALUE, MAX_VALUE + "=");
        assertExpression(MAX_VALUE, MAX_VALUE + "= = = =");

        //equals and subtract combo
        assertExpression(1, "1  = +");
        assertExpression(2, "1 = + =");
        assertExpression(4, "1 = + = + =");
        assertExpression(8, "1 = + = + = + =");
        assertExpression(16, "1 = + = + = + = + =");

        assertExpression(-1, "negate(1) = +");
        assertExpression(-2, "negate(1) = + =");
        assertExpression(-4, "negate(1) =+=+=");
        assertExpression(-8, "negate(1) =+=+=+=");
        assertExpression(-16, "negate(1) =+=+=+=+=");

        assertExpression(1, "1 =-");
        assertExpression(0, "1 =-=");
        assertExpression(0, "1 =-=-=");
        assertExpression(0, "1 =-=-=-=-=");

        assertExpression(-1, "negate(1) =-");
        assertExpression(0, "negate(1) =-=");
        assertExpression(0, "1 =-=-=");
        assertExpression(0, "1 =-=-=-=-=");
        assertExpression(10, "10 =-");
        assertExpression(0, "10 =-=");
        assertExpression(0, "10 =-=-=");
        assertExpression(0, "10 =-=-=-=-=");

        assertExpression(15, "5 +==");
        assertExpression(25, "5 +====");
        assertExpression(45, "5 +========");
        assertExpression(70, "5 +======+=");

        assertExpression(15, "5 +==");
        assertExpression(25, "5 +====");
        assertExpression(45, "5 +========");
        assertExpression(0, "5 +======-=");

        //common model
        assertExpression(1, "1 = +");
        assertExpressionWithoutClear(4, "1 =+=");
        assertExpressionWithoutClear(12, "1 =+=+=");
        assertExpressionWithoutClear(56, "1 =+=+=+=");
        assertExpressionWithoutClear(29, "1 = +");
        assertExpressionWithoutClear(28, "negate(1) = +");
        assertExpressionWithoutClear(54, "negate(1) =+=");

        assertExpression(-1, "negate(1) = +");
        assertExpressionWithoutClear(-4, "negate(1) =+=");
        assertExpressionWithoutClear(-12, "negate(1) =+=+=");
        assertExpressionWithoutClear(-56, "negate(1) =+=+=+=");
        assertExpressionWithoutClear(-464, "negate(1) =+=+=+=+=");

        assertExpression(1, "1 =-");
        assertExpression(0, "1 =-=");
        assertExpression(0, "1 =-=-=");
        assertExpression(0, "1 =-=-=-=-=");

        assertExpression(-1, "negate(1) =-");
        assertExpression(0, "negate(1) =-=");
        assertExpression(0, "1 =-=-=");
        assertExpression(0, "1 =-=-=-=-=");

        assertExpression(10, "10 =-");
        assertExpression(0, "10 =-=");
        assertExpression(0, "10 =-=-=");
        assertExpression(0, "10 =-=-=-=-=");

        assertExpression(15, "5 +==");
        assertExpression(25, "5 +====");
        assertExpression(45, "5 +========");
        assertExpression(70, "5 +======+=");

        assertExpression(15, "5 +==");
        assertExpression(25, "5 +====");
        assertExpression(45, "5+========");
        assertExpression(0, "5+======-=");

        assertExpression(10, "5 ++=");
        assertExpression(68, "34 ++++++=");
        assertExpression(-16, "negate(8)++++++++=");

        assertExpressionWithoutClear(8, "3+5=");
        assertExpressionWithoutClear(8, "3+5=");
        assertExpressionWithoutClear(100, "31+5/2/9+1*11+67=");
        assertExpressionWithoutClear(167, "=");
        assertExpressionWithoutClear(301, "= =");
        assertExpressionWithoutClear(222, "= = - 11 /2 + 5 = =");
        assertExpressionWithoutClear(0, "+ 1 = + = + = - =");
        assertExpressionWithoutClear(-4, "- 1 = + = + =");
        assertExpressionWithoutClear("-1.3333333333333333", "/3+");
        assertExpressionWithoutClear(-4, "*3=");

        assertExpression(11, "*3++++++++++11=");
        assertExpressionWithoutClear(44, "*3++++++++++11=");
    }

    @Test
    public void testExpression() {
        // -----------SUM 2 VALUES---------
        //each operation on new model obj
        assertExpression(0, "0 + 0 =");
        assertExpression(1, "1 + 0 =");
        assertExpression(1, "0 + 1 =");
        assertExpression(2, "2 + 0 =");
        assertExpression(3, "3 + 0 =");
        assertExpression(4, "4 + 0 =");
        assertExpression(5, "5 + 0 =");
        assertExpression(6, "6 + 0 =");
        assertExpression(7, "7 + 0 =");
        assertExpression(8, "8 + 0 =");
        assertExpression(9, "9 + 0 =");
        assertExpression(10, "10 + 0 =");
        assertExpression(99, "99 + 0 =");
        assertExpression(100, "100 + 0 =");
        assertExpression(MAX_VALUE, "2147483647 + 0 =");

        assertExpression(1, "0 + 1 =");
        assertExpression(2, "0 + 2 =");
        assertExpression(3, "0 + 3 =");
        assertExpression(4, "0 + 4 =");
        assertExpression(5, "0 + 5 =");
        assertExpression(6, "0 + 6 =");
        assertExpression(7, "0 + 7 =");
        assertExpression(8, "0 + 8 =");
        assertExpression(9, "0 + 9 =");
        assertExpression(10, "0 + 10 =");
        assertExpression(99, "0 + 99 =");
        assertExpression(100, "0 + 100 =");
        assertExpression(MAX_VALUE, "0 + 2147483647 =");

        assertExpression(2, "1 + 1 =");
        assertExpression(3, "2 + 1 =");
        assertExpression(4, "3 + 1 =");
        assertExpression(5, "4 + 1 =");
        assertExpression(6, "5 + 1 =");
        assertExpression(7, "6 + 1 =");
        assertExpression(8, "7 + 1 =");
        assertExpression(9, "8 + 1 =");
        assertExpression(10, "9 + 1 =");
        assertExpression(11, "10 + 1 =");

        assertExpression(2, "1 + 1 =");
        assertExpression(3, "1 + 2 =");
        assertExpression(4, "1 + 3 =");
        assertExpression(5, "1 + 4 =");
        assertExpression(6, "1 + 5 =");
        assertExpression(7, "1 + 6 =");
        assertExpression(8, "1 + 7 =");
        assertExpression(9, "1 + 8 =");
        assertExpression(10, "1 + 9 =");
        assertExpression(11, "1 + 10 =");


        assertExpression(10, "7 + 3 =");
        assertExpression(150, "50 + 100 =");
        assertExpression(150, "100 + 50 =");
        assertExpression(1048, "1024 + 24 =");
        assertExpression(10000, "2500 + 7500 =");

        assertExpression(MAX_VALUE - 1, MAX_VALUE / 2 + "+" + MAX_VALUE / 2 + "=");
        assertExpression(MAX_VALUE, MAX_VALUE + "+" + 0 + "=");
        assertExpression(MAX_VALUE, 0 + "+" + MAX_VALUE + "=");

        //each operation common model obj
        assertExpression(0, "0 + 0 =");
        assertExpression(1, "1 + 0 =");
        assertExpression(99, "99 + 0 =");
        assertExpression(1, "0 + 1 =");
        assertExpression(99, "0 + 99 =");
        assertExpression(2, "1 + 1 =");
        assertExpression(4, "1 + 3 =");
        assertExpression(10, "7 + 3 =");
        assertExpression(150, "100 + 50 =");
        assertExpression(150, "50 + 100 =");

        assertExpression(2, "1 + 1 =");
        assertExpression(3, "1 + 2 =");
        assertExpression(4, "1 + 3 =");
        assertExpression(5, "1 + 4 =");
        assertExpression(6, "1 + 5 =");
        assertExpression(7, "1 + 6 =");
        assertExpression(8, "1 + 7 =");
        assertExpression(9, "1 + 8 =");
        assertExpression(10, "1 + 9 =");
        assertExpression(11, "1 + 10 =");

        //test subtract 2 numbers
        assertExpression(0, "0 - 0 =");
        assertExpression(1, "1 - 0 =");
        assertExpression(99, "99 - 0 =");
        assertExpression(-1, "0 - 1 =");
        assertExpression(-99, "0 - 99 =");
        assertExpression(0, "1 - 1 =");
        assertExpression(-2, " 1 - 3 =");
        assertExpression(4, "7 -3 =");
        assertExpression(50, "100 - 50 =");
        assertExpression(-50, "50 - 100 =");

        assertExpression(-20, "10 - 10 - 10 - 10 =");
        assertExpression(-32, " - 10 - 10 - 10 -2 =");

        //test multiply 2 numbers
        assertExpression(0, "0 * 0 =");
        assertExpression(0, "1 * 0 =");
        assertExpression(0, "99 * 0 =");
        assertExpression(0, "0 * 1 =");
        assertExpression(0, "0 * 99 =");
        assertExpression(1, "1 * 1 =");
        assertExpression(3, "1 * 3 =");
        assertExpression(21, "7 * 3 =");
        assertExpression(5000, "100 * 50 =");
        assertExpression(5000, "50 * 100 =");

        //test divide 2 numbers
        assertExpression(0, "0 / 1 =");
        assertExpression(0, "0 / 99 =");
        assertExpression(1, "1 / 1 =");
        assertExpression("0.3333333333333333", "1 / 3 =");
        assertExpression("2.3333333333333333", " 7 / 3 =");
        assertExpression(2, "100 / 50 =");
        assertExpression(0.5, "50 / 100 =");
        // ----------- END OPERATION WITH 2 VALUES ---------

        //BIG EXPRESSION
        assertExpression(-12, "12 + 24 - sqrt(9) - square(3) /2 * negate(1) = ");
        assertExpression(-72, "12 * 12 - sqrt(81) + square(3) /2 * negate(1) = ");
        assertExpression(111.9, "12 + 1 + 43 * 2 - fraction(10) = ");

        //Random
        assertExpression(9.5, "+3/2+1*3+2=");
        assertExpressionWithoutClear(4, "2=");

        assertExpression("1.7320508075688773", "sqrt(3) = ");
        assertExpressionWithoutClear(3, "square() = ");

        assertExpression("0.000099", "99/1000000=");


        assertExpression("-1", "10 - square(5) 11 =");
        assertExpression("-10", "10 - square(0) 20 =");

        assertExpression("0", "10 - sqrt(5) 10 =");
        assertExpression("-10", "10 - sqrt(0) 20 =");

        assertExpression("-10", "10 - negate(0) 20 =");
        assertExpression("0", "10 - negate(5) 10 =");

        assertExpression("0", "10 - fraction(5) 10 =");
        assertExpression("Cannot divide by zero", "10 - fraction(0) 20 =");

        assertExpression("-10", "10 - percent(50) 20 =");
        assertExpression("-10", "10 - percent(0) 20 =");
    }

    @Test
    public void testDivideByZeroOperation() {
        assertDivideByZeroErrorMessage("0/0 =");
        assertDivideByZeroErrorMessage("/0=");
        assertDivideByZeroErrorMessage("99/0=");
        assertDivideByZeroErrorMessage("fraction(negate(0) = ");
        assertDivideByZeroErrorMessage("/=");
        assertDivideByZeroErrorMessage(MAX_VALUE + "/0=");
        assertDivideByZeroErrorMessage(MAX_VALUE + "/0=");
        assertDivideByZeroErrorMessage("/=");

        assertDivideByZeroErrorMessage("0/0=");
        assertDivideByZeroErrorMessage("1/ 0=");
        assertDivideByZeroErrorMessage("99/0=");
        assertDivideByZeroErrorMessage("fraction(0) = ");


    }

    @Test
    public void testNegateButton() {
        assertExpression(0, "negate(0) = ");
        assertExpression(0, "negate(negate(0) = ");
        assertExpression(0, "negate(negate(negate(negate(0) = ");
        assertExpression(-1, "negate(1) = ");
        assertExpression(1, "negate(negate(1) = ");
        assertExpression(-1, "negate(negate(negate(1) = ");
        assertExpression(-9, "negate(9) = ");
        assertExpression(9, "negate(negate(9) = ");
        assertExpression(0, "negate() =");

        assertExpression(0, "negate()");

        assertExpression(0, "negate() = ");
        assertExpression(0, "negate(negate() = ");
        assertExpression(0, "negate(negate() = ");
        assertExpression(0, "negate(negate() = ");
        assertExpression(0, "negate(negate(negate(negate() = ");

        assertExpression(-9, "negate(9) = ");
        assertExpression(0, "negate() + negate() = ");
        assertExpression(0, "negate(negate() + negate() = ");
        assertExpression(0, "negate(negate() + negate(negate(negate() = ");
        assertExpression(0, "negate(negate() + negate(negate(negate() = ");
        assertExpression(-7, "negate(9) + 2 = ");
        assertExpression(11, "negate(negate(9) + 2 = ");

        assertExpression(-1, "negate(1) = ");
        assertExpression(-9, "negate(9) =");
        assertExpression(-4, "negate(1) + negate(3) = ");
        assertExpression(9, "negate(negate(11) + negate(2) = ");
        assertExpression(0, "negate(negate(4) + negate(negate(negate(4) = ");
        assertExpression(0, "negate(negate(2) + negate(negate(negate(2) = ");
        assertExpression(-7, "negate(9) + 2 =");

        assertExpression("-1,234,567,890,123,456", "negate(1234567890123456)");
    }

    @Test
    public void testSquareRootButton() {
        assertExpression(3, "sqrt(9) = ");
        assertExpression(9, "sqrt(81) = ");
        assertExpression(3, "sqrt(sqrt(81) = ");
        assertExpression(9, "sqrt(81) = ");

        assertExpression(3, "sqrt(sqrt(81) + negate(0) = ");
        assertExpression(3, "sqrt(sqrt(81) + 0 = ");
        assertExpression(4, "sqrt(sqrt(81) + 1 = ");
        assertExpression(5, "sqrt(sqrt(81) + 2 = ");
        assertExpression(7.5, "sqrt(sqrt(81) + 2 + 10/2 = ");
        assertExpression(10, "sqrt(sqrt(81) + 2*2 = ");

        assertExpression(3, "sqrt(sqrt(81) - 0 = ");
        assertExpression(2, "sqrt(sqrt(81) - 1 = ");
        assertExpression(2, "sqrt(sqrt(81) - 2*2 = ");
        assertExpression(1, "sqrt(sqrt(81) - 2 = ");
        assertExpression(0, "sqrt(sqrt(81) - 3 = ");
        assertExpression(-1, "sqrt(sqrt(81) - 4 = ");
        assertExpression(-4.5, "sqrt(sqrt(81) - 2 - 10/2 = ");

        assertExpression(-6, "sqrt(sqrt(81) * negate(2) = ");
        assertExpression(-3, "sqrt(sqrt(81) * negate(1) = ");
        assertExpression(0, "sqrt(sqrt(81) * 0 = ");
        assertExpression(3, "sqrt(sqrt(81) * 1 = ");
        assertExpression(6, "sqrt(sqrt(81) * 2 = ");
        assertExpression(12, "sqrt(sqrt(81) * 2*2 = ");

        assertExpression(1.5, "sqrt(sqrt(81) / 2 = ");
        assertExpression(3, "sqrt(sqrt(81) / 2*2 = ");

        assertExpression(5, "sqrt(sqrt(81) + sqrt(4) = ");
        assertExpression(5, " + sqrt(sqrt(81) + sqrt(4) = ");
        assertExpression("1.000677130693066", "sqrt(sqrt(sqrt(sqrt(sqrt(sqrt(sqrt(sqrt(sqrt(sqrt(sqrt(4)");

        assertExpression("Illegal operand", "negate(sqrt(5)");
        assertExpression("Illegal operand", "5 - 10 = sqrt()");

        assertExpression("Illegal operand", "5 - 10 * 2 = sqrt()");
        assertExpression("Illegal operand", "5 - 10 / 2 = sqrt()");

        assertExpression("Illegal operand", "5 - 10 * 2 = * 1 = sqrt()");
        assertExpression("Illegal operand", "5 - 10 * 2 = / 1 = sqrt()");

        assertExpression("Illegal operand", "5 - 10 + negate(sqrt(5)");
        assertExpression("Illegal operand", "5 - 10 - negate(sqrt(5)");
        assertExpression("Illegal operand", "5 - 10 * negate(sqrt(5)");
        assertExpression("Illegal operand", "5 - 10 / negate(sqrt(5)");
    }

    @Test
    public void testSquareButton() {
        assertExpression(9, "square(3) = ");
        assertExpression(81, "square(square(3) = ");
        assertExpression(83, "square(square(3) + 2 = ");
        assertExpression(79, "square(square(3) - 2 = ");

        assertExpression(0, "square(0) = ");
        assertExpression(0, "square(square(0) = ");
        assertExpression(0, "square(square(square(square(0) = ");
        assertExpression(1, "square(1) = ");
        assertExpression(1, "square(square(1) = ");
        assertExpression(1, "square(square(square(1) = ");
        assertExpression(81, "square(9) = ");
        assertExpression(6561, "square(square(9) = ");

        assertExpression(0, "square() = square()");
        assertExpression(0, "square(square() = square()");
        assertExpression(0, "square(square() = square(square()");
        assertExpression(0, "square(square() = square(square(square()");
        assertExpression(0, "square(square(square(square() = square(square(square()");

        assertExpression(81, "square(9) = ");
        assertExpression(4, "square(2) = ");
        assertExpression(0, "square() + square() = ");
        assertExpression(0, "square(square() + square() = ");
        assertExpression(0, "square(square() + square(square(square() =");
        assertExpression(0, "square(square() + square(square(square() = ");
        assertExpression(6, "square(2) + 2 = ");
        assertExpression(3, "square(1) + 2 = ");
        assertExpression(2, "square(square(0) + 2 = ");
        assertExpression(18, "square(square(2) + 2 = ");

        assertExpression(9, "square(56)square(9)square(3)=");

        assertExpression("-0.5", "negate(fraction(2)");
        assertExpression("-2", "negate(fraction(fraction(2)");
        assertExpression("-0.5", "10 - 12 = fraction()");
        assertExpression("-2", "10 - 12 = fraction(fraction()");
    }

    @Test
    public void testFractionButton() {
        assertExpression(0.1, "fraction(10) = ");
        assertExpression(0.02, "fraction(50) = ");
        assertExpression(2, "fraction(fraction(2) = ");
        assertExpression(0.02, "fraction(50) = ");
        assertExpression(0.02, "fraction(50) = ");

        assertExpression(-0.1, "fraction(negate(10) = ");
        assertExpression(-0.02, "fraction(negate(50) = ");
        assertExpression(-2, "fraction(fraction(negate(2) = ");
        assertExpression(-0.02, "fraction(negate(50) = ");

        assertExpression(1, "fraction(fraction(1) + negate(0) = ");
        assertExpression(3, "fraction(fraction(2) + 1 = ");
        assertExpression("2.3333333333333333", "fraction(3) + 2 = ");
        assertExpression(6.05, "fraction(10) + 2 + 10/2 = ");
        assertExpression(4.5, "fraction(4) + 2*2 = ");

        assertExpression(1, "fraction(fraction(1) - 0 = ");
        assertExpression(0, "fraction(fraction(1) - 1 = ");
        assertExpression(-2, "fraction(fraction(1) - 2*2 = ");
        assertExpression(-1, "fraction(fraction(1) - 2 = ");
        assertExpression(-2, "fraction(fraction(1) - 3 = ");
        assertExpression(-3, "fraction(fraction(1) - 4 = ");
        assertExpression(-5.5, "fraction(fraction(1) - 2 - 10/2 = ");

        assertExpression(-0.4, "fraction(fraction(fraction(5) * negate(2) = ");
        assertExpression(-0.05, "fraction(fraction(fraction(20) * negate(1) = ");
        assertExpression(0, "fraction(fraction(fraction(100) * 0 = ");
        assertExpression(0.01, "fraction(fraction(fraction(100) * 1 = ");
        assertExpression("0.6666666666666667", "fraction(fraction(fraction(3) * 2 = ");
        assertExpression("1.3333333333333333", "fraction(fraction(fraction(3) * 2*2 = ");

        assertExpression(0.0151515151515152, "fraction(33) / 2 = ");
        assertExpression(0.0303030303030303, "   fraction(33) / 2*2 = ");

        assertExpression(1.5, "fraction(fraction(1) + fraction(2) = ");
        assertExpression(-1.5, " + negate(fraction(fraction(1) + negate(fraction(2) = ");
        assertExpression(10, "sqrt(sqrt(81) + 2*2 = ");
    }

    @Test
    public void testEngineeringMode() {
        assertExpression("4.572473708276177E-4", "1/3=======");
        assertExpression("6.774035123372115E-5", "4/3==========");

        //test switch engi mode
        assertExpression("1E16", "9999999999999999 + 1=");
        assertExpressionWithoutClear("9,999,999,999,999,999", "-1=");

        assertExpression("1E16", "9999999999999999 + 1 ==");
        assertExpressionWithoutClear("9,999,999,999,999,999", "-1==");

        assertExpression("1.1E16", "9999999999999999 * 1.1=");
        assertExpressionWithoutClear("9,999,999,999,999,999", "/ 1.1=");

        assertExpression("9,999,999,999,999,999", "9999999999999999 + 0.0000000000000001 ==");
        assertExpressionWithoutClear("9,999,999,999,999,999", "-0.0000000000000001==");

        assertExpression("9,999,999,999,999,999", "9999999999999999 - 0.0000000000000001 ==");
        assertExpressionWithoutClear("9,999,999,999,999,999", "+0.0000000000000001==");

        assertExpression("9.999999999999999E-17", "9999999999999999 * 0.0000000000000001 ==");
        assertExpressionWithoutClear("9,999,999,999,999,999", "/0.0000000000000001==");

        assertExpression("9.999999999999999E47", "9999999999999999 / 0.0000000000000001 ==");
        assertExpressionWithoutClear("9,999,999,999,999,999", "*0.0000000000000001==");

        assertExpression("9.866666666666666E17", "8888888888888888 * 111=");
        assertExpressionWithoutClear("8,888,888,888,888,888", "/ 111=");

        assertExpression("1.11E18", "9999999999999999 * 111=");
        assertExpressionWithoutClear("9,999,999,999,999,999", "/ 111=");

        assertExpression("9.999999999999999E16", "9999999999999999 / 0.1=");
        assertExpressionWithoutClear("9,999,999,999,999,999", "* 0.1=");

        assertExpression("9E16", "9000000000000000 * 10=");
        assertExpressionWithoutClear("9,000,000,000,000,000", "/ 10=");

        assertExpression("1E-17", "1 / 1000000000000000 / 100=");
        assertExpressionWithoutClear("0.000000000000001", "*100=");

        assertExpression("9.996000599960001E15", "square(square(9999)");
        assertExpressionWithoutClear("9,999", "sqrt(sqrt()");

        assertExpression("9.999999999999998E31", "9999999999999999*9999999999999999=");
        assertExpressionWithoutClear("9,999,999,999,999,999", "/9999999999999999=");

        //test boundary values
        assertExpression("Limit reached!", "square(square(square(square(square(square(square(9999999999999999)=");
        assertExpression("Limit reached!", "square(square(square(square(square(square(square(9999999999999999)");
        assertExpression("Limit reached!", "1/9999999999999999========MS/MR==========");
        assertExpression("Limit reached!", "1*9999999999999999========MS*MR==========");

        StringBuilder expression = new StringBuilder();
        for (int i = 0; i < 3321; i++) {
            expression.append("+=");
        }
        assertExpression("5.255518873824417E999", "1" + expression.toString()); //checks limit not reached

        //after doubled value limit is reached
        expression.append("+=");
        assertExpression("Limit reached!", "1" + expression.toString());

        assertExpression("9.999999999999968E511", "1*9999999999999999================================");
        assertExpression("9.999999999999938E991", "1*9999999999999999==============================================================");
    }

    @Test
    public void testResizeButtonFont() {
        int sleepTime = 300;

        stage.setWidth(250);
        stage.setHeight(350);

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertResizeFontButton("number_small_font", UITestButton.NUMBER_1);
        assertResizeFontButton("number_small_font", UITestButton.NUMBER_2);
        assertResizeFontButton("number_small_font", UITestButton.NUMBER_3);
        assertResizeFontButton("number_small_font", UITestButton.NUMBER_4);
        assertResizeFontButton("number_small_font", UITestButton.NUMBER_5);
        assertResizeFontButton("number_small_font", UITestButton.NUMBER_6);
        assertResizeFontButton("number_small_font", UITestButton.NUMBER_7);
        assertResizeFontButton("number_small_font", UITestButton.NUMBER_8);
        assertResizeFontButton("number_small_font", UITestButton.NUMBER_9);
        assertResizeFontButton("number_small_font", UITestButton.NUMBER_0);

        assertResizeFontButton("binary_small_font", UITestButton.ADD);
        assertResizeFontButton("binary_small_font", UITestButton.SUBTRACT);
        assertResizeFontButton("binary_small_font", UITestButton.MULTIPLY);
        assertResizeFontButton("binary_small_font", UITestButton.DIVIDE);
        assertResizeFontButton("binary_small_font", UITestButton.EQUAL);

        assertResizeFontButton("unary_small_font", UITestButton.SQUARE);
        assertResizeFontButton("unary_small_font", UITestButton.SQUARE_ROOT);
        assertResizeFontButton("unary_small_font", UITestButton.FRACTION);
        assertResizeFontButton("unary_small_font", UITestButton.PERCENT);
        assertResizeFontButton("unary_small_font", UITestButton.NEGATE);

        assertResizeFontButton("point_small_font", UITestButton.POINT);

        assertResizeFontButton("clear_small_font", UITestButton.CLEAR_ALL);
        assertResizeFontButton("clear_small_font", UITestButton.CLEAR_ENTRY);
        assertResizeFontButton("clear_small_font", UITestButton.BACKSPACE);

        assertResizeFontButton("memory_font", UITestButton.MEMORY_ADD);
        assertResizeFontButton("memory_font", UITestButton.MEMORY_SUBTRACT);
        assertResizeFontButton("memory_font", UITestButton.MEMORY_CLEAR);
        assertResizeFontButton("memory_font", UITestButton.MEMORY_RECALL);
        assertResizeFontButton("memory_font", UITestButton.MEMORY_SAVE);

        stage.setWidth(400);
        stage.setHeight(600);

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertResizeFontButton("number_big_font", UITestButton.NUMBER_1);
        assertResizeFontButton("number_big_font", UITestButton.NUMBER_2);
        assertResizeFontButton("number_big_font", UITestButton.NUMBER_3);
        assertResizeFontButton("number_big_font", UITestButton.NUMBER_4);
        assertResizeFontButton("number_big_font", UITestButton.NUMBER_5);
        assertResizeFontButton("number_big_font", UITestButton.NUMBER_6);
        assertResizeFontButton("number_big_font", UITestButton.NUMBER_7);
        assertResizeFontButton("number_big_font", UITestButton.NUMBER_8);
        assertResizeFontButton("number_big_font", UITestButton.NUMBER_9);
        assertResizeFontButton("number_big_font", UITestButton.NUMBER_0);

        assertResizeFontButton("binary_big_font", UITestButton.ADD);
        assertResizeFontButton("binary_big_font", UITestButton.SUBTRACT);
        assertResizeFontButton("binary_big_font", UITestButton.MULTIPLY);
        assertResizeFontButton("binary_big_font", UITestButton.DIVIDE);
        assertResizeFontButton("binary_big_font", UITestButton.EQUAL);

        assertResizeFontButton("unary_big_font", UITestButton.SQUARE);
        assertResizeFontButton("unary_big_font", UITestButton.SQUARE_ROOT);
        assertResizeFontButton("unary_big_font", UITestButton.FRACTION);
        assertResizeFontButton("unary_big_font", UITestButton.PERCENT);
        assertResizeFontButton("unary_big_font", UITestButton.NEGATE);

        assertResizeFontButton("clear_big_font", UITestButton.CLEAR_ALL);
        assertResizeFontButton("clear_big_font", UITestButton.CLEAR_ENTRY);
        assertResizeFontButton("clear_big_font", UITestButton.BACKSPACE);

        assertResizeFontButton("memory_font", UITestButton.MEMORY_ADD);
        assertResizeFontButton("memory_font", UITestButton.MEMORY_SUBTRACT);
        assertResizeFontButton("memory_font", UITestButton.MEMORY_CLEAR);
        assertResizeFontButton("memory_font", UITestButton.MEMORY_RECALL);
        assertResizeFontButton("memory_font", UITestButton.MEMORY_SAVE);

        assertResizeFontButton("point_big_font", UITestButton.POINT);
    }

    @Test
    public void testResizeDisplayFont() {
        waitForCompleteExecution();

        setMinMaxDisplayFont();

        stage.setHeight(350);
        stage.setWidth(250);

        parseAndExecute("CE 1");
        assertResizeFontDisplay(display);
        parseAndExecute("CE 123456");
        assertResizeFontDisplay(display);
        parseAndExecute("CE 1234567890");
        assertResizeFontDisplay(display);
        parseAndExecute("CE 1239084723179");
        assertResizeFontDisplay(display);
        parseAndExecute("CE 1234567890123456");
        assertResizeFontDisplay(display);
        parseAndExecute("CE 0.123456789012345");
        assertResizeFontDisplay(display);

        parseAndExecute("CE square(1239084723179)");
        assertResizeFontDisplay(display);
        parseAndExecute("CE sqrt(1239084723179)");
        assertResizeFontDisplay(display);

        stage.setHeight(550);
        stage.setWidth(350);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        parseAndExecute("CE 1239084723179");
        assertResizeFontDisplay(display);
        parseAndExecute("CE 1234567890123456");
        assertResizeFontDisplay(display);
        parseAndExecute("CE 0.1234567890123456");
        assertResizeFontDisplay(display);

        parseAndExecute("CE square(1239084723179)");
        assertResizeFontDisplay(display);
        parseAndExecute("CE sqrt(1239084723179)");
        assertResizeFontDisplay(display);
    }

    @Test
    public void testHistoryDisplay() {
        assertHistoryExpressionDisplay("5 + 1 −", "5 + 1 -");
        assertHistoryExpressionDisplay("4 − 1 ÷", "4 - 1 /");
        assertHistoryExpressionDisplay("3 × sqr(3)", "3 * square(3)");
        assertHistoryExpressionDisplay("2 ÷ 4 +", "2 / 4 +");
        assertHistoryExpressionDisplay("1 + 5 −", "1 + 5 -");
        assertHistoryExpressionDisplay("0 − 6 ×", "0 - 6 *");

        assertHistoryExpressionDisplay("5 + 6 + sqr(7)", "5 + 6 + square(7)");
        assertHistoryExpressionDisplay("124 − 6 ÷ √(7)", "124 - 6 / sqrt(7)");
        assertHistoryExpressionDisplay("3 + negate(233) + 1/(7)", "3 + negate(233) + fraction(7)");
        assertHistoryExpressionDisplay("1 × negate(6) + 1/(71)", "1 * negate(6) + fraction(71)");
        assertHistoryExpressionDisplay("0 + negate(1,231) ÷ negate(negate(6) + √(sqr(71)", "0 + negate(1231) / negate(negate(6) + sqrt(square(71)");

        assertHistoryExpressionDisplay("negate(0)", "negate()");
        assertHistoryExpressionDisplay("negate(1,231)", "negate(1231)");
        assertHistoryExpressionDisplay("negate(negate(1,231)", "negate(negate(1231)");
        assertHistoryExpressionDisplay("negate(negate(√(1,231)", "negate(negate(sqrt(1231)");
        assertHistoryExpressionDisplay("negate(negate(√(1,231)", "negate(negate(sqrt(1231)");
        assertHistoryExpressionDisplay("0", "negate(negate(percent(percent(1231)");
        assertHistoryExpressionDisplay("3 + 0.4356", "3 + square(percent(percent(22)");
        assertHistoryExpressionDisplay("3 − 5 +", "3 + square(percent(percent(22) CE - 5 +");

        //checks if history empty after clear-entry in cases where expression have 1 stage and unary operations
        CLEAR_ALL.push();
        parseAndExecute("square(5)");
        CLEAR_ENTRY.push();
        assertHistoryExpressionDisplayWithoutClear("", "");

        //
        CLEAR_ALL.push();
        parseAndExecute("5 + square(7)");
        CLEAR_ENTRY.push();
        assertHistoryExpressionDisplayWithoutClear("5 +", "");
    }

    @Test
    public void testMemoryButton() {
        //Memory
        assertExpression(17, "5 + 12 MS M+ =");
        assertExpressionWithoutClear(12, "MR - 12 =");

        assertExpression(12, " 12*3= MS M+ M+ M+ MR/12=");
        assertExpression(2, "5 + 3 M+ MC MC M- MR =");

        assertExpression(999, "123 MS 999");
        assertExpression(333, "123 M+ 333");
        assertExpression(444, "123 M- 444");
        assertExpression(444, "123 M- 888 MR 444");
    }

    @Test
    public void testBackspaceButton() {
        assertBackspace("1,234", "1234567<<<");
        assertBackspace("0", "1234567<<<<<<<");
        assertBackspace("0", "1234567<<<<<<<<<<<<");
        assertBackspace("5.0", "5.0123<<<");
        assertBackspace("5.1234", "5.12340<");
        assertBackspace("5.", "5.12340<<<<<");
        assertBackspace("5", "5.12340<<<<<<");

        assertBackspace("0.00", "0.001<");
        assertBackspace("0.0", "0.001<<");
        assertBackspace("0.", "0.001<<<");
        assertBackspace("0", "0.001<<<<");
        assertBackspace("0", "0.001<<<<<");

        assertBackspace("0.000000000000000", "0.0000000000000001<");
        assertBackspace("0.00000000000000", "0.0000000000000001<<");
        assertBackspace("0.0", "0.0000000000000001<<<<<<<<<<<<<<<");
        assertBackspace("0.", "0.0000000000000001<<<<<<<<<<<<<<<<");
        assertBackspace("0", "0.0000000000000001<<<<<<<<<<<<<<<<<");

        assertBackspace("53", "12 + 538< ");
        assertBackspace("1", "12 * 2 - 111 <<");
        assertBackspace("0", "12 * 2 - 111 + 123 <<<");
        assertBackspace("0", "12 * 2 - 111 + 123 <<<<<");

        assertBackspace("0", "1.<<<");
        assertBackspace("1", "11.1<<<");
        assertBackspace("0", "11.12<<<<<");
        assertBackspace("0", "0.<");
        assertBackspace("0.", "0.1<");
        assertBackspace("0.1", "0.12<");
        assertBackspace("1", "1.12<<<");
    }

    @Test
    public void testSystemButton() {
        //minimize button
        MINIMIZE_APP.push();
        waitForCompleteExecution();

        assertTrue(stage.isIconified());

        //maximize button
        MAXIMIZE_APP.push();
        waitForCompleteExecution();

        assertEquals(true, stage.isMaximized());

        MAXIMIZE_APP.push();
        waitForCompleteExecution();

        assertEquals(false, stage.isMaximized());
    }

    @Test
    public void testDraggableWindow() throws InterruptedException {
        assertDraggable(200, 200);
    }

    @Test
    public void testKeyboardShortcut() {
        assertKeyPressOnDisplay("1", DIGIT1);
        assertKeyPressOnDisplay("2", DIGIT2);
        assertKeyPressOnDisplay("3", DIGIT3);
        assertKeyPressOnDisplay("4", DIGIT4);
        assertKeyPressOnDisplay("5", DIGIT5);
        assertKeyPressOnDisplay("6", DIGIT6);
        assertKeyPressOnDisplay("7", DIGIT7);
        assertKeyPressOnDisplay("8", DIGIT8);
        assertKeyPressOnDisplay("9", DIGIT9);
        assertKeyPressOnDisplay("0", DIGIT0);
        assertKeyPressOnDisplay("0.", PERIOD);

        assertKeyPressOnHistory(CalculatorButton.MULTIPLY.getValue(), KeyCode.MULTIPLY);
        assertKeyPressOnHistory(CalculatorButton.DIVIDE.getValue(), SLASH);
        assertKeyPressOnHistory(CalculatorButton.ADD.getValue(), PLUS);
        assertKeyPressOnHistory(CalculatorButton.SUBTRACT.getValue(), MINUS);

        assertKeyPressOnDisplayWithExpression("9", EQUALS, "5+4", false);
        assertKeyPressOnDisplayWithExpression("1", ENTER, "5-4", false);

        assertKeyPressOnDisplayWithExpression("9", KeyCode.AT, "3", false); //square
        assertKeyPressOnDisplayWithExpression("0", DIGIT5, "3", true); //percent
        assertKeyPressOnDisplayWithExpression("1.5", DIGIT5, "3 + 50", true); //percent
        assertKeyPressOnDisplayWithExpression("3", DIGIT2, "9", true); //square root
        assertKeyPressOnDisplayWithExpression("0.1", R, "10", false); //fraction

        assertKeyPressOnDisplayWithExpression("10", M, "10", true);  //MS
        assertKeyPressOnDisplayWithExpressionWithoutClear("10", R, "", true);   //MR

        assertKeyPressOnDisplayWithExpression("10", P, "10", true); //M+
        assertKeyPressOnDisplayWithExpressionWithoutClear("10", P, "10", true); //M+
        assertKeyPressOnDisplayWithExpressionWithoutClear("20", R, "10", true); //MR

        assertKeyPressOnDisplayWithExpression("10", P, "10", true); //M+
        assertKeyPressOnDisplayWithExpressionWithoutClear("10", P, "10", true); //M+
        assertKeyPressOnDisplayWithExpressionWithoutClear("10", Q, "10", true); //M-
        assertKeyPressOnDisplayWithExpressionWithoutClear("10", R, "0", true); //MR
        assertKeyPressOnDisplayWithExpressionWithoutClear("0", L, "0", true); //MC
        assertKeyPressOnDisplayWithExpressionWithoutClear("0", R, "0", true); //MR

        assertKeyPressOnDisplayWithExpression("1", BACK_SPACE, "10", true);
        assertKeyPressOnDisplayWithExpression("0", BACK_SPACE, "1", true);

        //clear_ALL
        assertKeyPressOnDisplayWithExpression("0", ESCAPE, "10", true);
        assertKeyPressOnDisplayWithExpression("0", ESCAPE, "1123123", true);
        assertKeyPressOnDisplayWithExpression("0", ESCAPE, "negate(123123)", true);
        assertKeyPressOnDisplayWithExpression("0", ESCAPE, "123+ 3213", true);

        //clear_entry
        assertKeyPressOnDisplayWithExpression("0", DELETE, "10 + 12", true);
        assertKeyPressOnDisplayWithExpressionWithoutClear("24", M, "14 = ", true);

        //negate
        assertKeyPressOnDisplayWithExpression("-10", F9, "10", true);
        assertKeyPressOnDisplayWithExpression("-22", F9, "14 + 22 ", true);

        //test numpad button
        assertKeyPressOnDisplay("1", NUMPAD1);
        assertKeyPressOnDisplay("2", NUMPAD2);
        assertKeyPressOnDisplay("3", NUMPAD3);
        assertKeyPressOnDisplay("4", NUMPAD4);
        assertKeyPressOnDisplay("5", NUMPAD5);
        assertKeyPressOnDisplay("6", NUMPAD6);
        assertKeyPressOnDisplay("7", NUMPAD7);
        assertKeyPressOnDisplay("8", NUMPAD8);
        assertKeyPressOnDisplay("9", NUMPAD9);
        assertKeyPressOnDisplay("0", NUMPAD0);
        assertKeyPressOnDisplay("0.", DECIMAL);
        assertKeyPressOnHistory(CalculatorButton.DIVIDE.getValue(), KeyCode.DIVIDE);
        assertKeyPressOnHistory(CalculatorButton.ADD.getValue(), KeyCode.ADD);
        assertKeyPressOnHistory(CalculatorButton.SUBTRACT.getValue(), KeyCode.SUBTRACT);
    }

    @Test
    public void testClearButton() {
        assertExpression(3, "sqrt(9)=");
        CLEAR_ENTRY.push();
        assertExpressionWithoutClear(81, "square(9)=");

        CLEAR_ALL.push();
        assertExpressionWithoutClear(11, "5 + 3 * 2 + 11");

        CLEAR_ENTRY.push();
        assertExpressionWithoutClear(16, "16");
        assertExpressionWithoutClear(32, "=");

        CLEAR_ENTRY.push();
        assertExpressionWithoutClear(0, "");

        assertExpression("-5", "5 + negate(5)");
        CLEAR_ENTRY.push();
        assertExpression(0, "");
        assertExpression(0, "negate()");
    }

    private void assertExpression(String expected, String input) {
        CLEAR_ALL.push();
        parseAndExecute(input);
        assertEquals(expected, display.getText());
    }

    private void assertExpression(double expected, String input) {
        CLEAR_ALL.push();
        parseAndExecute(input);
        assertEquals(formatter.format(expected), display.getText());
    }

    private void assertExpression(int expected, String input) {
        CLEAR_ALL.push();
        parseAndExecute(input);
        assertEquals(formatter.format(expected), display.getText());
    }

    private void assertExpressionWithoutClear(String expected, String input) {
        parseAndExecute(input);
        assertEquals(expected, display.getText());
    }

    private void assertExpressionWithoutClear(int expected, String input) {
        parseAndExecute(input);
        assertEquals(String.valueOf(expected), display.getText());
    }

    private void assertKeyPressOnDisplay(String expected, KeyCode code) {
        CLEAR_ALL.push();

        Platform.runLater(() -> CalculatorButton.NUMBER_0.getButton().getParent()
                .fireEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", code, false, false, false, false)));

        waitForCompleteExecution();

        assertEquals(expected, display.getText());
    }

    private void assertKeyPressOnDisplayWithExpression(String expected, KeyCode code, String expression, boolean isPressedCtrl) {
        CLEAR_ALL.push();

        parseAndExecute(expression);

        Platform.runLater(() -> CalculatorButton.NUMBER_0.getButton().getParent()
                .fireEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", code, false, isPressedCtrl, false, false)));

        waitForCompleteExecution();

        assertEquals(expected, display.getText());
    }

    private void assertKeyPressOnDisplayWithExpressionWithoutClear(String expected, KeyCode code, String expression, boolean isPressedCtrl) {
        parseAndExecute(expression);

        Platform.runLater(() -> CalculatorButton.NUMBER_0.getButton().getParent()
                .fireEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", code, false, isPressedCtrl, false, false)));

        waitForCompleteExecution();

        assertEquals(expected, display.getText());
    }

    private void assertKeyPressOnHistory(String expected, KeyCode code) {
        CLEAR_ALL.push();

        Platform.runLater(() -> CalculatorButton.NUMBER_0.getButton().getParent()
                .fireEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", code, false, false, false, false)));

        waitForCompleteExecution();

        assertEquals("0 " + expected, history.getText());
    }

    private void parseAndExecute(String input) {
        String data = input.replaceAll("\\s+", "");

        StringBuilder sb = new StringBuilder();
        List<UITestButton> listUnary = new ArrayList<>();
        boolean memory_flag = false;
        boolean clear_flag = false;
        boolean unary_flag = false;

        for (int i = 0; i < data.length(); i++) {
            String symbol = data.substring(i, i + 1);

            if (symbol.toUpperCase().equals("C") && !unary_flag && !memory_flag) {
                clear_flag = true;
                continue;
            }

            if (clear_flag) {
                if (symbol.toUpperCase().equals("E")) {
                    UITestButton.getUIButton("CE").push();
                } else {
                    UITestButton.getUIButton("C").push();
                }
                clear_flag = false;
                continue;
            }

            if (StringUtils.isNumeric(symbol)) {
                UITestButton.getUIButton(symbol).push();
                continue;
            }

            if (symbol.equals("<")) {
                UITestButton.getUIButton(symbol).push();
                continue;
            }

            if (symbol.toUpperCase().equals("M")) {
                memory_flag = true;
                continue;
            }

            if (memory_flag) {
                UITestButton.getUIButton("M" + symbol).push();
                memory_flag = false;
                continue;
            }

            if (StringUtils.isAlpha(symbol)) {
                unary_flag = true;
                sb.append(symbol);
                continue;
            }

            if (symbol.equals(".")) {
                UITestButton.getUIButton(symbol).push();
                continue;
            }

            if (symbol.equals("(")) {
                listUnary.add(UITestButton.getUIButton(sb.toString()));
                sb = new StringBuilder();
                unary_flag = false;
                continue;
            }

            if (symbol.equals(")")) {
                listUnary.forEach(UITestButton::push);
                listUnary.clear();
                continue;
            }

            UITestButton.getUIButton(symbol).push();
        }

        waitForCompleteExecution();
    }

    /**
     * Lock test thread and wait for complete operations in JAvaFX Thread
     */
    private void waitForCompleteExecution() {
        Object sync = new Object();

        synchronized (sync) {
            Platform.runLater(() -> {
                synchronized (sync) {
                    sync.notify();
                }
            });

            try {
                sync.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void assertResizeFontButton(String expected, UITestButton button) {
        ObservableList<String> cssClasses = button.getStyleClass();
        assertTrue(cssClasses.contains(expected));
    }

    private void setMinMaxDisplayFont() {
        ObservableList<String> styleClass = display.getStyleClass();

        String maxFont = "display_max_font";
        String minFont = "display_min_font";

        if (!styleClass.contains(maxFont)) {
            styleClass.add(maxFont);
        }
        styleClass.remove(minFont);

        display.applyCss();
        maxDisplayFont = (int) display.getFont().getSize();

        //get css min display font
        if (!styleClass.contains(minFont)) {
            styleClass.add(minFont);
        }
        styleClass.remove(maxFont);

        display.applyCss();
        minDisplayFont = (int) display.getFont().getSize();

        styleClass.remove(minFont);
    }

    private void assertResizeFontDisplay(Label display) {
        double d = display.getWidth() / display.getText().length() * MULTIPLIER;

        Scene scene = display.getScene();
        if (compare(scene.getWidth(), BOUNDARY_WIDTH) > 0 && compare(scene.getHeight(), BOUNDARY_HEIGHT) > 0) {
            if (d > maxDisplayFont) {
                d = maxDisplayFont;
            }
        } else {
            if (d > minDisplayFont) {
                d = minDisplayFont;
            }
        }
        assertEquals((int) d, (int) Integer.valueOf(display.getStyle().replace("-fx-font-size:", "")));
    }

    private void assertDraggable(int dragX, int dragY) {
        MouseEvent mousePressed = new MouseEvent(MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null);
        Event.fireEvent(stage.getScene(), mousePressed);
        double startX = mousePressed.getScreenX();
        double startY = mousePressed.getScreenY();

        MouseEvent mouseDrag = new MouseEvent(MouseEvent.MOUSE_DRAGGED, 0, 0, dragX, dragY, MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null);
        Event.fireEvent(stage.getScene(), mouseDrag);
        double endX = mouseDrag.getScreenX();
        double endY = mouseDrag.getScreenY();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(Math.abs(startX - endX), dragX, 0.1);
        assertEquals(Math.abs(startY - endY), dragY, 0.1);
    }

    private void assertHistoryExpressionDisplayWithoutClear(String expect, String input) {
        parseAndExecute(input);

        assertEquals(expect, history.getText());
    }

    private void assertHistoryExpressionDisplay(String expected, String input) {
        CLEAR_ALL.push();
        parseAndExecute(input);

        assertEquals(expected, history.getText());
    }

    private void assertBackspace(String expected, String expression) {
        CLEAR_ALL.push();
        parseAndExecute(expression);

        assertEquals(expected, display.getText());
    }

    private void assertDivideByZeroErrorMessage(String input) {
        CLEAR_ALL.push();
        parseAndExecute(input);

        assertEquals("Cannot divide by zero", display.getText());
    }
}