package jp.wapio.calc.function.impl;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import jp.wapio.calc.CalculationContext;
import jp.wapio.calc.CalculationException;
import jp.wapio.calc.NullValue;
import jp.wapio.calc.function.Functions;

public class IF extends AbstractFunction {

    private Predicate<Integer> function;

    private IF(Predicate<Integer> function) {
        this.function = function;
    }

    private static class InstanceHolder {
        private static final Map<Functions.Type, IF> INSTANCES = new EnumMap<>(Functions.Type.class);

        static {
            INSTANCES.put(Functions.Type.EQ, new IF(n -> n == 0));
            INSTANCES.put(Functions.Type.NE, new IF(n -> n != 0));
            INSTANCES.put(Functions.Type.GT, new IF(n -> n > 0));
            INSTANCES.put(Functions.Type.GE, new IF(n -> n >= 0));
            INSTANCES.put(Functions.Type.LS, new IF(n -> n < 0));
            INSTANCES.put(Functions.Type.LE, new IF(n -> n <= 0));
        }
    }

    public static IF getInstance(Functions.Type type) {
        if (InstanceHolder.INSTANCES.containsKey(type)) {
            return InstanceHolder.INSTANCES.get(type);
        } else {
            throw new IllegalArgumentException("function type does not exist.");
        }
    }

    @Override
    public void apply(CalculationContext context) {
        try {
            Object arg2 = context.stack().pop();
            Object arg1 = context.stack().pop();
            int comparisonResult;
            Boolean result;
            if (arg1 instanceof NullValue && arg2 instanceof NullValue) {
                comparisonResult = 0;
            } else if (arg1 instanceof NullValue) {
                comparisonResult = 1;
            } else if (arg2 instanceof NullValue) {
                comparisonResult = -1;
            } else {
                if (arg1 instanceof String a && arg2 instanceof String b) {
                    comparisonResult = a.compareTo(b);
                } else if (arg1 instanceof BigDecimal a && arg2 instanceof BigDecimal b) {
                    comparisonResult = a.compareTo(b);
                } else if (arg1 instanceof Boolean a && arg2 instanceof Boolean b) {
                    comparisonResult = a.compareTo(b);
                } else {
                    throw new CalculationException("comparing different data types is not supported.");
                }
            }
            result = function.test(comparisonResult);
            context.stack().push(result);
        } catch (NoSuchElementException e) {
            throw new CalculationException(e);
        }
    }
}
