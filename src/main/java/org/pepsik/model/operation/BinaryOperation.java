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
            return f.divide(s, 17, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
        }
    },
    MULTIPLY("*") {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            return f.multiply(s).setScale(16, RoundingMode.UP);
        }
    },
    EQUAL("=") {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            throw new RuntimeException("EQUALS NOT COMPLETE YET");
        }
    };

    private String operator;

    BinaryOperation(String operator) {
        this.operator = operator;
    }

    /**
     * Executes operation with 2 values
     * @param f first value
     * @param s second value
     * @return result operation
     */
    public abstract BigDecimal execute(BigDecimal f, BigDecimal s);

    public final String getOperator() {
        return operator;
    }

    public static boolean isExist(String str) {
        for (BinaryOperation value : values()) {
            if (value.getOperator().equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds binary operation by string
     * @param operator string represents operation
     * @return binary operation
     *
     * @throws IllegalArgumentException in case if operation not found
     */
    public static BinaryOperation find(char operator) {
        for (BinaryOperation value : values()) {
            if (value.getOperator().equals(String.valueOf(operator))) {
                return value;
            }
        }
        throw new IllegalArgumentException(" No enum constant for " + operator);
    }
}
