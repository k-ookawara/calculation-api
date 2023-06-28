package jp.wapio.calc.parser;

/**
 * パース処理例外
 */
public class ParseException extends RuntimeException {

    private static final long serialVersionUID = -2627686411977554998L;

    public ParseException() {
        super();
    }

    public ParseException(String message) {
        super(message);
    }
}
