package ec.utb.transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionFileSaver implements TransactionSaver {
    private static final String FILENAME = "transactions.dat";

    @Override
    public void saveTransaction(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
            writer.write(transaction.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    @Override
    public void deleteTransaction(UUID transactionId) {
        List<Transaction> transactions = readTransactions();
        transactions.removeIf(t -> t.getTransactionId().equals(transactionId));
        writeTransactions(transactions);
    }

    private List<Transaction> readTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Transaction transaction = Transaction.fromString(line);
                if (transaction != null) {
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions: " + e.getMessage());
        }
        return transactions;
    }

    private void writeTransactions(List<Transaction> transactions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Transaction transaction : transactions) {
                writer.write(transaction.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing transactions: " + e.getMessage());
        }
    }
}