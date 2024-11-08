package ec.utb.transaction;
import ec.utb.Application;
import ec.utb.Bank;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Transaction {
    private UUID transactionId;
    private String transactionName;
    private double amount;
    private LocalDate transactionDate;

    public Transaction(double amount, String transactionName) {
        this.transactionId = UUID.randomUUID();
        this.transactionName = transactionName;
        this.amount = amount;
        this.transactionDate = LocalDate.now();
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }


    public static Transaction fromString(String line) {
        return null;
    }

    @Override
    public String toString() {
        return "Transaction ID: " + transactionId + ", Name: " + transactionName + ", Amount: " + amount + ", Date: " + transactionDate;
    }
}