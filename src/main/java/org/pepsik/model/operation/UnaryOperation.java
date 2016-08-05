package org.pepsik.model.operation;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * This enum represents unary operation. Each constant consist correspond string presentation and logic to operate.
 */
public enum UnaryOperation {
    SQUARE("X²") {
        @Override
        public BigDecimal execute(BigDecimal value) {
            return value.multiply(value).setScale(15, RoundingMode.UP);
        }
    },
    SQRT("√") {
        @Override
        public BigDecimal execute(BigDecimal value) {
            return BigDecimal.valueOf(StrictMath.sqrt(value.doubleValue()));
        }
    },
    PERCENT("%") {
        @Override
        public BigDecimal execute(BigDecimal value) {
            throw new RuntimeException("UNSUPPORTED % OPERATION!");
        }
    },
    FRACTION("1/X") {
        @Override
        public BigDecimal execute(BigDecimal value) {
            return new BigDecimal("1").divide(value, 18, RoundingMode.UP);
        }
    },
    NEGATE("+-") {
        @Override
        public BigDecimal execute(BigDecimal value) {
            return value.negate();
        }
    };

    private String operator;

    UnaryOperation(String operator) {
        this.operator = operator;
    }

    /**
     * Finds unary operation by string
     * @param value String represent operation
     * @return unary operation
     */
    public static UnaryOperation find(String value) {
        for (UnaryOperation u : values()) {
            if (u.getOperator().equals(value))
                return u;
        }

        throw new IllegalArgumentException("Unary operator not found!" + value);
    }

    /**
     * Executes operation on value
     * @param value input value
     * @return result
     */
    public abstract BigDecimal execute(BigDecimal value);

    public String getOperator() {
        return operator;
    }

}
