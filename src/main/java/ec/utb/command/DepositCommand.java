package ec.utb.command;
import ec.utb.Bank;
import ec.utb.transaction.*;
import java.util.Scanner;

public class DepositCommand extends Command {

    private final Bank bank;

    public DepositCommand(Bank bank, TransactionManager transactionManager) {
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
            System.out.print("Enter transaction name: ");
            String transactionName = scanner.nextLine().trim();
            Transaction deposit = new DepositTransaction(amount, transactionName);
            bank.addTransaction(deposit);
            System.out.println("Deposited " + amount + " into the account with transaction name: " + transactionName);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format. Please enter a valid number.");
        }
    }
}