package ec.utb.command;
import ec.utb.transaction.*;
import java.util.Scanner;

public class DepositCommand extends Command {

    private TransactionManager transactionManager;

    public DepositCommand(Bank bank, TransactionManager transactionManager) {
        super("DEPOSIT", "Deposit money into the account", bank);
        this.transactionManager = transactionManager;
    }

    @Override
    public void executeCommand(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter deposit amount:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter transaction name:");
        String transactionName = scanner.nextLine();

        DepositTransaction deposit = new DepositTransaction(amount, transactionName);

        bank.addTransaction(deposit);
    }
}