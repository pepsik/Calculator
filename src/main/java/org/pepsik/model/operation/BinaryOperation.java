package org.pepsik.model.operation;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This enum represents binary operation. Each constant consist correspond string presentation and logic to operate.
 */
public enum BinaryOperation {
    ADD("+") {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            return f.add(s);
        }
    },
    SUBTRACT("-") {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            return f.subtract(s);
        }
    },
    DIVIDE("/") {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            return f.divide(s, Constant.SCALE, BigDecimal.ROUND_HALF_UP);
        }
    },
    MULTIPLY("*") {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            return f.multiply(s).setScale(Constant.SCALE, RoundingMode.HALF_UP);
        }
    },
    EQUAL("=") {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            throw new RuntimeException("EQUAL NOT EXECUTES HERE");
        }
    };

    /**
     * Constant for binary operation constant
     */

    /**
     * String representation of operator
     */
    private String operator;

    BinaryOperation(String operator) {
        this.operator = operator;
    }

    /**
     * Finds binary operation by string
     *
     * @param operator string represents operation
     * @return binary operation or null if no such found
     */
    public static BinaryOperation find(String operator) {
        for (BinaryOperation value : values()) {
            if (value.getOperator().equals(operator)) { //todo exlude variable
                return value;
            }
        }
        return null;
    }

    /**
     * Executes operation with 2 values
     *
     * @param f first SCALE
     * @param s second SCALE
     * @return result operation
     */
    public abstract BigDecimal execute(BigDecimal f, BigDecimal s);

    public String getOperator() {
        return operator;
    }
}
