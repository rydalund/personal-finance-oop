package ec.utb.command;
import ec.utb.Bank;
import ec.utb.transaction.TransactionType;
import ec.utb.transaction.TransactionSaver;

public abstract class Command {
    private String name; //ska kanske göra om
    protected Bank bank;
    protected TransactionSaver transactionSaver;

    public Command(String name, Bank bank, TransactionSaver transactionSaver) {
        this.name = name;
        this.bank = bank;
        this.transactionSaver = transactionSaver;
    }

    public String getName() {
        return name;
    }

    public abstract void executeCommand(String[] splitString);


    protected double getAmountFromInput(String[] splitString) {
        if (splitString.length < 2) {
            throw new IllegalArgumentException("Amount is required.");
        }

        try {
            return Double.parseDouble(splitString[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount.");
        }
    }

    protected String getTransactionNameOrDefault(String[] splitString, TransactionType transactionType) {
        if (splitString.length > 1 && !splitString[1].isEmpty()) {
            return splitString[1]; // Använd namn från input om det finns
        } else {
            return transactionType.getDescription(); // Annars använd default namn från TransactionType
        }
    }
}