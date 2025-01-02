package ec.utb.data;
import ec.utb.transaction.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

public class TransactionDatabaseSaver implements TransactionManager {

    public TransactionDatabaseSaver() {
        createDatabaseIfNotExists();
        createTransactionTableIfNotExists();
    }

    private void createDatabaseIfNotExists() {
        String checkDbSql = "SELECT 1 FROM pg_database WHERE datname = ?";
        String createDbSql = "CREATE DATABASE " + DatabaseConnection.getDbName();

        try (Connection connection = DriverManager.getConnection(DatabaseConnection.getDbUrl(), DatabaseConnection.getDbUser(), DatabaseConnection.getDbPassword());
             PreparedStatement checkStatement = connection.prepareStatement(checkDbSql)) {

            checkStatement.setString(1, DatabaseConnection.getDbName());
            ResultSet resultSet = checkStatement.executeQuery();

            if (!resultSet.next()) {
                try (Statement createStatement = connection.createStatement()) {
                    createStatement.executeUpdate(createDbSql);
                    System.out.println("Database '" + DatabaseConnection.getDbName() + "' created successfully.");
                }
            } else {
                System.out.println("Database '" + DatabaseConnection.getDbName() + "' already exists.");
            }

        } catch (SQLException e) {
            System.err.println("Error checking or creating database: " + e.getMessage());
        }
    }

    private void createTransactionTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS transactions ("
                + "transaction_id UUID PRIMARY KEY, "
                + "amount DECIMAL(10, 2), "
                + "description VARCHAR(255), "
                + "date DATE, "
                + "type VARCHAR(50)"
                + ");";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Error creating transactions table: " + e.getMessage());
        }
    }

    @Override
    public List<Transaction> readTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                UUID transactionId = UUID.fromString(resultSet.getString("transaction_id"));
                double amount = resultSet.getDouble("amount");
                String transactionName = resultSet.getString("description");
                LocalDate transactionDate = resultSet.getDate("date").toLocalDate();
                TransactionType transactionType = TransactionType.valueOf(resultSet.getString("type"));
                Transaction transaction = createTransaction(transactionId, amount, transactionName, transactionDate, transactionType);
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            System.err.println("Error reading transactions from database: " + e.getMessage());
        }

        return transactions;
    }

    private Transaction createTransaction(UUID transactionId, double amount, String transactionName, LocalDate transactionDate, TransactionType transactionType) {
        switch (transactionType) {
            case DEPOSIT:
                return new DepositTransaction(transactionId, amount, transactionName, transactionDate);
            case WITHDRAW:
                return new WithdrawTransaction(transactionId, amount, transactionName, transactionDate);
            default:
                throw new IllegalArgumentException("Unknown transaction: " + transactionType);
        }
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (transaction_id, amount, description, date, type) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, transaction.getTransactionId());
            statement.setDouble(2, transaction.getAmount());
            statement.setString(3, transaction.getTransactionName());
            statement.setDate(4, Date.valueOf(transaction.getTransactionDate()));
            statement.setString(5, transaction.getTransactionType().name());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Transaction saved!");
            }

        } catch (SQLException e) {
            System.err.println("Error saving transaction to database: " + e.getMessage());
        }
    }

    @Override
    public void deleteTransaction(UUID transactionId) {
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, transactionId.toString());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Transaction deleted!");
            }

        } catch (SQLException e) {
            System.err.println("Error deleting transaction from database: " + e.getMessage());
        }
    }
}