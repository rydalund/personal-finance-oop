package ec.utb.command;
import ec.utb.Bank;
import ec.utb.transaction.*;
import java.util.List;
import java.util.Scanner;

public class ShowCommand extends Command {

    private final TransactionManager transactionManager;

    public ShowCommand(Bank bank, TransactionManager transactionManager) {
        super("SHOW", "Displays transactions based on filter", bank);
        this.transactionManager = transactionManager;
    }

    @Override
    public void executeCommand() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Filter transactions by (D)eposit, (W)ithdraw, or (B)oth? (default is both): ");
        String typeInput = scanner.nextLine().trim().toUpperCase();
        TransactionType type = TransactionType.BOTH;
        if (typeInput.equals("D")) {
            type = TransactionType.DEPOSIT;
        } else if (typeInput.equals("W")) {
            type = TransactionType.WITHDRAW;
        }
        System.out.print("Enter year (or leave empty for all): ");
        String yearInput = scanner.nextLine().trim();
        Integer year = yearInput.isEmpty() ? null : Integer.parseInt(yearInput);
        System.out.print("Enter month (1-12, or leave empty for all): ");
        String monthInput = scanner.nextLine().trim();
        Integer month = monthInput.isEmpty() ? null : Integer.parseInt(monthInput);
        System.out.print("Enter day (1-31, or leave empty for all): ");
        String dayInput = scanner.nextLine().trim();
        Integer day = dayInput.isEmpty() ? null : Integer.parseInt(dayInput);
        List<Transaction> filteredTransactions = getFilteredTransactions(year, month, day, type);
        printTransactions(filteredTransactions);
    }

    private List<Transaction> getFilteredTransactions(Integer year, Integer month, Integer day, TransactionType type) {
        List<Transaction> transactions = transactionManager.readTransactions();
        return TransactionFilter.getTransactions(transactions, year, month, day, type);
    }

    private void printTransactions(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
}