package ec.utb.data;
import ec.utb.transaction.*;
import ec.utb.user.AdminUser;
import ec.utb.user.ClientUser;
import ec.utb.user.User;
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
                + "type VARCHAR(50), "
                + "user_id UUID"
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
        return readTransactionsForUser(null);
    }

    @Override
    public List<Transaction> readTransactionsForUser(UUID userId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        if (userId != null) {
            sql += " WHERE user_id = ?";
        }
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (userId != null) {
                statement.setObject(1, userId);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID transactionId = UUID.fromString(resultSet.getString("transaction_id"));
                double amount = resultSet.getDouble("amount");
                String transactionName = resultSet.getString("description");
                LocalDate transactionDate = resultSet.getDate("date").toLocalDate();
                TransactionType transactionType = TransactionType.valueOf(resultSet.getString("type"));
                UUID userIdFromDb = UUID.fromString(resultSet.getString("user_id"));
                Transaction transaction = createTransaction(transactionId, amount, transactionName, transactionDate, transactionType, userIdFromDb);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Error reading transactions from database: " + e.getMessage());
        }
        return transactions;
    }

    @Override
    public Transaction readTransactionById(UUID transactionId) {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, transactionId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("transaction_id"));
                double amount = resultSet.getDouble("amount");
                String transactionName = resultSet.getString("description");
                LocalDate transactionDate = resultSet.getDate("date").toLocalDate();
                TransactionType transactionType = TransactionType.valueOf(resultSet.getString("type"));
                UUID userIdFromDb = UUID.fromString(resultSet.getString("user_id"));
                return createTransaction(id, amount, transactionName, transactionDate, transactionType, userIdFromDb);
            }
        } catch (SQLException e) {
            System.err.println("Error reading transaction by ID from database: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (transaction_id, amount, description, date, type, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, transaction.getTransactionId());
            statement.setDouble(2, transaction.getAmount());
            statement.setString(3, transaction.getTransactionName());
            statement.setDate(4, Date.valueOf(transaction.getTransactionDate()));
            statement.setString(5, transaction.getTransactionType().name());
            statement.setObject(6, transaction.getUserId());
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
            statement.setObject(1, transactionId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Transaction deleted!");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting transaction from database: " + e.getMessage());
        }
    }

    @Override
    public void deleteUserTransactions(UUID userId) {
        String sql = "DELETE FROM transactions WHERE user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, userId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("All transactions for user with ID " + userId + " have been deleted.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting transactions for user from database: " + e.getMessage());
        }
    }

    private Transaction createTransaction(UUID transactionId, double amount, String transactionName, LocalDate transactionDate, TransactionType transactionType, UUID userId) {
        switch (transactionType) {
            case DEPOSIT:
                return new DepositTransaction(transactionId, amount, transactionName, transactionDate, userId);
            case WITHDRAW:
                return new WithdrawTransaction(transactionId, amount, transactionName, transactionDate, userId);
            default:
                throw new IllegalArgumentException("Unknown transaction type: " + transactionType);
        }
    }


    @Override
    public List<User> readAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                UUID userId = UUID.fromString(resultSet.getString("user_id"));
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String passwordHash = resultSet.getString("password_hash");
                String role = resultSet.getString("role");
                User user;
                if ("Admin".equals(role)) {
                    user = new AdminUser(userId, username, email, passwordHash);
                } else {
                    user = new ClientUser(userId, username, email, passwordHash);
                }
                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving users from database: " + e.getMessage());
        }
        return users;
    }
}