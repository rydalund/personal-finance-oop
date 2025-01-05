package ec.utb.transaction;
import java.time.LocalDate;
import java.util.UUID;


public class DepositTransaction extends Transaction {

    public DepositTransaction(UUID transactionId, double amount, String transactionName, LocalDate transactionDate, UUID userId) {
        super(transactionId, amount, transactionName, transactionDate, TransactionType.DEPOSIT, userId);
    }

    public DepositTransaction(double amount, String transactionName, UUID userId) {
        super(null, amount, getDefaultTransactionName(transactionName), null, TransactionType.DEPOSIT, userId);
    }

    private static String getDefaultTransactionName(String transactionName) {
        return (transactionName == null || transactionName.isEmpty()) ? TransactionType.DEPOSIT.getDescription() : transactionName;
    }
}