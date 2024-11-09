package ec.utb.transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank {

    private final List<Transaction> transactions;
    private double balance;
    private final TransactionManager transactionManager;

    public Bank(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.transactions = new ArrayList<>();
        loadTransactions();
    }

    private void loadTransactions() {
        List<Transaction> savedTransactions = transactionManager.readTransactions();
        for (Transaction transaction : savedTransactions) {
            System.out.println("Loaded transaction: " + transaction);

            if (transactions.stream().noneMatch(t -> t.getTransactionId().equals(transaction.getTransactionId()))) {
                transactions.add(transaction);
                updateBalance(transaction);
            } else {
                System.out.println("Duplicate transaction found with UUID: " + transaction.getTransactionId());
            }
        }
    }

    public void addTransaction(Transaction transaction) {
        System.out.println("Adding transaction: " + transaction);
        if (!transactions.contains(transaction)) {
            transactions.add(transaction);
            updateBalance(transaction);
            System.out.println("Updated balance after transaction: " + balance);
            transactionManager.saveTransaction(transaction);
        } else {
            System.out.println("Transaction with ID " + transaction.getTransactionId() + " already exists.");
        }
    }

    public void updateBalance(Transaction transaction) {
        if (transaction instanceof DepositTransaction) {
            balance += transaction.getAmount();
            System.out.println("Balance updated after deposit: " + balance);
        } else if (transaction instanceof WithdrawTransaction) {
            balance -= transaction.getAmount();
            System.out.println("Balance updated after withdraw: " + balance);
        }
    }

    public double getBalance() {
        return balance;
    }

    public Transaction getTransactionById(UUID transactionId) {
        return transactions.stream()
                .filter(transaction -> transaction.getTransactionId().equals(transactionId))
                .findFirst()
                .orElse(null);
    }

    public void removeTransaction(UUID transactionId) {
        Transaction transaction = getTransactionById(transactionId);
        if (transaction != null) {
            transactionManager.deleteTransaction(transactionId);
            balance -= transaction.getAmount();
            transactions.remove(transaction);
        }
    }
}