package ec.utb.transaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionFilter {

    public static List<Transaction> getTransactions(List<Transaction> transactions, Integer year, Integer month, Integer day, TransactionType type) {
        List<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            boolean matches = true;

            if (type != null) {
                if (type == TransactionType.DEPOSIT && !(transaction instanceof DepositTransaction)) {
                    matches = false;
                }
                if (type == TransactionType.WITHDRAW && !(transaction instanceof WithdrawTransaction)) {
                    matches = false;
                }
                if (type == TransactionType.BOTH && !(transaction instanceof DepositTransaction) && !(transaction instanceof WithdrawTransaction)) {
                    matches = false;
                }
            }

            if (year != null && transaction.getTransactionDate().getYear() != year) {
                matches = false;
            }

            if (month != null && transaction.getTransactionDate().getMonthValue() != month) {
                matches = false;
            }

            if (day != null && transaction.getTransactionDate().getDayOfMonth() != day) {
                matches = false;
            }

            if (matches) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }
}