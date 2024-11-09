package ec.utb.command;
import ec.utb.transaction.*;
import java.util.Scanner;
import java.util.UUID;

public class WithdrawCommand extends Command {

    private TransactionManager transactionManager;

    public WithdrawCommand(Bank bank, TransactionManager transactionManager) {
        super("WITHDRAW", "Withdraw an amount from the account", bank);
        this.transactionManager = transactionManager;
    }

    @Override
    public void executeCommand(String[] splitString) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the amount to withdraw: ");
        double amount = scanner.nextDouble();

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        double balance = bank.getBalance();
        if (balance < amount) {
            System.out.println("Insufficient funds. Your balance is: " + balance);
            return;
        }

        System.out.println("Enter a name or description for the transaction: ");
        scanner.nextLine();
        String transactionName = scanner.nextLine().trim();

        WithdrawTransaction withdrawTransaction = new WithdrawTransaction(
                UUID.randomUUID(), amount, transactionName, java.time.LocalDate.now()
        );

        bank.addTransaction(withdrawTransaction);

        System.out.println("Withdrawal successful. Amount: " + amount + " Description: " + transactionName);
    }
}