package org.pepsik.model.operation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * This enum represents unary operation. Each constant consist correspond string presentation and logic to operate.
 */
public enum UnaryOperation {
    SQUARE("sqr") {
        @Override
        public BigDecimal execute(BigDecimal value) {
            return value.multiply(value).setScale(Constants.SCALE, RoundingMode.HALF_UP);
        }
    },
    SQRT("√") {
        @Override
        public BigDecimal execute(BigDecimal value) {
            return sqrt(value, MathContext.DECIMAL128);
        }
    },
    FRACTION("1/") {
        @Override
        public BigDecimal execute(BigDecimal value) {
            return new BigDecimal(BigInteger.ONE).divide(value, Constants.SCALE, RoundingMode.HALF_UP);
        }
    },
    NEGATE("negate") {
        @Override
        public BigDecimal execute(BigDecimal value) {
            return value.negate();
        }
    },
    PERCENT("%") {
        @Override
        public BigDecimal execute(BigDecimal value) {
            BigDecimal result = operand.multiply(value.divide(new BigDecimal(100), Constants.SCALE, BigDecimal.ROUND_HALF_UP));
            operand = null;
            return result;
        }
    };

    /**
     * Scale for unary operation constant
     */
    private static class Constants {
        private static final int SCALE = 100;
    }
    /**
     * String representation of operator
     */
    private String operator;

    /**
     * Operand which operate to. Used only for PERCENT operation
     */
    private static BigDecimal operand;

    /**
     * Sets operand for percent calculation (first value which is calculated from the percentage)
     *
     * @param operand operand which operate to
     */
    public static void setOperand(BigDecimal operand) {
        UnaryOperation.operand = operand;
    }

    /**
     * Finds unary operation by string
     *
     * @param value String represent operation
     * @return unary operation or null if no such found
     */
    public static UnaryOperation find(String value) {
        for (UnaryOperation u : values()) {
            if (u.getOperator().equals(value))
                return u;
        }
        return null;
    }

    UnaryOperation(String operator) {
        this.operator = operator;
    }

    /**
     * Calculates sqrt operation for enum constant SQRT
     * @param x input value to operate
     * @param mc context
     * @return operation result
     */
    private static BigDecimal sqrt(BigDecimal x, MathContext mc) {
        BigDecimal TWO = BigDecimal.valueOf(2L);
        BigDecimal g = x.divide(TWO, mc);
        boolean done = false;
        final int maxIterations = mc.getPrecision() + 1;
        for (int i = 0; !done && i < maxIterations; i++) {
            // r = (x/g + g) / 2
            BigDecimal r = x.divide(g, mc);
            r = r.add(g);
            r = r.divide(TWO, mc);
            done = r.equals(g);
            g = r;
        }
        return g;
    }

    /**
     * Executes operation on value
     *
     * @param value input value
     * @return result
     */
    public abstract BigDecimal execute(BigDecimal value);

    public String getOperator() {
        return operator;
    }
}
