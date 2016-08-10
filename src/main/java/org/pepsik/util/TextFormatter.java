package org.pepsik.util;

import javafx.scene.control.Label;
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

    private static Label display;

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

                    for (UnaryOperation unary : stage.getUnaryOperators()) {
                        if (unary.equals(UnaryOperation.PERCENT)) {
                            unarySB = new StringBuilder();
                            UnaryOperation.setOperand(new BigDecimal(display.getText()).stripTrailingZeros());
                            unarySB.append(UnaryOperation.PERCENT.execute(stage.getOperand()));
                        } else {
                            unarySB.append(unary.getOperator().toLowerCase());
                            unarySB.append("(");
                        }
                    }

                    sb.append(unarySB.toString());

                    if (unarySB.length() == 0) {
                        sb.append(stage.getOperand());
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
     * @param input value to display
     * @param scale value scale
     * @return formatted string
     */
    public static String display(BigDecimal input, int scale) {
        DecimalFormat f = new DecimalFormat();

        //if number lower then 0.00 show in engi mode
        if (input.abs().doubleValue() < 0.001 && input.doubleValue() != 0) {
            f.applyPattern("0.0E0");
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

    public static void setDisplay(Label display) {
        TextFormatter.display = display;
    }
}
