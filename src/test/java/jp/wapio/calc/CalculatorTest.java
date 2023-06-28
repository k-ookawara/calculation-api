package jp.wapio.calc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import jp.wapio.calc.config.Config;

class CalculatorTest {

    @Test
    void 文字列結合() {
        Config config = new Config();
        Expression expr = new Expression("1", List.of("1", "\"2\"", "ABC", "&ADD", "&ADD", "\"\"", "&ADD"));
        CalculationContext context = new CalculationContext(config);
        new Calculator().calculate(context, expr);
        assertEquals("12ABC", context.stack().popAsString());
        assertTrue(context.stack().isEmpty());
    }

    @Test
    void 四則演算() {
        Config config = new Config();
        Expression expr = new Expression("expr_id", List.of(new BigDecimal("1"), new BigDecimal("9"), "&ADD",
                new BigDecimal("3"), new BigDecimal("4"), "&SUB", "&MUL", new BigDecimal("0.25"), "&DIV"));
        CalculationContext context = new CalculationContext(config);
        new Calculator().calculate(context, expr);
        assertEquals("-40", context.stack().popAsString());
        assertTrue(context.stack().isEmpty());
    }

    @Test
    void 変数を使った四則演算() {
        Config config = new Config();
        Expression expr = new Expression("expr_id", List.of("$price1", "$price2", "&ADD", "$discount1", "&SUB",
                "$sales_tax", "&MUL", "$discount2", "&DIV"));
        Map<String, Object> variables = Map.of("$price1", new BigDecimal("2000"), "$price2", new BigDecimal("1000"),
                "$discount1", new BigDecimal("400"), "$sales_tax", new BigDecimal("0.9"), "$discount2",
                new BigDecimal("2"));
        CalculationContext context = new CalculationContext(config, variables);
        new Calculator().calculate(context, expr);
        assertEquals("1170", context.stack().popAsString());
        assertTrue(context.stack().isEmpty());
    }

    @Test
    void 変数を使った文字列結合() {
        Config config = new Config();
        Expression expr = new Expression("expr_id",
                List.of("$都道府県", "$市町村", "&ADD", "$丁目", "&ADD", "$番地", "&ADD", "$建物名", "&ADD", "$部屋番号", "&ADD"));
        Map<String, Object> variables = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("$都道府県", "東京都");
                put("$市町村", "千代田区");
                put("$丁目", "〇〇〇");
                put("$番地", 1);
                put("$建物名", "");
                put("$部屋番号", null);
            }
        };
        CalculationContext context = new CalculationContext(config, variables);
        new Calculator().calculate(context, expr);
        assertEquals("東京都千代田区〇〇〇1null", context.stack().popAsString());
        assertTrue(context.stack().isEmpty());
    }

    @Test
    void IFEQ() {
        // EQ
        Calculator calculator = new Calculator();
        CalculationContext context = new CalculationContext(new Config());

        calculator.calculate(context, new Expression("expressionId", List.of("1", "1", "&EQ")));
        assertTrue(context.stack().popAsBoolean());

        calculator.calculate(context, new Expression("expressionId", List.of("1", "0", "&EQ")));
        assertFalse(context.stack().popAsBoolean());

        calculator.calculate(context, new Expression("expressionId", List.of(
                "1",
                "1",
                "&EQ",
                "@1",
                "A",
                "%1",
                "B")));
        assertEquals("A", context.stack().pop());
        calculator.calculate(context, new Expression("expressionId", List.of(
                "1",
                "0",
                "&EQ",
                "@2",
                "A",
                "%1",
                "B")));
        assertEquals("B", context.stack().pop());
    }
}
