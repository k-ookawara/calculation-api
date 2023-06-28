package jp.wapio.calc.function.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import jp.wapio.calc.CalculationContext;
import jp.wapio.calc.NullValue;
import jp.wapio.calc.config.Config;
import jp.wapio.calc.function.Functions;

class IFTest {

    @ParameterizedTest
    @MethodSource("parameters")
    void test(Functions.Type type, Object a, Object b, Boolean expected) {
        CalculationContext context = new CalculationContext(new Config());
        context.stack().push(a);
        context.stack().push(b);
        IF.getInstance(type).apply(context);
        assertEquals(expected, context.stack().pop());
    }

    static Stream<Arguments> parameters() {
        return Stream.of(
                    // EQ
                    arguments(Functions.Type.EQ, NullValue.INSTANCE, NullValue.INSTANCE, true),
                    arguments(Functions.Type.EQ, NullValue.INSTANCE, "", false),
                    arguments(Functions.Type.EQ, "", NullValue.INSTANCE, false),
                    arguments(Functions.Type.EQ, "a", "a", true),
                    arguments(Functions.Type.EQ, "a", "b", false),
                    arguments(Functions.Type.EQ, "b", "a", false),
                    arguments(Functions.Type.EQ, new BigDecimal("0"), new BigDecimal("0"), true),
                    arguments(Functions.Type.EQ, new BigDecimal("0"), new BigDecimal("1"), false),
                    arguments(Functions.Type.EQ, new BigDecimal("-1"), new BigDecimal("0"), false),
                    arguments(Functions.Type.EQ, new BigDecimal("-1"), new BigDecimal("-1"), true),
                    arguments(Functions.Type.EQ, new BigDecimal("0.1"), new BigDecimal("0.1"), true),
                    arguments(Functions.Type.EQ, new BigDecimal("0.1"), new BigDecimal("0.10"), true),
                    arguments(Functions.Type.EQ, new BigDecimal("+0.1"), new BigDecimal("0.1"), true),
                    // NE
                    arguments(Functions.Type.NE, NullValue.INSTANCE, NullValue.INSTANCE, false),
                    arguments(Functions.Type.NE, NullValue.INSTANCE, "", true),
                    arguments(Functions.Type.NE, "", NullValue.INSTANCE, true),
                    arguments(Functions.Type.NE, "a", "a", false),
                    arguments(Functions.Type.NE, "a", "b", true),
                    arguments(Functions.Type.NE, "b", "a", true),
                    arguments(Functions.Type.NE, new BigDecimal("0"), new BigDecimal("0"), false),
                    arguments(Functions.Type.NE, new BigDecimal("0"), new BigDecimal("1"), true),
                    arguments(Functions.Type.NE, new BigDecimal("-1"), new BigDecimal("0"), true),
                    arguments(Functions.Type.NE, new BigDecimal("-1"), new BigDecimal("-1"), false),
                    arguments(Functions.Type.NE, new BigDecimal("0.1"), new BigDecimal("0.1"), false),
                    arguments(Functions.Type.NE, new BigDecimal("0.1"), new BigDecimal("0.10"), false),
                    arguments(Functions.Type.NE, new BigDecimal("+0.1"), new BigDecimal("0.1"), false),
                    // GT
                    arguments(Functions.Type.GT, NullValue.INSTANCE, NullValue.INSTANCE, false),
                    arguments(Functions.Type.GT, NullValue.INSTANCE, "", true),
                    arguments(Functions.Type.GT, "", NullValue.INSTANCE, false),
                    arguments(Functions.Type.GT, "a", "a", false),
                    arguments(Functions.Type.GT, "a", "b", false),
                    arguments(Functions.Type.GT, "b", "a", true),
                    arguments(Functions.Type.GT, new BigDecimal("0"), new BigDecimal("0"), false),
                    arguments(Functions.Type.GT, new BigDecimal("0"), new BigDecimal("1"), false),
                    arguments(Functions.Type.GT, new BigDecimal("0"), new BigDecimal("-1"), true),
                    arguments(Functions.Type.GT, new BigDecimal("-1"), new BigDecimal("-1"), false),
                    arguments(Functions.Type.GT, new BigDecimal("0.1"), new BigDecimal("0.1"), false),
                    arguments(Functions.Type.GT, new BigDecimal("0.1"), new BigDecimal("0.10"), false),
                    arguments(Functions.Type.GT, new BigDecimal("+0.1"), new BigDecimal("0.1"), false),
                    // GE
                    arguments(Functions.Type.GE, NullValue.INSTANCE, NullValue.INSTANCE, true),
                    arguments(Functions.Type.GE, NullValue.INSTANCE, "", true),
                    arguments(Functions.Type.GE, "", NullValue.INSTANCE, false),
                    arguments(Functions.Type.GE, "a", "a", true),
                    arguments(Functions.Type.GE, "a", "b", false),
                    arguments(Functions.Type.GE, "b", "a", true),
                    arguments(Functions.Type.GE, new BigDecimal("0"), new BigDecimal("0"), true),
                    arguments(Functions.Type.GE, new BigDecimal("0"), new BigDecimal("1"), false),
                    arguments(Functions.Type.GE, new BigDecimal("0"), new BigDecimal("-1"), true),
                    arguments(Functions.Type.GE, new BigDecimal("-1"), new BigDecimal("-1"), true),
                    arguments(Functions.Type.GE, new BigDecimal("0.1"), new BigDecimal("0.1"), true),
                    arguments(Functions.Type.GE, new BigDecimal("0.1"), new BigDecimal("0.10"), true),
                    arguments(Functions.Type.GE, new BigDecimal("+0.1"), new BigDecimal("0.1"), true),
                    // LS
                    arguments(Functions.Type.LS, NullValue.INSTANCE, NullValue.INSTANCE, false),
                    arguments(Functions.Type.LS, NullValue.INSTANCE, "", false),
                    arguments(Functions.Type.LS, "", NullValue.INSTANCE, true),
                    arguments(Functions.Type.LS, "a", "a", false),
                    arguments(Functions.Type.LS, "a", "b", true),
                    arguments(Functions.Type.LS, "b", "a", false),
                    arguments(Functions.Type.LS, new BigDecimal("0"), new BigDecimal("0"), false),
                    arguments(Functions.Type.LS, new BigDecimal("0"), new BigDecimal("1"), true),
                    arguments(Functions.Type.LS, new BigDecimal("0"), new BigDecimal("-1"), false),
                    arguments(Functions.Type.LS, new BigDecimal("-1"), new BigDecimal("-1"), false),
                    arguments(Functions.Type.LS, new BigDecimal("0.1"), new BigDecimal("0.1"), false),
                    arguments(Functions.Type.LS, new BigDecimal("0.1"), new BigDecimal("0.10"), false),
                    arguments(Functions.Type.LS, new BigDecimal("+0.1"), new BigDecimal("0.1"), false),
                    // LE
                    arguments(Functions.Type.LE, NullValue.INSTANCE, NullValue.INSTANCE, true),
                    arguments(Functions.Type.LE, NullValue.INSTANCE, "", false),
                    arguments(Functions.Type.LE, "", NullValue.INSTANCE, true),
                    arguments(Functions.Type.LE, "a", "a", true),
                    arguments(Functions.Type.LE, "a", "b", true),
                    arguments(Functions.Type.LE, "b", "a", false),
                    arguments(Functions.Type.LE, new BigDecimal("0"), new BigDecimal("0"), true),
                    arguments(Functions.Type.LE, new BigDecimal("0"), new BigDecimal("1"), true),
                    arguments(Functions.Type.LE, new BigDecimal("0"), new BigDecimal("-1"), false),
                    arguments(Functions.Type.LE, new BigDecimal("-1"), new BigDecimal("-1"), true),
                    arguments(Functions.Type.LE, new BigDecimal("0.1"), new BigDecimal("0.1"), true),
                    arguments(Functions.Type.LE, new BigDecimal("0.1"), new BigDecimal("0.10"), true),
                    arguments(Functions.Type.LE, new BigDecimal("+0.1"), new BigDecimal("0.1"), true)
                );
    }
}
