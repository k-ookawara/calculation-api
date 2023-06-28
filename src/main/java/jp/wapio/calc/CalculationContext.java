package jp.wapio.calc;

import java.util.Collections;
import java.util.Map;

import jp.wapio.calc.config.Config;

/**
 * 計算コンテキスト
 */
public class CalculationContext {

    private Config config;

    private Stack stack = new Stack();

    private Map<String, Object> variables;

    public CalculationContext(Config config) {
        this(config, Map.of());
    }
    
    public CalculationContext(Config config, Map<String, Object> variables) {
        this.config = config;
        this.variables = variables;
    }

    public Config config() {
        return config;
    }

    public Stack stack() {
        return stack;
    }
    
    /**
     * 不変な変数マップを返します。
     * 
     * @return 不変な変数マップ
     */
    public Map<String, Object> variables() {
        return Collections.unmodifiableMap(variables);
    }

    public void putVariable(String key, Object o) {
        variables.put(key, o);
    }
}
