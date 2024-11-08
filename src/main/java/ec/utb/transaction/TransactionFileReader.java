package ec.utb.transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionFileReader {

    private static final String FILENAME = "transactions.dat";

    public static List<Transaction> readTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Transaction transaction = fromString(line);
                if (transaction != null) {
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions from file: " + e.getMessage());
        }
        return transactions;
    }

    public static Transaction fromString(String line) {
        if (line.contains("Deposit")) {
            return DepositTransaction.fromString(line);
        } else if (line.contains("Withdraw")) {
            return WithdrawTransaction.fromString(line);
        }
        return null;
    }
}