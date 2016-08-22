package org.pepsik.model.operation;

import org.pepsik.model.Model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

/**
 * This enum represents unary operation. Each constant consist correspond string presentation and logic to operate.
 * Used double model scale
 */
public enum UnaryOperation {
    SQUARE {
        @Override
        public BigDecimal execute(BigDecimal value) {
            return value.multiply(value).setScale(Model.SCALE * 2, RoundingMode.HALF_UP);
        }
    },
    SQUARE_ROOT {
        @Override
        public BigDecimal execute(BigDecimal value) {
            return sqrt(value, MathContext.DECIMAL128);
        }
    },
    FRACTION {
        @Override
        public BigDecimal execute(BigDecimal value) {
            return ONE.divide(value, Model.SCALE * 2, RoundingMode.HALF_UP);
        }
    },
    NEGATE {
        @Override
        public BigDecimal execute(BigDecimal value) {
            return value.negate();
        }
    },
    PERCENT {
        @Override
        public BigDecimal execute(BigDecimal value) {
            throw new RuntimeException("PERCENT NOT EXECUTES HERE");
        }
    };

    /**
     * Support value for sqrt calculation
     */
    private static final BigDecimal TWO = BigDecimal.valueOf(2);

    /**
     * Calculates sqrt operation for enum constant SQRT
     *
     * @param x  input SCALE to operate
     * @param mc context
     * @return operation result
     */
    private static BigDecimal sqrt(BigDecimal x, MathContext mc) {
        if (x.equals(ZERO)) {
            return ZERO;
        }

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
     * Executes operation on SCALE
     *
     * @param value input SCALE
     * @return result
     */
    public abstract BigDecimal execute(BigDecimal value);
}
