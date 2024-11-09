package ec.utb.transaction;
import java.time.LocalDate;
import java.util.UUID;

public class WithdrawTransaction extends Transaction {

    public WithdrawTransaction(UUID transactionId, double amount, String transactionName, LocalDate transactionDate) {
        super(transactionId, amount, transactionName, transactionDate);
    }

    public WithdrawTransaction(double amount, String transactionName) {
        super(null, amount, transactionName, null);
    }

}