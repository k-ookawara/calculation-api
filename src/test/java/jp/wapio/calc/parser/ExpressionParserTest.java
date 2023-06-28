package jp.wapio.calc.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class ExpressionParserTest {

    @Test
    void test() {
        assertEquals(List.of("12345"), new ExpressionParser("12345").parse());
        assertEquals(List.of("12345"), new ExpressionParser("12345").parse());
        assertEquals(List.of("1.2"), new ExpressionParser("1.2").parse());
        assertEquals(List.of("-12345"), new ExpressionParser("-12345").parse());
        assertEquals(List.of("1", "2", "&ADD"), new ExpressionParser("1 + 2").parse());
        assertEquals(List.of("1", "2", "&ADD", "3", "&SUB"), new ExpressionParser("1 + 2 - 3 ").parse());
        assertEquals(List.of("1", "2", "3", "&SUB", "&ADD"), new ExpressionParser("(1 + (2 - 3))").parse());
        assertEquals(List.of("1", "4", "&ADD", "2", "3", "&ADD", "&SUB"), new ExpressionParser("(1 + 4) -(2 + 3)").parse());
        assertEquals(List.of("1", "3", "&MUL"), new ExpressionParser("(1 * 3)").parse());
        assertEquals(List.of("1", "3", "&MUL", "2", "&ADD"), new ExpressionParser("(1 * 3 + 2)").parse());
        assertEquals(List.of("1", "3", "2", "&DIV", "&ADD"), new ExpressionParser("(1 + 3 / 2)").parse());
        assertEquals(List.of("1", "3", "&MUL", "2", "&DIV"), new ExpressionParser("(1 * 3 / 2)").parse());
        assertEquals(List.of("1", "3", "2", "&ADD", "&MUL"), new ExpressionParser("1 * (3 + 2)").parse());
        assertEquals(List.of("1", "-2", "&SUB", "3", "&SUB"), new ExpressionParser("1 - -2 - 3)").parse());
        assertEquals(List.of("\"A\""), new ExpressionParser("\"A\"").parse());
        assertEquals(List.of("\"A\"", "\"B\"", "&ADD"), new ExpressionParser("\"A\" + \"B\"").parse());
        assertEquals(List.of("1.114", "2", "&RND"), new ExpressionParser("&RND(1.114, 2)").parse());
        assertEquals(List.of("1.114", "3", "&ADD", "2", "1", "&ADD", "&RND"), new ExpressionParser("&RND(1.114 + 3, 2 + 1)").parse());
        assertEquals(List.of("1", "2", "&EQ", "@2", "3", "%1", "4"), new ExpressionParser("&IF(1 = 2, 3, 4)").parse());
        assertEquals(
                List.of("1", "2", "&EQ", "@8", "10", "20", "&NE", "@2", "30", "%1", "40", "%7", "10", "20", "&NE", "@2", "30", "%1", "40"),
                new ExpressionParser("&IF(1 = 2, &IF(10 != 20, 30, 40), &IF(10 != 20, 30, 40))").parse());
    }
}
