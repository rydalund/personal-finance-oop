package ec.utb.menu;

import ec.utb.Bank;
import ec.utb.command.Command;
import ec.utb.command.DeleteCommand;
import ec.utb.command.DepositCommand;
import ec.utb.command.WithdrawCommand;
import ec.utb.transaction.TransactionSaver;

import java.util.Scanner;

public class BankMenu extends Menu {

    private final TransactionSaver transactionSaver;

    public BankMenu(Bank bank, TransactionSaver transactionSaver) {
        super(bank);
        this.transactionSaver = transactionSaver;
    }

    @Override
    public void show() {
        Scanner scanner = new Scanner(System.in);

        registerCommand(new DepositCommand(bank, transactionSaver));
        registerCommand(new WithdrawCommand(bank, transactionSaver));
        registerCommand(new DeleteCommand(bank, transactionSaver));

        boolean continueRunning = true;
        while (continueRunning) {
            System.out.println("\nEnter a command (type 'HELP' for list of commands): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("END")) {
                System.out.println("Exiting...");
                continueRunning = false;
            } else {
                try {
                    tryExecuteCommand(input);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input, try again.");
                }
            }
        }
        scanner.close();
    }
}