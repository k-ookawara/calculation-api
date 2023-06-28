package jp.wapio.calc.config;

public enum ConfigType {

    /** 小数点の丸め精度 */
    ROUNDING_PRECISION("rounding.precision");

    private String key;

    private ConfigType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
