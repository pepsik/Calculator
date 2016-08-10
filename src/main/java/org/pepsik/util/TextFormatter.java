package org.pepsik.util;

import org.pepsik.model.Model;
import org.pepsik.model.Stage;
import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Deque;

/**
 * Util class for format display output
 */
public class TextFormatter {

    private static Model model;

    /**
     * Formats history
     *
     * @param expression list of stages to format
     * @return String represents expression
     */
    public static String history(Deque<Stage> expression) {
        int scale = 16;
        StringBuilder sb = new StringBuilder();

        for (Stage stage : expression) {
            BigDecimal operand = stage.getOperand();
            BinaryOperation operator = stage.getBinaryOperator();

            //adds binary operation if exist
            if (operator != null) {
                sb.append(" ");
                sb.append(stage.getBinaryOperator().getOperator());
            }

            if (operand != null) {
                if (sb.length() != 0) {
                    sb.append(" ");
                }

                //adds unary operations if exist
                if (stage.getUnaryOperators().isEmpty()) {
                    sb.append(stage.getOperand().setScale(scale, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
                } else {
                    // separately collect unary operators, not collect unary operators before PERCENT
                    StringBuilder unarySB = new StringBuilder();

                    boolean isPercent = false;
                    for (UnaryOperation unary : stage.getUnaryOperators()) {
                        if (unary.equals(UnaryOperation.PERCENT)) {
                            isPercent = true;
                            unarySB = new StringBuilder();
                            unarySB.append(display(model.getOperand(), 16));
                        } else {
                            isPercent = false;
                            unarySB.append(unary.getOperator().toLowerCase());
                            unarySB.append("(");
                        }
                    }

                    if (isPercent) {
                        sb.append(unarySB);
                    } else {
                        sb.append(unarySB);
                        sb.append(stage.getOperand());
                        sb.append(")");
                    }

//                    if (unarySB.length() != 0) {
//                        unarySB.append(display(model.getOperand(), 16));
////                        sb.append(stage.getOperand());
//                        sb.append(")");
//                    }else {
//
//                    }
                }
            }
        }


        return sb.toString();
    }

    /**
     * Format display output
     *
     * @param input value to display
     * @param scale value scale
     * @return formatted string
     */
    public static String display(BigDecimal input, int scale) {
        DecimalFormat f = new DecimalFormat();

        //if number lower then 0.00 show in engi mode
        if (input.abs().doubleValue() < 0.001 && input.movePointRight(scale).doubleValue() % 1 != 0) {
            f.applyPattern("0.###############E0");
            return f.format(input);
        }

        //if number have more then scale number digits of integral number then show in engi mode
        if (input.precision() - input.scale() > scale) {
            f.applyPattern("0.################E0");
            return f.format(input);
        } else {
            f.applyPattern("###,###.################");
            return f.format(input.setScale(scale, BigDecimal.ROUND_HALF_UP));
        }
    }

    public static void setModel(Model model) {
        TextFormatter.model = model;
    }
}
