package org.pepsik.util;

import org.pepsik.model.Stage;
import org.pepsik.model.operation.BinaryOperation;
import org.pepsik.model.operation.UnaryOperation;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Deque;
import java.util.Iterator;

/**
 * Created by Berezovyi Aleksandr on 8/1/2016.
 */
public class TextFormatter {

    /**
     * Formats history
     * @param expression list of stages to format
     * @return String represents expression
     */
    public static String history(Deque<Stage> expression) {
        StringBuilder sb = new StringBuilder();
        Iterator<Stage> iterator = expression.iterator();

        if (iterator.hasNext()) {
            sb.append(iterator.next().getResultOperation());
        }

        while (iterator.hasNext()) {
            Stage stage = iterator.next();
            BigDecimal operand = stage.getOperand();
            BinaryOperation operator = stage.getBinaryOperator();

            if (operator != null) {
                sb.append(" ");
                sb.append(stage.getBinaryOperator().getOperator());
            }
            if (operand != null) {
                sb.append(" ");
                if (stage.getUnaryOperators().isEmpty()) {
                    sb.append(stage.getOperand());
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
     * @param input string represents display
     * @return formatted display string
     */
    public static String display(String input) {
        double d = Double.valueOf(input);
        if (d % 1 == 0) {
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            input = decimalFormat.format(d);
        }
        return input;
    }
}
