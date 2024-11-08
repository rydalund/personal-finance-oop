package ec.utb.command;

import ec.utb.Bank;
import ec.utb.transaction.Transaction;
import ec.utb.transaction.TransactionType;
import ec.utb.transaction.WithdrawTransaction;
import ec.utb.transaction.TransactionSaver;

public class WithdrawCommand extends Command {

    public WithdrawCommand(Bank bank, TransactionSaver transactionSaver) {
        super("WITHDRAW", bank, transactionSaver);
    }

    @Override
    public void executeCommand(String[] splitString) {
        double amount = getAmountFromInput(splitString);
        if (amount > 0 && amount <= bank.getBalance()) {
            String transactionName = getTransactionNameOrDefault(splitString, TransactionType.WITHDRAW);
            Transaction transaction = new WithdrawTransaction(amount, transactionName);
            bank.addTransaction(transaction);
            transactionSaver.saveTransaction(transaction);
            System.out.println("Withdrawal of " + amount + " processed.");
        } else {
            System.out.println("Invalid amount or insufficient funds.");
        }
    }
}