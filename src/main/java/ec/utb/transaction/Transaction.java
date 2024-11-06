package ec.utb.transaction;
import ec.utb.Application;
import ec.utb.Bank;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Transaction {
    private UUID transactionId;
    private String transactionName;
    private double entry;

    public Transaction(double entry, String transactionName){
        this.transactionId = UUID.randomUUID();
        this.transactionName = transactionName;
        this.entry = entry;
    }
}