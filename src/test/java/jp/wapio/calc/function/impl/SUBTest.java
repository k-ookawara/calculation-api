package jp.wapio.calc.function.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import jp.wapio.calc.CalculationContext;
import jp.wapio.calc.config.Config;

class SUBTest {

    @ParameterizedTest
    @MethodSource("parameters")
    void test(Object a, Object b, Object expected) {
        CalculationContext context = new CalculationContext(new Config());
        context.stack().push(a);
        context.stack().push(b);
        SUB.getInstance().apply(context);
        assertEquals(expected, context.stack().pop());
    }

    static Stream<Arguments> parameters() {
        return Stream.of(
                    arguments(new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("-1")),
                    arguments(new BigDecimal("-1"), new BigDecimal("2"), new BigDecimal("-3")),
                    arguments(new BigDecimal("+1"), new BigDecimal("-2"), new BigDecimal("3")),
                    arguments(new BigDecimal("-1"), new BigDecimal("-2"), new BigDecimal("1")),
                    arguments(new BigDecimal("0.1"), new BigDecimal("0.2"), new BigDecimal("-0.1"))
                );
    }

}
