package jp.wapio.calc.function.impl;

import java.util.NoSuchElementException;

import jp.wapio.calc.CalculationContext;
import jp.wapio.calc.CalculationException;

public class ADD extends AbstractFunction {

    private ADD() {
    }

    private static class InstanceHolder {
        private static ADD Instance = new ADD();
    }

    public static ADD getInstance() {
        return InstanceHolder.Instance;
    }

    @Override
    public void apply(CalculationContext context) {
        try {
            Object arg2 = context.stack().pop();
            Object arg1 = context.stack().pop();
            Object result;
            if (arg1 instanceof String || arg2 instanceof String) {
                result = asString(arg1) + asString(arg2);
            } else {
                result = asBigDecimal(arg1).add(asBigDecimal(arg2));
            }
            context.stack().push(result);
        } catch (NoSuchElementException e) {
            throw new CalculationException(e);
        }
    }
}
