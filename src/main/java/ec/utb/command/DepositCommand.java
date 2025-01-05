package ec.utb.command;

import ec.utb.Bank;
import ec.utb.transaction.DepositTransaction;
import ec.utb.transaction.Transaction;
import ec.utb.user.User;
import java.util.Scanner;

public class DepositCommand extends Command {

    private final Bank bank;

    public DepositCommand(Bank bank) {
        super("DEPOSIT", "Deposit into the account", bank);
        this.bank = bank;
    }

    @Override
    public void executeCommand() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter deposit amount: ");
        String amountInput = scanner.nextLine().trim();
        try {
            double amount = Double.parseDouble(amountInput);
            if (amount <= 0) {
                System.out.println("Amount must be positive.");
                return;
            }
            System.out.print("Enter (optional) deposit transaction name: ");
            String transactionName = scanner.nextLine().trim();
            User loggedInUser = bank.getLoggedInUser();
            if (loggedInUser == null) {
                System.out.println("No user is logged in.");
                return;
            }
            Transaction deposit = new DepositTransaction(amount, transactionName, loggedInUser.getUserId());
            bank.addTransaction(deposit);
            System.out.println("Deposit transaction has been recorded.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format. Please enter a valid number.");
        }
    }
}