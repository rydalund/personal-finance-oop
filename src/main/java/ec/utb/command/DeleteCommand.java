package ec.utb.command;
import ec.utb.Bank;
import ec.utb.transaction.Transaction;

import java.util.Scanner;
import java.util.UUID;

public class DeleteCommand extends Command {

    public DeleteCommand(Bank bank) {
        super("DELETE", "Delete a transaction by its ID", bank);
    }

    @Override
    public void executeCommand() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the transaction ID to delete (UUID format): ");
        String transactionIdInput = scanner.nextLine().trim();
        UUID transactionId;
        try {
            transactionId = UUID.fromString(transactionIdInput);
        } catch (IllegalArgumentException e) {
            System.out.print("Invalid transaction ID format.");
            return;
        }
        Transaction transactionToDelete = getBank().getTransactionById(transactionId);
        if (transactionToDelete == null) {
            System.out.println("Transaction not found with ID: " + transactionId);
            return;
        }
        getBank().removeTransaction(transactionId);
        System.out.println("Transaction with ID " + transactionId + " has been deleted.");
    }
}