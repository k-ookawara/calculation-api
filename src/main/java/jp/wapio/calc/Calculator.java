package jp.wapio.calc;

import jp.wapio.calc.function.Functions;

/**
 * 計算実行クラス
 */
public class Calculator {

    /**
     * 式を元に計算する。<br>
     * 計算結果は以下のように取得する。<br>
     * <pre>
     * {@code
     *  new Calculator().calculate(context, expression);
     *  Object result = context.stack().pop();
     * }
     * </pre>
     * @param context 計算コンテキスト
     * @param expression 式
     */
    public void calculate(CalculationContext context, Expression expression) {
        for (int i = 0; i < expression.tokens().size(); i++) {
            Object token = expression.tokens().get(i);
            if (token instanceof String string) {
                if (string.startsWith("&")) {
                    Functions.get(string.substring(1)).apply(context);
                } else if (string.startsWith("$")) {
                    context.stack().push(context.variables().get(string));
                } else if (string.startsWith("@")) {
                    int skip = Integer.parseInt(string.substring(1));
                    if (!context.stack().popAsBoolean()) {
                        i += skip;
                    }
                } else if (string.startsWith("%")) {
                    int skip = Integer.parseInt(string.substring(1));
                    i += skip;
                } else if (string.startsWith("\"")) {
                    if (string.endsWith("\"")) {
                        context.stack().push(string.length() == 2 ? "" : string.substring(1, string.length() - 1));
                    } else {
                        throw new CalculationException("%s is invalid token.".formatted(string));
                    }
                } else {
                    context.stack().push(token);
                }
            } else {
                context.stack().push(token);
            }
        }
    }
}
