package ec.utb;

public class NoTransactionsFoundException extends RuntimeException {
    public NoTransactionsFoundException(String message) {
        super(message);
    }
}
