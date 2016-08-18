package org.pepsik.controller.util;

import org.pepsik.model.Stage;
import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Deque;
import java.util.List;

import static org.pepsik.controller.button.CalculatorButton.valueOf;
import static org.pepsik.model.operation.UnaryOperation.PERCENT;

/**
 * Util class for format display output
 */
public class TextFormatter {

    private static final String BACKSPACE = " ";
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";

    /**
     * Criteria for switch to engi mode
     */
    private static final BigDecimal CRITERIA = new BigDecimal("0.001");

    /**
     * Input formatter
     */
    private static final DecimalFormat f = new DecimalFormat();

    /**
     * Build history expression
     */
    private static final StringBuilder SB = new StringBuilder();

    /**
     * Build unary expression
     */
    private static final StringBuilder UNARY_SB = new StringBuilder();

    /**
     * Formats history
     *
     * @param expression     list of stages to format
     * @param currentOperand current input operand in expression
     * @return String represents expression
     */
    public static String history(Deque<Stage> expression, BigDecimal currentOperand, int scale) {
        SB.setLength(0);

        //iterate though expression stages and assemble string to show on display
        for (Stage stage : expression) {
            BigDecimal stageOperand = stage.getOperand();
            BinaryOperation operator = stage.getBinaryOperator();

            //adds binary operation if exist
            if (operator != null) {
                SB.append(BACKSPACE);
                SB.append(valueOf(operator.name()).getValue());
            }

            //if we get first stage without operator add space
            if (stageOperand != null) {
                if (SB.length() != 0) {
                    SB.append(BACKSPACE);
                }

                List<UnaryOperation> unaryOperators = stage.getUnaryOperators();

                //adds unary operations if exist
                if (unaryOperators.isEmpty()) {
                    SB.append(display(stageOperand, scale));
                } else {
                    // separately collect unary operators, not collect unary operators before PERCENT
                    boolean isPercent = false; //last unary percent flag
                    UNARY_SB.setLength(0);

                    for (UnaryOperation unary : unaryOperators) {
                        //if get percent then clear all previous recorded unary operations
                        if (unary.equals(PERCENT)) {
                            isPercent = true;
                            UNARY_SB.setLength(0); //clear all recorded unary
                            UNARY_SB.append(display(currentOperand, scale));
                        } else {
                            isPercent = false;
                            UNARY_SB.append(valueOf(unary.name()).getValue());
                            UNARY_SB.append(LEFT_BRACKET);
                        }
                    }
                    //checks if we get percent as last unary
                    SB.append(UNARY_SB);

                    if (!isPercent) {
                        SB.append(display(stageOperand, scale));
                        SB.append(RIGHT_BRACKET);
                    }
                }
            }
        }
        return SB.toString();
    }

    /**
     * Format display output
     *
     * @param input        SCALE to display
     * @param displayScale SCALE scale
     * @return formatted string
     */
    public static String display(BigDecimal input, int displayScale) {
        input = input.stripTrailingZeros();
        int inputScale = input.scale();
        int inputPrecision = input.precision();
        String pattern;

        //if number lower then 0.00 show in engi mode
        if (input.abs().compareTo(CRITERIA) == -1 && inputScale > displayScale) {
            pattern = "0.###############E0";
        } else
        //if number have more then scale count digits before point then show in engi mode
        {
            int digitsIntegerPart = inputPrecision - inputScale;

            if (digitsIntegerPart > displayScale) {
                pattern = "0.";

                if (inputScale > 0) {
                    for (int i = 0; i < inputScale; i++) {
                        pattern += "0";
                    }
                } else {
                    pattern += "###############";
                }

                pattern += "E0";
            } else {
                //show not more then scale count digits on display
                pattern = "###,###.#";
                for (int i = 0; i < displayScale - digitsIntegerPart; i++) {
                    pattern += "#";
                }

                input = input.setScale(displayScale, BigDecimal.ROUND_HALF_UP);
            }
        }
        f.applyPattern(pattern);
        return f.format(input);
    }

    /**
     * Formats input number
     *
     * @return formatted input number to display
     */
    public static String formatInput() {
        int scale = InputNumber.getInputScale();
        String pattern = "###,##0.";

        if (scale != 0) {
            for (int i = 0; i < scale; i++) {
                pattern += "0";
            }
        } else {
            pattern = "###,###";
        }

        f.applyPattern(pattern);
        return f.format(InputNumber.getInput());
    }
}
