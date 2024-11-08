package ec.utb.transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionManager {
    void addTransaction(Transaction transaction);
    void deleteTransaction(UUID transactionId);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByType(TransactionType type);
    List<Transaction> getTransactionsByDateRange(String startDate, String endDate);
}