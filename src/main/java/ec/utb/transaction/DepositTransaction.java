package ec.utb.transaction;

import ec.utb.Bank;

public class DepositTransaction extends Transaction {
    public DepositTransaction(double amount, String transactionName) {
        super(amount, transactionName);
    }

    @Override
    public String toString() {
        return getAmount() + "," + getTransactionName(); // Format för att spara transaktionen
    }

    public static DepositTransaction fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 2) {
            try {
                double amount = Double.parseDouble(parts[0].trim());
                String transactionName = parts[1].trim();
                return new DepositTransaction(amount, transactionName);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing DepositTransaction: " + e.getMessage());
            }
        }
        return null;
    }
}