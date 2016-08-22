package org.pepsik.controller.util;

import org.pepsik.controller.CalculatorController;
import org.pepsik.controller.button.CalculatorButton;
import org.pepsik.model.Stage;
import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static org.pepsik.controller.CalculatorController.*;
import static org.pepsik.model.operation.UnaryOperation.PERCENT;

/**
 * Util class for format display output
 */
public class TextFormatter {

    /**
     * represents space
     */
    private static final String SPACE = " ";

    /**
     * represents left bracket
     */
    private static final String LEFT_BRACKET = "(";

    /**
     * represents right bracket
     */
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
     * Mapping Unary operation to CalculatorButton
     */
    private static Map<UnaryOperation, CalculatorButton> unaryMapping = new HashMap<>();

    /**
     * Mapping Binary operation to CalculatorButton
     */
    private static Map<BinaryOperation, CalculatorButton> binaryMapping = new HashMap<>();

    /**
     * Formats history
     *
     * @param expression  list of stages to format
     * @param currentOperand current input operand in expression
     * @return String represents expression
     */
    public static String history(Deque<Stage> expression, BigDecimal currentOperand, int scale) {
        StringBuilder sb = new StringBuilder();

        //iterate though expression stages and assemble string to show on display
        for (Stage stage : expression) {
            BigDecimal stageOperand = stage.getOperand();
            BinaryOperation binary = stage.getBinaryOperator();

            //adds binary operation if exist
            if (binary != null) {
                sb.append(SPACE);
                CalculatorButton cb = binaryMapping.get(binary); //gets binary operation from cache
                //if not found search in Controller mapping
                if (cb == null) {
                    getBinaryMapping().entrySet().stream()
                            .filter(entry -> entry.getValue().equals(binary))
                            .forEach(entry -> {
                                CalculatorButton temp = entry.getKey();
                                sb.append(temp.getValue());
                                binaryMapping.put(binary, temp); //and add to cache
                            });

                } //or if find in cache, do it
                else {
                    sb.append(cb.getValue());
                }
            }

            //if we get first stage without operator add space
            if (stageOperand != null) {
                if (sb.length() != 0) {
                    sb.append(SPACE);
                }

                List<UnaryOperation> unaryOperators = stage.getUnaryOperators();

                //adds unary operations if exist
                if (unaryOperators.isEmpty()) {
                    sb.append(display(stageOperand, scale));
                } else {
                    // separately collect unary operators, not collect unary operators before PERCENT
                    boolean isPercent = false; //last unary percent flag
                    StringBuilder unarySb = new StringBuilder();

                    for (UnaryOperation unary : unaryOperators) {
                        //if get percent then clear all previous recorded unary operations
                        if (unary.equals(PERCENT)) {
                            isPercent = true;
                            unarySb.setLength(0); //clear all recorded unary
                            unarySb.append(display(currentOperand, scale));
                        } else {
                            isPercent = false;
                            //finds CalculatorButton by Operation
                            CalculatorButton cb = unaryMapping.get(unary); //gets unary operation from cache
                            //if not found search in Controller mapping
                            if (cb == null) {
                                getUnaryMapping().entrySet().stream()
                                        .filter(entry -> entry.getValue().equals(unary))
                                        .forEach(entry -> {
                                            CalculatorButton temp = entry.getKey();
                                            unarySb.append(temp.getValue());
                                            unaryMapping.put(unary, temp); //and add to cache
                                        });
                            } //or if find in cache, do it
                            else {
                                unarySb.append(cb.getValue());
                            }

                            unarySb.append(LEFT_BRACKET);
                        }
                    }

                    sb.append(unarySb);

                    //checks if we get percent as last unary operation
                    if (!isPercent) {
                        sb.append(display(stageOperand, scale));
                        sb.append(RIGHT_BRACKET);
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * Format display output
     *
     * @param input value to display
     * @param displayScale input scale for display
     * @return formatted string
     */
    public static String display(BigDecimal input, int displayScale) {
        input = input.stripTrailingZeros();
        int inputScale = input.scale();
        int inputPrecision = input.precision();

        String pattern;
        //if number lower then 0.001 show in engi mode
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

                input = input.setScale(displayScale, ROUND_HALF_UP);
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
