package ec.utb.command;

import ec.utb.Bank;
import ec.utb.transaction.Transaction;
import ec.utb.transaction.TransactionSaver;

import java.util.UUID;

public class DeleteCommand extends Command {


    public DeleteCommand( Bank bank, TransactionSaver transactionSaver) {
        super("DELETE", bank, transactionSaver);
    }

    @Override
    public void executeCommand(String[] splitString) {

        if (splitString.length < 2) {
            System.out.println("Transaction ID is required to delete a transaction.");
            return;
        }

        try {
            UUID transactionId = UUID.fromString(splitString[1]);
            Transaction transactionToDelete = bank.getTransactionById(transactionId);

            if (transactionToDelete != null) {
                bank.removeTransaction(transactionToDelete);
                transactionSaver.deleteTransaction(transactionId);
                System.out.println("Transaction with ID " + transactionId + " has been deleted.");
            } else {
                System.out.println("Transaction not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid transaction ID format.");
        }
    }
}