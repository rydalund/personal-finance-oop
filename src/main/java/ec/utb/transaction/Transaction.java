package ec.utb.transaction;
import ec.utb.Application;
import java.time.LocalDate;
import java.util.UUID;

public abstract class Transaction {

    private UUID transactionId;
    private double entry;
    private String transactionName;
    private double balance;
    private LocalDate transactionDate;

    public Transaction (double entry, String transactionName, Application application) {
        this.transactionId = UUID.randomUUID();
        this.entry = entry;
        this.transactionName = transactionName;
        this.transactionDate = LocalDate.now();
    }
    protected abstract void updateBankBalance (String[] commandArgs);
}
