package ec.utb.transaction;
import java.time.LocalDate;
import java.util.UUID;


public class DepositTransaction extends Transaction {

    public DepositTransaction(UUID transactionId, double amount, String transactionName, LocalDate transactionDate) {
        super(transactionId, amount, transactionName, transactionDate, TransactionType.DEPOSIT);
    }

    public DepositTransaction(double amount, String transactionName) {
        super(null, amount, getDefaultTransactionName(transactionName), null, TransactionType.DEPOSIT);
    }

    private static String getDefaultTransactionName(String transactionName) {
        return (transactionName == null || transactionName.isEmpty()) ? TransactionType.DEPOSIT.getDescription() : transactionName;
    }
}