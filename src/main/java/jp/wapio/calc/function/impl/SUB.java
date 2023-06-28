package jp.wapio.calc.function.impl;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import jp.wapio.calc.CalculationContext;
import jp.wapio.calc.CalculationException;

public class SUB extends AbstractFunction {

    private SUB() {
    }

    private static class InstanceHolder {
        private static SUB Instance = new SUB();
    }

    public static SUB getInstance() {
        return InstanceHolder.Instance;
    }

    public void apply(CalculationContext context) {
        try {
            BigDecimal arg2 = asBigDecimal(context.stack().pop());
            BigDecimal arg1 = asBigDecimal(context.stack().pop());
            BigDecimal result = arg1.subtract(arg2);
            context.stack().push(result);
        } catch (NoSuchElementException e) {
            throw new CalculationException(e);
        }
    }
}
