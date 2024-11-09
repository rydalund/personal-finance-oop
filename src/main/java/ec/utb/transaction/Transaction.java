package ec.utb.transaction;
import java.time.LocalDate;
import java.util.UUID;

public abstract class Transaction {

    private final UUID transactionId;
    private final double amount;
    private final String transactionName;
    private final LocalDate transactionDate;

    public Transaction(UUID transactionId, double amount, String transactionName, LocalDate transactionDate) {
        this.transactionId = (transactionId != null) ? transactionId : UUID.randomUUID();
        this.amount = amount;
        this.transactionName = transactionName;
        this.transactionDate = (transactionDate != null) ? transactionDate : LocalDate.now();
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public String getTransactionType() {
        if (this instanceof DepositTransaction) {
            return "DEPOSIT";
        } else if (this instanceof WithdrawTransaction) {
            return "WITHDRAW";
        }
        return "UNKNOWN";
    }

    @Override
    public String toString() {
        return amount + "," + transactionName + "," + transactionDate + "," + getTransactionType() + "," + transactionId.toString();
    }

    public static Transaction fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 5) {
            try {
                double amount = Double.parseDouble(parts[0].trim());
                String transactionName = parts[1].trim();
                LocalDate transactionDate = LocalDate.parse(parts[2].trim());
                String transactionType = parts[3].trim();
                UUID transactionId = UUID.fromString(parts[4].trim());

                if (transactionType.equals("DEPOSIT")) {
                    return new DepositTransaction(transactionId, amount, transactionName, transactionDate);
                } else if (transactionType.equals("WITHDRAW")) {
                    return new WithdrawTransaction(transactionId, amount, transactionName, transactionDate);
                }
            } catch (Exception e) {
                System.err.println("Error parsing transaction from string: " + e.getMessage());
            }
        }
        return null;
    }
}