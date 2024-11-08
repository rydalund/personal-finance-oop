package ec.utb.transaction;

public enum TransactionType {
    DEPOSIT("Deposit"),
    WITHDRAW("Withdraw");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

