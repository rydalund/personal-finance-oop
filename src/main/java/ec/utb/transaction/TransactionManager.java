package ec.utb.transaction;
import java.util.List;
import java.util.UUID;

public interface TransactionManager {
    List<Transaction> readTransactions();
    void saveTransaction(Transaction transaction);
    void deleteTransaction(UUID transactionId);
}