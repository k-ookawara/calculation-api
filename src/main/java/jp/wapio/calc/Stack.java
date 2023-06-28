package jp.wapio.calc;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 計算用のStackクラス。
 */
public class Stack {

    private Deque<Object> stack = new ArrayDeque<>();

    public Object pop() {
        return stack.pop();
    }

    public void push(Object obj) {
        stack.push(obj == null ? NullValue.INSTANCE : obj);
    }

    public String popAsString() {
        Object o = pop();
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

    public BigDecimal popAsBigDecimal() {
        Object o = pop();
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

    public boolean popAsBoolean() {
        Object o = pop();
        if (o instanceof Boolean b) {
            return b.booleanValue();
        } else if (o instanceof String s) {
            return Boolean.parseBoolean(s);
        } else {
            throw new CalculationException("Argument is invalid.");
        }
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
