package ec.utb.data;

import java.sql.*;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "password";
    private static final String DB_NAME = "personal_finance";

    public static String getDbUrl() {
        return DB_URL;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static String getDbPassword() {
        return DB_PASSWORD;
    }

    public static String getDbName() {
        return DB_NAME;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + DB_NAME, DB_USER, DB_PASSWORD);
    }

    public static void createDatabaseIfNotExists() throws SQLException {
        String checkDbSql = "SELECT 1 FROM pg_database WHERE datname = ?";
        String createDbSql = "CREATE DATABASE " + DB_NAME;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement checkStatement = connection.prepareStatement(checkDbSql)) {
            checkStatement.setString(1, DB_NAME);
            ResultSet resultSet = checkStatement.executeQuery();
            if (!resultSet.next()) {
                try (Statement createStatement = connection.createStatement()) {
                    createStatement.executeUpdate(createDbSql);
                    System.out.println("Database '" + DB_NAME + "' created successfully.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error checking or creating database: " + e.getMessage());
            throw e;
        }
    }

    public static void createTablesIfNotExists() throws SQLException {
        String createUsersTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "user_id UUID PRIMARY KEY, "
                + "username VARCHAR(50) UNIQUE NOT NULL, "
                + "email VARCHAR(100) UNIQUE NOT NULL, "
                + "password_hash VARCHAR(255) NOT NULL, "
                + "role VARCHAR(50)"
                + ");";

        String createTransactionsTableSQL = "CREATE TABLE IF NOT EXISTS transactions ("
                + "transaction_id UUID PRIMARY KEY, "
                + "amount DECIMAL(10, 2), "
                + "description VARCHAR(255), "
                + "date DATE, "
                + "type VARCHAR(50), "
                + "user_id UUID, "
                + "FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE"
                + ");";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createUsersTableSQL);
            System.out.println("Users table created or already exists.");
            statement.executeUpdate(createTransactionsTableSQL);
            System.out.println("Transactions table created or already exists.");
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
            throw e;
        }
    }

    public static void initializeDatabase() throws SQLException {
        createDatabaseIfNotExists();
        createTablesIfNotExists();
    }
}