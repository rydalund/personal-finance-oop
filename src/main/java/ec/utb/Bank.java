package ec.utb;


import ec.utb.transaction.DepositTransaction;
import ec.utb.transaction.Transaction;
import ec.utb.transaction.TransactionType;
import ec.utb.transaction.WithdrawTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank {
    private List<Transaction> transactions = new ArrayList<>();
    private double balance;

    // Lägg till en transaktion till banken
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        updateBalance();
    }

    // Hämta transaktion från ID
    public Transaction getTransactionById(UUID transactionId) {
        for (Transaction t : transactions) {
            if (t.getTransactionId().equals(transactionId)) {
                return t;
            }
        }
        return null;
    }

    // Ta bort transaktion från banken
    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
        updateBalance();
    }

    // Uppdatera balansen efter en transaktionsändring
    private void updateBalance() {
        balance = 0;
        for (Transaction t : transactions) {
            if (t instanceof DepositTransaction) {
                balance += t.getAmount();
            } else if (t instanceof WithdrawTransaction) {
                balance -= t.getAmount();
            }
        }
    }

    public double getBalance() {
        return balance;
    }

    // Getter för alla transaktioner (om det behövs)
    public List<Transaction> getTransactions() {
        return transactions;
    }
}