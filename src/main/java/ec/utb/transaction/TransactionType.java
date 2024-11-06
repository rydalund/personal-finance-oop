package ec.utb.transaction;

public enum TransactionType {
    DEPOSIT('+'),
    WITHDRAW('-');

    private final char asChar;

    TransactionType(char asChar) {
        this.asChar = asChar;
    }

    public char asChar() {
        return asChar;
    }
}

