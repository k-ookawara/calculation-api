package jp.wapio.calc.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TokenizerTest {

    @ParameterizedTest
    @MethodSource("parameters")
    void test(String string, String[] expecteds) {
        var tokenizer = new Tokenizer(new Characters(string));
        for (String expected : expecteds) {
            assertEquals(expected, tokenizer.next());
        }
        assertFalse(tokenizer.hasNext());
    }

    static Stream<Arguments> parameters() {
        return Stream.of(
                arguments(
                        "\"AAA\"1234.1234$AZaz_09&AZaz_09+-*/=!=<>()<=>=",
                        new String[] {
                                "\"AAA\"",
                                "1234.1234",
                                "$AZaz_09",
                                "&AZaz_09",
                                "+",
                                "-",
                                "*",
                                "/",
                                "=",
                                "!=",
                                "<",
                                ">",
                                "(",
                                ")",
                                "<=",
                                ">="
                        })
                );
    }

}
