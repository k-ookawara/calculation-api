package jp.wapio.calc.function.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;

import jp.wapio.calc.CalculationContext;
import jp.wapio.calc.CalculationException;
import jp.wapio.calc.config.ConfigType;

public class MUL extends AbstractFunction {

    private MUL() {
    }

    private static class InstanceHolder {
        private static MUL Instance = new MUL();
    }

    public static MUL getInstance() {
        return InstanceHolder.Instance;
    }

    public void apply(CalculationContext context) {
        try {
            Integer precision = context.config().getAsInteger(ConfigType.ROUNDING_PRECISION.getKey());
            BigDecimal arg2 = asBigDecimal(context.stack().pop());
            BigDecimal arg1 = asBigDecimal(context.stack().pop());
            BigDecimal result = arg1.multiply(arg2).setScale(precision, RoundingMode.DOWN);
            context.stack().push(result);
        } catch (NoSuchElementException e) {
            throw new CalculationException(e);
        }
    }
}
