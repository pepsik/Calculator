package org.pepsik.model;

import com.athaydes.automaton.FXApp;
import com.athaydes.automaton.FXer;
import org.apache.commons.lang.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pepsik.MainApp;
import org.pepsik.model.operation.BinaryOperation;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.athaydes.automaton.assertion.AutomatonMatcher.hasText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UITest {

    private FXer user = FXer.getUserWith(FXApp.getScene().getRoot());

    @BeforeClass
    public static void launchApp() throws Exception {
        System.out.println("Launching Java App");

        FXApp.startApp(new MainApp());

        System.out.println("App has been launched");

        // let the window open and show before running tests
        Thread.sleep(2000);
    }

    @AfterClass
    public static void cleanup() {
        System.out.println("Cleaning up");
        FXApp.doInFXThreadBlocking(() -> FXApp.getStage().close());
    }

    @Test
    public void automatonTest() {
        System.out.println("Running test");

        testOperation(0, "+=");
        testOperation(0, "-=");
        testOperation(0, "*=");
        testOperation(0, "+=");
        testOperation(0, "-=");
        testOperation(0, "*=");
        testOperation(Double.NaN, "/=");
        testOperation(Double.NaN, "*=");
        testOperation(Double.NaN, "+=");
        testOperation(Double.NaN, "-=");

        user.clickOn("#clear_all");
        user.clickOn("#number_1");
        user.clickOn("#add");
        user.clickOn("#number_3");
        user.clickOn("#subtract");
        user.clickOn("#number_5");
        user.clickOn("#equal");

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertThat(user.getAt("#display"), hasText("-1"));
    }

    @Test
    public void inputNumberTest() {
//        testOperation("0", "");
//        testOperation(0, "0");
//        testOperation(1, "1");
//        testOperation(2, "2");
//        testOperation(3, "3");
//        testOperation(4, "4");
//        testOperation(5, "5");
//        testOperation(6, "6");
//        testOperation(7, "7");
//        testOperation(8, "8");
//        testOperation(9, "9");
//        testOperation(1234567890, "1 2 3 4 5 6 7 8 9 0");
//        testOperation(1234567890, "1 2 3 4 5 6 7 8 9 0");
//
//        testOperation("1.0");
        testOperation("9.9");
        testOperation("1.00");
        testOperation("1.00", "1.0.0");
        testOperation("1.11");
        testOperation(1.11, "1.1.1");
        testOperation(11.1, "11.1");
        testOperation(12.3, "12.3");
        testOperation(9.99, "9.99");
        testOperation("10.00");
        testOperation(123456., "1 2 3 4 5 6.");
        testOperation(1.234567890, "1 .2 .3 4 5. 6 .7 8. 9. 0");
        testOperation(0.1234567890, ".1 2 3 4 5 6 7 8 9 0");

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
    }

    @Test
    public void binaryOperationTest() {
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
    }

    @Test
    public void unaryOperationTest() {
        //---------------TEST UNARY OPERATORS------------
        // ----- NEGATE -----
//        testOperation(0, "negate(0) = ");
//        testOperation(0, "negate(negate(0) = ");
//        testOperation(0, "negate(negate(negate(negate(0) = ");
//        testOperation(-1, "negate(1) = ");
//        testOperation(1, "negate(negate(1) = ");
//        testOperation(-1, "negate(negate(negate(1) = ");
//        testOperation(-9, "negate(9) = ");
//        testOperation(9, "negate(negate(9) = ");
//        testOperation(0, "negate() =");
//
//        testOperation(0, "negate()");
//
//        testOperation(0, "negate() = negate()");
//        testOperation(0, "negate(negate() = negate()");
//        testOperation(0, "negate(negate() = negate(negate()");
//        testOperation(0, "negate(negate() = negate(negate(negate()");
//        testOperation(0, "negate(negate(negate(negate() = negate(negate(negate()");
//
//        testOperation(9, "negate(9) = negate()");
//        testOperation(-9, "negate(9) = negate(negate()");
//        testOperation(0, "negate() + negate() = negate()");
//        testOperation(0, "negate(negate() + negate() = negate()");
//        testOperation(0, "negate(negate() + negate(negate(negate() = negate()");
//        testOperation(0, "negate(negate() + negate(negate(negate() = negate(negate(negate()");
//        testOperation(7, "negate(9) + 2 = negate()");
//        testOperation(-7, "negate(9) + 2 = negate(negate()");
//        testOperation(11, "negate(negate(9) + 2 = negate(negate()");
//        testOperation(-11, "negate(negate(9) + 2 = negate(negate(negate()");
//
//        testOperation(-3, "negate(1) = negate(3)");
//        testOperation(2, "negate(9) = negate(negate(2)");
//        testOperation(4, "negate(1) + negate(3) = negate()");
//        testOperation(-5, "negate(negate(11) + negate(2) = negate(5)");
//        testOperation(-3, "negate(negate(4) + negate(negate(negate(4) = negate(3)");
//        testOperation(-1, "negate(negate(2) + negate(negate(negate(2) = negate(negate(negate(1)");
//        testOperation(-5, "negate(9) + 2 = negate(5)");
        // -----END NEGATE -----

        //---- SQRT ---
//        testOperation(3.0, "sqrt(9) = ");
//        testOperation(9, "sqrt(81) = ");
//        testOperation(3, "sqrt(sqrt(81) = ");
//        testOperation(3, "sqrt(81) = sqrt()");
//
//        testOperation(3, "sqrt(sqrt(81) + negate(0) = ");
//        testOperation(3, "sqrt(sqrt(81) + 0 = ");
//        testOperation(4, "sqrt(sqrt(81) + 1 = ");
//        testOperation(5, "sqrt(sqrt(81) + 2 = ");
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

        testOperation(6561, "square(9) = square()");
        testOperation(256, "square(2) = square(square()");
        testOperation(0, "square() + square() = square()");
        testOperation(0, "square(square() + square() = square()");
        testOperation(0, "square(square() + square(square(square() = square()");
        testOperation(0, "square(square() + square(square(square() = square(square(square()");
        testOperation(36, "square(2) + 2 = square()");
        testOperation(81, "square(1) + 2 = square(square()");
        testOperation(16, "square(square(0) + 2 = square(square()");
        testOperation(11019960576.0, "square(square(2) + 2 = square(square(square()");
        //--------- END SQUARE --------

        //--------- FRACTION --------
        testOperation("∞", "fraction(0) = ");
        testOperation(0.1, "fraction(10) = ");
        testOperation(0.02, "fraction(50) = ");
        testOperation(2, "fraction(fraction(2) = ");
        testOperation(50, "fraction(50) = fraction()");
        testOperation(0.2, "fraction(50) = fraction(5)");

        testOperation("∞", "fraction(negate(0) = ");
        testOperation(-0.1, "fraction(negate(10) = ");
        testOperation(-0.02, "fraction(negate(50) = ");
        testOperation(-2, "fraction(fraction(negate(2) = ");
        testOperation(-50, "fraction(negate(50) = fraction()");
        testOperation(0.2, "fraction(negate(50) = fraction(5)");
        testOperation(-0.2, "fraction(negate(50) = fraction(negate(5)");

        testOperation(1, "fraction(fraction(1) + negate(0) = ");
        testOperation(3, "fraction(fraction(2) + 1 = ");
        testOperation(2.3333, "fraction(3) + 2 = ");
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
        testOperation(0.66666, "fraction(fraction(fraction(3) * 2 = ");
        testOperation(1.33333, "fraction(fraction(fraction(3) * 2*2 = ");

        testOperation(0.0151515151515152, "fraction(33) / 2 = ");
        testOperation(0.0303030303030303, "   fraction(33) / 2*2 = ");

        testOperation(1.5, "fraction(fraction(1) + fraction(2) = ");
        testOperation(-1.5, " + negate(fraction(fraction(1) + negate(fraction(2) = ");
        //--------- END FRACTION --------
    }

    private void testOperation(double expected, String input) {
        user.clickOn("#clear_all");

        operate(new BigDecimal(expected).toString(), input);
    }

    private void testOperation(String input) {
        user.clickOn("#clear_all");

        operate(input, input);
    }

    private void testOperation(String expected, String input) {
        user.clickOn("#clear_all");

        operate(expected, input);
    }

    private void operate(String expected, String input) {
        System.out.println(expected);

        String data = input.replaceAll("\\s+", "");

        StringBuilder sb = new StringBuilder();
        List<String> listUnary = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            String symbol = data.substring(i, i + 1);

            if (StringUtils.isNumeric(symbol)) {
                user.clickOn("#" + UIButton.isExist(symbol));
                continue;
            }

            if (StringUtils.isAlpha(symbol)) {
                sb.append(symbol);
                continue;
            }

            if (symbol.equals(".")) {
                user.clickOn("#" + UIButton.isExist(symbol));
                continue;
            }

            if (BinaryOperation.isExist(symbol)) {
                user.clickOn("#" + UIButton.isExist(symbol));
                continue;
            }

            if (symbol.equals("(")) {
                listUnary.add(UIButton.isExist(sb.toString()));
                sb = new StringBuilder();
                continue;
            }

            if (symbol.equals(")")) {
                for (String operation : listUnary) {
                    user.clickOn("#" + operation);
                }

                listUnary.clear();
                continue;
            }

            throw new IllegalStateException("FAIL" + symbol);
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertThat(user.getAt("#display"), hasText(expected));
    }
}
