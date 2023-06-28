package jp.wapio.calc;

public class NullValue {

    public static final NullValue INSTANCE = new NullValue();

    private NullValue() {
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof NullValue;
    }
}
