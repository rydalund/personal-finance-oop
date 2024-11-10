package ec.utb.transaction;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionFileSaver implements TransactionManager {
    private final String filePath;

    public TransactionFileSaver(String filePath) {
        this.filePath = filePath;
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Transaction file created: " + filePath);
                } else {
                    System.out.println("Transaction file already exists.");
                }
            } catch (IOException e) {
                System.err.println("Error creating transaction file: " + e.getMessage());
            }
        }
    }

    @Override
    public List<Transaction> readTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Transaction transaction = Transaction.fromString(line);
                transactions.add(transaction);
            }
        } catch (IOException e) {
            System.err.println("Error reading transactions from file " + filePath + ": " + e.getMessage());
        }
        System.out.println("Transactions read from file: " + transactions.size());
        return transactions;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        List<Transaction> existingTransactions = readTransactions();

        if (existingTransactions.stream().noneMatch(t -> t.getTransactionId().equals(transaction.getTransactionId()))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(transaction.toString());
                writer.newLine();
            } catch (IOException e) {
                System.err.println("Error saving transaction: " + e.getMessage());
            }
        } else {
            System.out.println("Transaction with UUID " + transaction.getTransactionId() + " already exists in the file.");
        }
    }

    @Override
    public void deleteTransaction(UUID transactionId) {
        List<Transaction> transactions = readTransactions();
        List<Transaction> updatedTransactions = transactions.stream()
                .filter(transaction -> !transaction.getTransactionId().equals(transactionId))
                .toList();

        if (updatedTransactions.size() == transactions.size()) {
            System.out.println("Transaction not found with ID: " + transactionId);
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (Transaction transaction : updatedTransactions) {
                    writer.write(transaction.toString());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error saving updated transactions: " + e.getMessage());
            }
        }
    }
}