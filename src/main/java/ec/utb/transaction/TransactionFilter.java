package ec.utb.transaction;
import ec.utb.NoTransactionsFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TransactionFilter {

    public static List<Transaction> getTransactions(List<Transaction> transactions, UUID userId, Integer year, Integer month, Integer day, TransactionType type) throws NoTransactionsFoundException {
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(transaction -> matchesUserId(transaction, userId))
                .filter(transaction -> matchesType(transaction, type))
                .filter(transaction -> matchesDate(transaction, year, month, day))
                .collect(Collectors.toList());
        if (filteredTransactions.isEmpty()) {
            throw new NoTransactionsFoundException("No transactions found matching filter.");
        }
        return filteredTransactions;
    }

    private static boolean matchesUserId(Transaction transaction, UUID userId) {
        return transaction.getUserId().equals(userId);
    }

    private static boolean matchesType(Transaction transaction, TransactionType type) {
        if (type == null) return true;
        return switch (type) {
            case DEPOSIT -> transaction instanceof DepositTransaction;
            case WITHDRAW -> transaction instanceof WithdrawTransaction;
            case BOTH -> transaction instanceof DepositTransaction || transaction instanceof WithdrawTransaction;
            default -> false;
        };
    }

    private static boolean matchesDate(Transaction transaction, Integer year, Integer month, Integer day) {
        LocalDate date = transaction.getTransactionDate();
        if (year != null && date.getYear() != year) return false;
        if (month != null && date.getMonthValue() != month) return false;
        return day == null || date.getDayOfMonth() == day;
    }
}