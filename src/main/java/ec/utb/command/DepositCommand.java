package ec.utb.command;

import ec.utb.Bank;
import ec.utb.transaction.DepositTransaction;
import ec.utb.transaction.Transaction;
import ec.utb.transaction.TransactionType;
import ec.utb.transaction.TransactionSaver;

public class DepositCommand extends Command {

    public DepositCommand(Bank bank, TransactionSaver transactionSaver) {
        super("DEPOSIT", bank, transactionSaver);
    }

    @Override
    public void executeCommand(String[] splitString) {
        double amount = getAmountFromInput(splitString);
        if (amount > 0) {
            String transactionName = getTransactionNameOrDefault(splitString, TransactionType.DEPOSIT);
            Transaction transaction = new DepositTransaction(amount, transactionName);
            bank.addTransaction(transaction);
            transactionSaver.saveTransaction(transaction);
            System.out.println("Deposit of " + amount + " processed.");
        } else {
            System.out.println("Invalid amount for deposit.");
        }
    }
}