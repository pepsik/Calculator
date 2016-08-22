package org.pepsik.model.operation;

import org.pepsik.model.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This enum represents binary operation. Each constant consist correspond string presentation and logic to operate.
 * Used double model scale for calculation to track when operation result reaches limit
 * e.g. if we square a smallest value with common scale we get ZERO in BigDecimal
 */
public enum BinaryOperation {
    /**
     * Add operation
     */
    ADD {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            return f.add(s);
        }
    },
    /**
     * Subtract operation
     */
    SUBTRACT {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            return f.subtract(s);
        }
    },
    /**
     * Divide operation
     */
    DIVIDE {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            return f.divide(s, Model.SCALE * 2, BigDecimal.ROUND_HALF_UP);
        }
    },
    /**
     * Multiply operation
     */
    MULTIPLY {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            return f.multiply(s).setScale(Model.SCALE * 2, RoundingMode.HALF_UP);
        }
    },
    /**
     * Equal operation, but use Model.calculateEqual()
     *
     * @throws UnsupportedOperationException in case when called
     */
    EQUAL {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            throw new UnsupportedOperationException("equal not executes here, use Model.calculateEqual()");
        }
    };

    /**
     * Executes operation with 2 values
     *
     * @param f first operand
     * @param s second operand
     * @return result operation
     */
    public abstract BigDecimal execute(BigDecimal f, BigDecimal s);
}
