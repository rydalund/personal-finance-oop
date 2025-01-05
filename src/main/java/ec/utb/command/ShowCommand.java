package ec.utb.command;
import ec.utb.Bank;
import ec.utb.transaction.*;
import ec.utb.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ShowCommand extends Command {

    private final Bank bank;

    public ShowCommand(Bank bank) {
        super("SHOW", "Displays transactions based on filter", bank);
        this.bank = bank;
    }

    @Override
    public void executeCommand() {
        Scanner scanner = new Scanner(System.in);
        User loggedInUser = bank.getLoggedInUser();
        if (loggedInUser == null) {
            System.out.println("No user is logged in.");
            return;
        }
        System.out.print("Filter transactions by (D)eposit, (W)ithdraw, or (B)oth? (default is both): ");
        String typeInput = scanner.nextLine().trim().toUpperCase();
        TransactionType type = TransactionType.BOTH;
        if (typeInput.equals("D")) {
            type = TransactionType.DEPOSIT;
        } else if (typeInput.equals("W")) {
            type = TransactionType.WITHDRAW;
        }
        Integer year = askForYear(scanner);
        Integer month = askForMonth(scanner);
        Integer day = askForDay(scanner);
        List<Transaction> filteredTransactions = getFilteredTransactions(loggedInUser.getUserId(), year, month, day, type);
        if (filteredTransactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            printTransactions(filteredTransactions);
        }
    }

    private Integer askForYear(Scanner scanner) {
        System.out.print("Enter year (or leave empty for all): ");
        String yearInput = scanner.nextLine().trim();
        if (yearInput.isEmpty()) {
            return null;
        } else {
            try {
                return Integer.parseInt(yearInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid year format. Please enter a valid number.");
                return askForYear(scanner);
            }
        }
    }

    private Integer askForMonth(Scanner scanner) {
        System.out.print("Enter month (1-12, or leave empty for all): ");
        String monthInput = scanner.nextLine().trim();
        if (monthInput.isEmpty()) {
            return null;
        } else {
            try {
                int month = Integer.parseInt(monthInput);
                if (month < 1 || month > 12) {
                    System.out.println("Invalid month. Please enter a number between 1 and 12.");
                    return askForMonth(scanner);
                }
                return month;
            } catch (NumberFormatException e) {
                System.out.println("Invalid month format. Please enter a valid number.");
                return askForMonth(scanner);
            }
        }
    }

    private Integer askForDay(Scanner scanner) {
        System.out.print("Enter day (1-31, or leave empty for all): ");
        String dayInput = scanner.nextLine().trim();
        if (dayInput.isEmpty()) {
            return null;
        } else {
            try {
                int day = Integer.parseInt(dayInput);
                if (day < 1 || day > 31) {
                    System.out.println("Invalid day. Please enter a number between 1 and 31.");
                    return askForDay(scanner);
                }
                return day;
            } catch (NumberFormatException e) {
                System.out.println("Invalid day format. Please enter a valid number.");
                return askForDay(scanner);
            }
        }
    }

    private List<Transaction> getFilteredTransactions(UUID userId, Integer year, Integer month, Integer day, TransactionType type) {
        List<Transaction> transactions = bank.getTransactionManager().readTransactionsForUser(userId);
        if (transactions == null || transactions.isEmpty()) {
            return new ArrayList<>();
        }
        return TransactionFilter.getTransactions(transactions, userId, year, month, day, type);  // Filtrera transaktioner
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