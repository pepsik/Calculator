package org.pepsik.controller.util;

import org.pepsik.model.Stage;
import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Deque;

/**
 * Util class for format display output
 */
public class TextFormatter {

    /**
     * Formats history
     *
     * @param expression list of stages to format
     * @param curOperand current input operand in expression
     * @return String represents expression
     */
    public static String history(Deque<Stage> expression, BigDecimal curOperand) {
        int scale = 16;
        StringBuilder sb = new StringBuilder();

        //iterate though expression stages and assemble string to show on display
        for (Stage stage : expression) {
            BigDecimal operand = stage.getOperand();
            BinaryOperation operator = stage.getBinaryOperator();

            //adds binary operation if exist
            if (operator != null) {
                sb.append(" ");
                sb.append(stage.getBinaryOperator().getOperator());
            }

            //if we get first stage without operator add space
            if (operand != null) {
                if (sb.length() != 0) {
                    sb.append(" ");
                }

                //adds unary operations if exist
                if (stage.getUnaryOperators().isEmpty()) {
                    sb.append(display(stage.getOperand(), scale));
                } else {
                    // separately collect unary operators, not collect unary operators before PERCENT
                    StringBuilder unarySB = new StringBuilder();
                    boolean isPercent = false; //last unary percent flag

                    for (UnaryOperation unary : stage.getUnaryOperators()) {
                        //if get percent then clear all previous recorded unary operations
                        if (unary.equals(UnaryOperation.PERCENT)) {
                            isPercent = true;
                            unarySB = new StringBuilder(); //clear all recorded unary
                            unarySB.append(display(curOperand, scale));
                        } else {
                            isPercent = false;
                            unarySB.append(unary.getOperator().toLowerCase());
                            unarySB.append("(");
                        }
                    }

                    //checks if we get percent as last unary
                    if (isPercent) {
                        sb.append(unarySB);
                    } else {
                        sb.append(unarySB);
                        sb.append(display(stage.getOperand(), scale));
                        sb.append(")");
                    }
                }
            }
        }


        return sb.toString();
    }

    /**
     * Format display output
     *
     * @param input SCALE to display
     * @param scale SCALE scale
     * @return formatted string
     */
    public static String display(BigDecimal input, int scale) {
        DecimalFormat f = new DecimalFormat();
        input = input.stripTrailingZeros();

        //if number lower then 0.00 show in engi mode
        if (input.abs().compareTo(new BigDecimal("0.001")) == -1 && input.scale() > scale) {
            f.applyPattern("0.###############E0");
            return f.format(input);
        }

        //if number have more then scale count digits before point then show in engi mode
        if (input.precision() - input.scale() > scale) {
            String pattern = "0.";

            if (input.scale() > 0) {
                for (int i = 0; i < input.scale(); i++) {
                    pattern += "0";
                }
            } else {
                pattern += "###############";
            }
            pattern += "E0";
            f.applyPattern(pattern);
            return f.format(input);
        }

        if (input.precision() - input.scale() <= scale) {
            //show not more then scale count digits on display
            String pattern = "###,###.#";
            for (int i = 0; i < scale - input.precision() + input.scale(); i++) {
                pattern += "#";
            }
            f.applyPattern(pattern);
            return f.format(input.setScale(scale, BigDecimal.ROUND_HALF_UP));
        }

        return input.toPlainString();
    }

    /**
     * Formats input number
     *
     * @return formatted input number to display
     */
    public static String formatInputNumber() {
        DecimalFormat f = new DecimalFormat();
        int scale = InputNumber.getScale();
        String pattern = "###,##0.";

        if (scale != 0) {
            for (int i = 0; i < scale - 1; i++) {
                pattern += "0";
            }
            f.applyPattern(pattern);
        } else {
            f.applyPattern("###,###");
        }

        return f.format(InputNumber.getInput());
    }
}
