package ec.utb.transaction;
import ec.utb.Bank;
import java.time.LocalDate;

public class WithdrawTransaction extends Transaction {

    public WithdrawTransaction(double entry, String transactionName, LocalDate transactionDate, TransactionType type, Bank bank) {
        super(entry, transactionName);
        transactionDate = LocalDate.now();
        TransactionType type1 = TransactionType.WITHDRAW;
        double balance = bank.getBalance() - entry;
    }
}
