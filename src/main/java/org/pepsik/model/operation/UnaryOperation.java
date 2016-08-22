package org.pepsik.model.operation;

import org.pepsik.model.exception.IllegalOperandException;
import org.pepsik.model.Model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

/**
 * This enum represents unary operation. Each constant consist correspond string presentation and logic to operate.
 * Used double model scale for calculation to track when operation result reaches limit
 * e.g. if we square a smallest value with common scale we get ZERO in BigDecimal
 */
public enum UnaryOperation {
    /**
     * Square operation
     */
    SQUARE {
        @Override
        public BigDecimal execute(BigDecimal value) {
            return value.multiply(value).setScale(Model.SCALE * 2, RoundingMode.HALF_UP);
        }
    },
    /**
     * Square root operation
     */
    SQUARE_ROOT {
        @Override
        public BigDecimal execute(BigDecimal value) throws IllegalOperandException {
            if (value.compareTo(ZERO) == -1) {
                throw new IllegalOperandException("Operand must be positive for sqrt operation but was " + value);
            } else if (value.compareTo(ZERO) == 0) {
                return ZERO;
            }
            return sqrt(value, MathContext.DECIMAL128);
        }
    },
    /**
     * Fraction operation
     */
    FRACTION {
        @Override
        public BigDecimal execute(BigDecimal value) {
            return ONE.divide(value, Model.SCALE * 2, RoundingMode.HALF_UP);
        }
    },
    /**
     * Negate operation
     */
    NEGATE {
        @Override
        public BigDecimal execute(BigDecimal value) {
            return value.negate();
        }
    },
    /**
     * Percent operation, but use Model.calculateUnary()
     *
     * @throws UnsupportedOperationException in case when called
     */
    PERCENT {
        @Override
        public BigDecimal execute(BigDecimal value) {
            throw new UnsupportedOperationException("percent not executes here, use model.calculateUnary()");
        }
    };

    /**
     * Support value for sqrt calculation
     */
    private static final BigDecimal TWO = BigDecimal.valueOf(2);

    /**
     * Calculates sqrt operation for enum constant SQRT
     * http://blog.udby.com/archives/17
     *
     * @param x  input value to operate
     * @param mc math context
     * @return operation result
     */
    private static BigDecimal sqrt(BigDecimal x, MathContext mc) {
        BigDecimal g = x.divide(TWO, mc);
        boolean done = false;
        final int maxIterations = mc.getPrecision() + 1;
        for (int i = 0; !done && i < maxIterations; i++) {
            // r = (x/g + g) / 2
            BigDecimal r = x.divide(g, mc);
            r = r.add(g);
            r = r.divide(TWO, mc);
            done = r.compareTo(g) == 0; //changed equal to compare
            g = r;
        }
        return g;
    }

    /**
     * Executes operation on operand
     *
     * @param value input operand
     * @return result
     */
    public abstract BigDecimal execute(BigDecimal value) throws IllegalOperandException;
}
