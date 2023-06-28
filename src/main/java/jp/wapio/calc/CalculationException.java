package jp.wapio.calc;

/**
 * 計算例外
 */
public class CalculationException extends RuntimeException {

    private static final long serialVersionUID = -5199929321315017809L;

    public CalculationException() {
        super();
    }

    public CalculationException(String message) {
        super(message);
    }

    public CalculationException(Exception e) {
        super(e);
    }

    public CalculationException(String message, Exception e) {
        super(message, e);
    }
}
