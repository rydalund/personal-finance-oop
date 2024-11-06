package ec.utb.command;
import ec.utb.Bank;
import ec.utb.transaction.TransactionType;
import ec.utb.utility.TransactionSaver;

import java.util.Scanner;

public abstract class Command {
    protected Scanner scanner = new Scanner(System.in);
    private String name;
    protected Bank bank;

    public Command(String name, Bank bank) {
        this.name = name;
        this.bank = bank;
    }

    public abstract void executeCommand(String[] splitString);

    public String getName() {
        return name;
    }

    protected static double testInputLength(String[] splitString) {
        if (splitString.length == 2) {
            String inputDoubleString = splitString[1];
            try {
                return Double.parseDouble(inputDoubleString);
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format: " + inputDoubleString);
                return 0.00;
            }
        } else {
            throw new IllegalArgumentException("Invalid input length");
        }
    }

    protected String getTransactionNameOrDefault(String[] splitString, TransactionType transactionType) {
        if (splitString.length > 1 && !splitString[1].isEmpty()) {
            return splitString[1];
        } else {
            return transactionType.name();
        }
    }
}