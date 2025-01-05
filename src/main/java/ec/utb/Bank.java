package ec.utb;
import ec.utb.transaction.DepositTransaction;
import ec.utb.transaction.Transaction;
import ec.utb.transaction.TransactionManager;
import ec.utb.transaction.WithdrawTransaction;
import ec.utb.user.AuthenticationManager;
import ec.utb.user.User;
import java.util.*;

public class Bank {
    private double balance; //Används inte just nu
    private final TransactionManager transactionManager;
    private final AuthenticationManager authenticationManager;
    private final Map<UUID, Double> userBalances;

    public Bank(TransactionManager transactionManager, AuthenticationManager authenticationManager) {
        this.transactionManager = transactionManager;
        this.authenticationManager = authenticationManager;
        this.userBalances = new HashMap<>();
        this.balance = 0.0;
        loadTransactions();
    }

    public double getUserBalance(UUID userId) {
        return userBalances.getOrDefault(userId, 0.0);
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public User getLoggedInUser() {
        return authenticationManager.getLoggedInUser();
    }

    private void loadTransactions() {
        List<Transaction> savedTransactions = transactionManager.readTransactions();
        balance = 0.0;
        userBalances.clear();

        for (Transaction transaction : savedTransactions) {
            updateBalance(transaction);
            UUID userId = transaction.getUserId();
            userBalances.put(userId, userBalances.getOrDefault(userId, 0.0) + transaction.getAmount());
        }
    }

    private void updateBalance(Transaction transaction) {
        if (transaction instanceof DepositTransaction) {
            balance += transaction.getAmount();
        } else if (transaction instanceof WithdrawTransaction) {
            balance -= transaction.getAmount();
            UUID userId = transaction.getUserId();
            userBalances.put(userId, userBalances.getOrDefault(userId, 0.0) - transaction.getAmount());
        }
    }

    public void addTransaction(Transaction transaction) {
        System.out.println("Adding transaction: " + transaction);

        if (transaction instanceof WithdrawTransaction withdrawTransaction) {
            UUID userId = withdrawTransaction.getUserId();

            if (!hasSufficientBalance(userId, withdrawTransaction.getAmount())) {
                throw new IllegalArgumentException("Insufficient balance for withdrawal.");
            }

            balance -= withdrawTransaction.getAmount();
            userBalances.put(userId, userBalances.getOrDefault(userId, 0.0) - withdrawTransaction.getAmount());
        } else if (transaction instanceof DepositTransaction) {
            balance += transaction.getAmount();
            UUID userId = transaction.getUserId();
            userBalances.put(userId, userBalances.getOrDefault(userId, 0.0) + transaction.getAmount());
        }

        transactionManager.saveTransaction(transaction);
    }

    private boolean hasSufficientBalance(UUID userId, double amount) {
        return userBalances.getOrDefault(userId, 0.0) >= amount;
    }

    public Transaction getTransactionById(UUID transactionId) {
        return transactionManager.readTransactionById(transactionId);
    }

    public void removeTransaction(UUID transactionId) {
        Transaction transaction = getTransactionById(transactionId);
        if (transaction != null) {
            try {
                User loggedInUser = this.getLoggedInUser();
                if (loggedInUser == null) {
                    System.out.println("No user is logged in.");
                    return;
                }
                List<Transaction> userTransactions = this.transactionManager.readTransactionsForUser(loggedInUser.getUserId());
                userTransactions.removeIf(t -> t.getTransactionId().equals(transactionId));
                double newBalance = 0.0;
                for (Transaction t : userTransactions) {
                    if (t.getAmount() < 0) {
                        newBalance -= Math.abs(t.getAmount());
                    } else {
                        newBalance += t.getAmount();
                    }
                }
                this.updateUserBalance(loggedInUser.getUserId(), newBalance);
                transactionManager.deleteTransaction(transactionId);

                System.out.println("Transaction removed and balance updated.");
            } catch (Exception e) {
                System.err.println("Error removing transaction: " + e.getMessage());
            }
        } else {
            System.out.println("Transaction not found.");
        }
    }

    public List<User> getAllUsers() {
        return transactionManager.readAllUsers();
    }

    public void updateUserBalance(UUID userId, double newBalance) {
        userBalances.put(userId, newBalance);
    }
}