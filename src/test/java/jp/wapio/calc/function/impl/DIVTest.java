package jp.wapio.calc.function.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import jp.wapio.calc.CalculationContext;
import jp.wapio.calc.CalculationException;
import jp.wapio.calc.config.Config;

class DIVTest {

    @ParameterizedTest
    @MethodSource("parameters")
    void test(Object a, Object b, Object expected) {
        CalculationContext context = new CalculationContext(new Config());
        context.stack().push(a);
        context.stack().push(b);
        DIV.getInstance().apply(context);
        assertEquals(expected, context.stack().pop());
    }

    static Stream<Arguments> parameters() {
        return Stream.of(
                    arguments(new BigDecimal("0"), new BigDecimal("1"), new BigDecimal("0.0000")),
                    arguments(new BigDecimal("1"), new BigDecimal("1"), new BigDecimal("1.0000")),
                    arguments(new BigDecimal("4"), new BigDecimal("2"), new BigDecimal("2.0000")),
                    arguments(new BigDecimal("2"), new BigDecimal("4"), new BigDecimal("0.5000")),
                    arguments(new BigDecimal("-1"), new BigDecimal("4"), new BigDecimal("-0.2500")),
                    arguments(new BigDecimal("1"), new BigDecimal("-2"), new BigDecimal("-0.5000")),
                    arguments(new BigDecimal("-1"), new BigDecimal("-2"), new BigDecimal("0.5000")),
                    arguments(new BigDecimal("2.0"), new BigDecimal("0.2"), new BigDecimal("10.0000")),
                    arguments(new BigDecimal("1.0"), new BigDecimal("3.0"), new BigDecimal("0.3333"))
                );
    }

    @ParameterizedTest
    @MethodSource("exParameter")
    void testException(Object a, Object b, Class<? extends Exception> expected, String message) {
        CalculationContext context = new CalculationContext(new Config());
        context.stack().push(a);
        context.stack().push(b);
        Assertions.assertThrowsExactly(expected, () -> DIV.getInstance().apply(context), message);
    }

    static Stream<Arguments> exParameter() {
        return Stream.of(
                    arguments(new BigDecimal("1"), new BigDecimal("0"), CalculationException.class, "/ by zero")
                );
    }

}
