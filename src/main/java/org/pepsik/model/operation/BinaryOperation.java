package org.pepsik.model.operation;

import java.math.BigDecimal;

/**
 * Created by Berezovyi Aleksandr on 8/2/2016.
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
            return f.divide(s, 16, BigDecimal.ROUND_UP);
        }
    },
    MULTIPLY("*") {
        @Override
        public BigDecimal execute(BigDecimal f, BigDecimal s) {
            return f.multiply(s);
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

    public static BinaryOperation find(char operator) {
        for (BinaryOperation value : values()) {
            if (value.getOperator().equals(String.valueOf(operator))) {
                return value;
            }
        }
        throw new IllegalArgumentException(" No enum constant for " + operator);
    }
}
