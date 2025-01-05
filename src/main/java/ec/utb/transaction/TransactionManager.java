package ec.utb.transaction;
import ec.utb.user.User;

import java.util.List;
import java.util.UUID;

public interface TransactionManager {
    List<Transaction> readTransactions();
    void saveTransaction(Transaction transaction);
    void deleteTransaction(UUID transactionId);
    List<Transaction> readTransactionsForUser(UUID userId);
    Transaction readTransactionById(UUID transactionId);
    List<User> readAllUsers();
    void deleteUserTransactions(UUID userId);
}