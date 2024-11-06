package ec.utb.command;

import ec.utb.Bank;
import ec.utb.transaction.DepositTransaction;
import ec.utb.transaction.Transaction;
import ec.utb.transaction.TransactionType;

import java.util.Scanner;

public class DepositCommand extends Command {

    private String name;
    private double amount;
    protected Bank bank;
    protected TransactionType transactionType;

    public DepositCommand(String name, String description, Bank bank,  TransactionType transactionType, double amount) {
        super(name, description, bank);
        this.transactionType = TransactionType.DEPOSIT; //kanske vänta tills transaction skapas
        this.amount = amount;
    }

    @Override
    public void executeCommand(String[] splitString) {
        double valueFromInput = 0;
        try {
            valueFromInput = Command.testInputLength(splitString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.printf("So you want to %s: %.2f?%n", transactionType.toString().toLowerCase(), valueFromInput);
        System.out.println("Type 'yes' to save or 'no' to cancel this entry");
        String answer = scanner.nextLine().trim().toUpperCase();

        if (answer.equalsIgnoreCase("yes")) {
            System.out.println("Submit label for this transaction (or just press enter for default label): ");
            String label = scanner.nextLine().trim();
            if (label.isEmpty()) {
                label = transactionType.toString();
            }

            if (transactionType == TransactionType.WITHDRAW && valueFromInput > bank.getBalance()) {
                System.out.println("Insufficient funds. Transaction cancelled.");
                return;
            }

            Transaction trans = new DepositTransaction(valueFromInput, transactionType, label, bank);
            /*ArrayList<Transaction> transactionList = new ArrayList<>();
            transactionList.add(trans);
            TransactionFileWriter.writeTransactions(transactionList, BankFileHandler.filename);

            System.out.printf("%s of %.2f completed successfully.%n", transactionType, Math.abs(valueFromInput));
        } else {
            System.out.println("Transaction cancelled.");
        }*/
    }
    }
}
