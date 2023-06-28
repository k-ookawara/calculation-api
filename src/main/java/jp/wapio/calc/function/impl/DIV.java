package jp.wapio.calc.function.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;

import jp.wapio.calc.CalculationContext;
import jp.wapio.calc.CalculationException;
import jp.wapio.calc.config.ConfigType;

public class DIV extends AbstractFunction {

    private DIV() {
    }

    private static class InstanceHolder {
        private static DIV Instance = new DIV();
    }

    public static DIV getInstance() {
        return InstanceHolder.Instance;
    }

    public void apply(CalculationContext context) {
        try {
            BigDecimal arg2 = asBigDecimal(context.stack().pop());
            BigDecimal arg1 = asBigDecimal(context.stack().pop());
            Integer precision = context.config().getAsInteger(ConfigType.ROUNDING_PRECISION.getKey());
            BigDecimal result = arg1.divide(arg2, precision, RoundingMode.DOWN);
            context.stack().push(result);
        } catch (NoSuchElementException | ArithmeticException e) {
            throw new CalculationException(e);
        }
    }
}
