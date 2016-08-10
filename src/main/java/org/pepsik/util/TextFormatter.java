package org.pepsik.util;

import org.pepsik.model.Stage;
import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Deque;
import java.util.Iterator;

/**
 * Util class for format display output
 */
public class TextFormatter {

    /**
     * Formats history
     *
     * @param expression list of stages to format
     * @return String represents expression
     */
    public static String history(Deque<Stage> expression) {
        StringBuilder sb = new StringBuilder();

        for (Stage stage : expression) {
            BigDecimal operand = stage.getOperand();
            BinaryOperation operator = stage.getBinaryOperator();

            if (operator != null) {
                sb.append(" ");
                sb.append(stage.getBinaryOperator().getOperator());
            }

            if (operand != null) {
                if (sb.length() != 0) {
                    sb.append(" ");
                }

                if (stage.getUnaryOperators().isEmpty()) {
                    sb.append(stage.getOperand().setScale(16, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
                } else {
                    for (UnaryOperation unary : stage.getUnaryOperators()) {
                        sb.append(unary.name().toLowerCase());
                        sb.append("(");
                    }

                    sb.append(stage.getOperand());
                    sb.append(")");
                }
            }
        }

        return sb.toString();
    }

    /**
     * Format display
     *
     * @param input string represents display
     * @return formatted display string
     */
    public static String display(BigDecimal result, int scale) {
        DecimalFormat f = new DecimalFormat();

        if (result.abs().doubleValue() < 0.001 && result.doubleValue() != 0) {
            f.applyPattern("0.0E0");
            return f.format(result);
        }

        if (result.precision() - result.scale() > scale) {
            f.applyPattern("0.################E0");
            return f.format(result);
        } else {
            f.applyPattern("###,###.################");
            return f.format(result.setScale(scale, BigDecimal.ROUND_HALF_UP));
        }
    }
}
