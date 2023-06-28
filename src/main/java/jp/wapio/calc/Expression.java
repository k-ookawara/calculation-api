package jp.wapio.calc;

import java.util.List;

/**
 * 計算式。
 */
public record Expression(String expressionId, List<Object> tokens) {
}
