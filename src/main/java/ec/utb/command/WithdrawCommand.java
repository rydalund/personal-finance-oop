package ec.utb.command;
import ec.utb.Bank;
import ec.utb.transaction.*;
import ec.utb.user.User;
import java.util.Scanner;
public class WithdrawCommand extends Command {

    private final Bank bank;

    public WithdrawCommand(Bank bank) {
        super("WITHDRAW", "Withdraw from the account", bank);
        this.bank = bank;
    }

    @Override
    public void executeCommand() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter withdraw amount: ");
        String amountInput = scanner.nextLine().trim();

        try {
            double amount = Double.parseDouble(amountInput);
            if (amount <= 0) {
                System.out.println("Amount must be positive.");
                return;
            }
            User loggedInUser = bank.getLoggedInUser();
            if (loggedInUser == null) {
                System.out.println("No user is logged in.");
                return;
            }
            double userBalance = bank.getUserBalance(loggedInUser.getUserId());
            if (userBalance < amount) {
                System.out.println("Insufficient funds. Your current balance is: " + userBalance);
                return;
            }
            System.out.print("Enter (optional) withdraw transaction name: ");
            String transactionName = scanner.nextLine().trim();
            Transaction withdraw = new WithdrawTransaction(amount, transactionName, loggedInUser.getUserId());
            bank.addTransaction(withdraw);
            System.out.println("Withdraw transaction has been recorded.");
            System.out.println("New balance: " + bank.getUserBalance(loggedInUser.getUserId()));
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format. Please enter a valid number.");
        }
    }
}