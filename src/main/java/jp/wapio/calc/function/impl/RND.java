package jp.wapio.calc.function.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

import jp.wapio.calc.CalculationContext;
import jp.wapio.calc.CalculationException;
import jp.wapio.calc.function.Functions;

public class RND extends AbstractFunction {

    private BiFunction<BigDecimal, Integer, BigDecimal> function;

    private RND(BiFunction<BigDecimal, Integer, BigDecimal> function) {
        this.function = function;
    }

    private static class InstanceHolder {

        private static final Map<Functions.Type, RND> INSTANCES = new EnumMap<>(Functions.Type.class);

        static {
            INSTANCES.put(Functions.Type.RND, new RND((n, scale) -> n.setScale(scale, RoundingMode.HALF_UP)));
            INSTANCES.put(Functions.Type.RNDU, new RND((n, scale) -> n.setScale(scale, RoundingMode.UP)));
            INSTANCES.put(Functions.Type.RNDD, new RND((n, scale) -> n.setScale(scale, RoundingMode.DOWN)));
        }
    }

    public static RND getInstance(Functions.Type type) {
        if (InstanceHolder.INSTANCES.containsKey(type)) {
            return InstanceHolder.INSTANCES.get(type);
        } else {
            throw new IllegalArgumentException("function type does not exist.");
        }
    }

    @Override
    public void apply(CalculationContext context) {
        try {
            int scale = asBigDecimal(context.stack().pop()).intValue();
            BigDecimal value = asBigDecimal(context.stack().pop());
            BigDecimal result = function.apply(value, scale);
            context.stack().push(result);
        } catch (NoSuchElementException e) {
            throw new CalculationException(e);
        }
    }
}
