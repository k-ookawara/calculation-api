package jp.wapio.calc.function.impl;

import java.math.BigDecimal;

import jp.wapio.calc.CalculationException;
import jp.wapio.calc.NullValue;
import jp.wapio.calc.function.Function;

public abstract class AbstractFunction implements Function, Cloneable {

    public String asString(Object o) {
        if (o instanceof NullValue) {
            return null;
        } else if (o instanceof String s) {
            return s;
        } else if (o instanceof BigDecimal b) {
            return b.stripTrailingZeros().toPlainString();
        } else if (o instanceof Number n) {
            return String.valueOf(n);
        } else {
            throw new CalculationException("Argument is invalid.");
        }
    }

    public BigDecimal asBigDecimal(Object o) {
        if (o instanceof NullValue) {
            return null;
        } else if (o instanceof String s) {
            return new BigDecimal(s);
        } else if (o instanceof BigDecimal b) {
            return b;
        } else if (o instanceof Number n) {
            return new BigDecimal(String.valueOf(n));
        } else {
            throw new CalculationException("Argument is invalid.");
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
