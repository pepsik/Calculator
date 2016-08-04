package org.pepsik.model.operation;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by Berezovyi Aleksandr on 8/2/2016.
 */
public enum UnaryOperation { //todo finish
    SQUARE("X²") {
        @Override
        public BigDecimal execute(BigDecimal value) {
            return value.multiply(value);
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
            return new BigDecimal("1").divide(value);
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

    public abstract BigDecimal execute(BigDecimal value);

    public String getOperator() {
        return operator;
    }
}
