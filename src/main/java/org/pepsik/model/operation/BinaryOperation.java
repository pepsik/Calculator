package org.pepsik.model.operation;

import org.pepsik.model.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This enum represents binary operation. Each constant consist correspond string presentation and logic to operate.
 * Used double model scale
 */
public enum BinaryOperation {
    ADD {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            return f.add(s);
        }
    },
    SUBTRACT {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            return f.subtract(s);
        }
    },
    DIVIDE {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            return f.divide(s, Model.SCALE * 2, BigDecimal.ROUND_HALF_UP);
        }
    },
    MULTIPLY {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            return f.multiply(s).setScale(Model.SCALE * 2, RoundingMode.HALF_UP);
        }
    },
    EQUAL {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            throw new RuntimeException("EQUAL NOT EXECUTES HERE");
        }
    };

    /**
     * Executes operation with 2 values
     *
     * @param f first SCALE
     * @param s second SCALE
     * @return result operation
     */
    public abstract BigDecimal execute(BigDecimal f, BigDecimal s);
}
