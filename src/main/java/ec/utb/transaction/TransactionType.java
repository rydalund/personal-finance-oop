package ec.utb.transaction;

public enum TransactionType {
    DEPOSIT("Deposit"),
    WITHDRAW("Withdraw"),
    BOTH("Both");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /*
    public static TransactionType fromString(String type) {
        if (type != null) {
            for (TransactionType transactionType : TransactionType.values()) {
                if (type.equalsIgnoreCase(transactionType.name()) || type.equalsIgnoreCase(transactionType.getDescription())) {
                    return transactionType;
                }
            }
        }
        return null;
    }*/
}