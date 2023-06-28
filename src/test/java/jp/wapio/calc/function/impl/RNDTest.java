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
import jp.wapio.calc.function.Functions;

class RNDTest {

    @ParameterizedTest
    @MethodSource("parameters")
    void test(Functions.Type type, Object a, Object b, BigDecimal expected) {
        CalculationContext context = new CalculationContext(new Config());
        context.stack().push(a);
        context.stack().push(b);
        RND.getInstance(type).apply(context);
        assertEquals(expected, context.stack().pop());
    }

    static Stream<Arguments> parameters() {
        return Stream.of(
                // RND
                arguments(Functions.Type.RND, new BigDecimal("0.15"), "1", new BigDecimal("0.2")),
                arguments(Functions.Type.RND, new BigDecimal("0.14"), "1", new BigDecimal("0.1")),
                arguments(Functions.Type.RND, new BigDecimal("0.14"), "2", new BigDecimal("0.14")),
                arguments(Functions.Type.RND, new BigDecimal("0"), "2", new BigDecimal("0.00")),
                arguments(Functions.Type.RND, new BigDecimal("-0.15"), "1", new BigDecimal("-0.2")),
                arguments(Functions.Type.RND, new BigDecimal("-0.14"), "1", new BigDecimal("-0.1")),
                arguments(Functions.Type.RND, new BigDecimal("-0.14"), "2", new BigDecimal("-0.14")),
                arguments(Functions.Type.RND, new BigDecimal("-0"), "2", new BigDecimal("0.00")),
                // RNDU
                arguments(Functions.Type.RNDU, new BigDecimal("0.15"), "1", new BigDecimal("0.2")),
                arguments(Functions.Type.RNDU, new BigDecimal("0.14"), "1", new BigDecimal("0.2")),
                arguments(Functions.Type.RNDU, new BigDecimal("0.14"), "2", new BigDecimal("0.14")),
                arguments(Functions.Type.RNDU, new BigDecimal("0"), "2", new BigDecimal("0.00")),
                arguments(Functions.Type.RNDU, new BigDecimal("-0.15"), "1", new BigDecimal("-0.2")),
                arguments(Functions.Type.RNDU, new BigDecimal("-0.14"), "1", new BigDecimal("-0.2")),
                arguments(Functions.Type.RNDU, new BigDecimal("-0.14"), "2", new BigDecimal("-0.14")),
                arguments(Functions.Type.RNDU, new BigDecimal("-0"), "2", new BigDecimal("0.00")),
                // RNDD
                arguments(Functions.Type.RNDD, new BigDecimal("0.15"), "1", new BigDecimal("0.1")),
                arguments(Functions.Type.RNDD, new BigDecimal("0.14"), "1", new BigDecimal("0.1")),
                arguments(Functions.Type.RNDD, new BigDecimal("0.14"), "2", new BigDecimal("0.14")),
                arguments(Functions.Type.RNDD, new BigDecimal("0"), "2", new BigDecimal("0.00")),
                arguments(Functions.Type.RNDD, new BigDecimal("-0.15"), "1", new BigDecimal("-0.1")),
                arguments(Functions.Type.RNDD, new BigDecimal("-0.14"), "1", new BigDecimal("-0.1")),
                arguments(Functions.Type.RNDD, new BigDecimal("-0.14"), "2", new BigDecimal("-0.14")),
                arguments(Functions.Type.RNDD, new BigDecimal("-0"), "2", new BigDecimal("0.00"))
                );
    }
}
