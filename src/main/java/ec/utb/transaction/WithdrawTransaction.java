package ec.utb.transaction;
import java.time.LocalDate;
import java.util.UUID;

public class WithdrawTransaction extends Transaction {

    public WithdrawTransaction(UUID transactionId, double amount, String transactionName, LocalDate transactionDate, UUID userId) {
        super(transactionId, amount, transactionName, transactionDate, TransactionType.WITHDRAW, userId);
    }

    public WithdrawTransaction(double amount, String transactionName, UUID userId) {
        super(null, amount, getDefaultTransactionName(transactionName), null, TransactionType.WITHDRAW, userId);
    }

    private static String getDefaultTransactionName(String transactionName) {
        return (transactionName == null || transactionName.isEmpty()) ? TransactionType.WITHDRAW.getDescription() : transactionName;
    }
}