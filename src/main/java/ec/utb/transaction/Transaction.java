package ec.utb.transaction;
import java.time.LocalDate;
import java.util.UUID;

public abstract class Transaction {

    private final UUID transactionId;
    private final double amount;
    private final String transactionName;
    private final LocalDate transactionDate;
    private final TransactionType transactionType;
    private final UUID userId;

    public Transaction(UUID transactionId, double amount, String transactionName, LocalDate transactionDate, TransactionType transactionType, UUID userId) {
        this.transactionId = (transactionId != null) ? transactionId : UUID.randomUUID();
        this.amount = amount;
        this.transactionName = (transactionName != null && !transactionName.isEmpty()) ? transactionName : transactionType.getDescription();
        this.transactionDate = (transactionDate != null) ? transactionDate : LocalDate.now();
        this.transactionType = transactionType;
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public UUID getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return String.join(",",
                String.valueOf(amount),
                transactionName,
                transactionDate.toString(),
                transactionType.name(),
                transactionId.toString(),
                userId.toString()
        );
    }

    public static Transaction fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 6) {
            try {
                double amount = Double.parseDouble(parts[0].trim());
                String transactionName = parts[1].trim();
                LocalDate transactionDate = LocalDate.parse(parts[2].trim());
                String transactionTypeStr = parts[3].trim();
                UUID transactionId = UUID.fromString(parts[4].trim());
                UUID userId = UUID.fromString(parts[5].trim());
                TransactionType transactionType = TransactionType.valueOf(transactionTypeStr.toUpperCase());
                if (transactionType == TransactionType.DEPOSIT) {
                    return new DepositTransaction(transactionId, amount, transactionName, transactionDate, userId);
                } else if (transactionType == TransactionType.WITHDRAW) {
                    return new WithdrawTransaction(transactionId, amount, transactionName, transactionDate, userId);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Error parsing transaction from string: " + e.getMessage(), e);
            }
        }
        throw new IllegalArgumentException("Invalid string format for transaction");
    }

    public String getTransactionName() {
        return transactionName;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}