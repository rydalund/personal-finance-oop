package ec.utb.transaction;
import java.util.UUID;

public interface TransactionSaver {
    void saveTransaction(Transaction transaction);
    void deleteTransaction(UUID transactionId);
}