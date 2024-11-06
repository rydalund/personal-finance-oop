package ec.utb.utility;
import java.util.UUID;
import ec.utb.transaction.Transaction;

public interface TransactionSaver {

    void saveTransaction(Transaction transaction);

    void deleteTransaction(UUID transactionId);
}