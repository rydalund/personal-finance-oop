package ec.utb.transaction;

import ec.utb.Bank;

public class WithdrawTransaction extends Transaction {
    public WithdrawTransaction(double amount, String transactionName) {
        super(amount, transactionName);
    }

    @Override
    public String toString() {
        return getAmount() + "," + getTransactionName();
    }

    public static WithdrawTransaction fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 2) {
            try {
                double amount = Double.parseDouble(parts[0].trim());
                String transactionName = parts[1].trim();
                return new WithdrawTransaction(amount, transactionName);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing WithdrawTransaction: " + e.getMessage());
            }
        }
        return null;
    }
}