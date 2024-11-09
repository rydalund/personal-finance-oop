package ec.utb.command;
import ec.utb.transaction.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ShowCommand extends Command {

    private TransactionManager transactionManager;

    public ShowCommand(Bank bank, TransactionManager transactionManager) {
        super("SHOW", "Displays transactions based on filters", bank);
        this.transactionManager = transactionManager;
    }

    @Override
    public void executeCommand(String[] splitString) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Filter transactions by (D)eposit, (W)ithdraw, or (B)oth? (default is both)");
        String typeInput = scanner.nextLine().trim().toUpperCase();
        TransactionType type = TransactionType.BOTH;
        if (typeInput.equals("D")) {
            type = TransactionType.DEPOSIT;
        } else if (typeInput.equals("W")) {
            type = TransactionType.WITHDRAW;
        }
        System.out.println("Enter year (or leave empty for all): ");
        String yearInput = scanner.nextLine().trim();
        Integer year = yearInput.isEmpty() ? null : Integer.parseInt(yearInput);
        System.out.println("Enter month (1-12, or leave empty for all): ");
        String monthInput = scanner.nextLine().trim();
        Integer month = monthInput.isEmpty() ? null : Integer.parseInt(monthInput);
        System.out.println("Enter day (1-31, or leave empty for all): ");
        String dayInput = scanner.nextLine().trim();
        Integer day = dayInput.isEmpty() ? null : Integer.parseInt(dayInput);
        LocalDate date = (year != null && month != null && day != null) ?
                LocalDate.of(year, month, day) : null;
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