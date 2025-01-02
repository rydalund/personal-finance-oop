package ec.utb.data;
import ec.utb.user.AdminUser;
import ec.utb.user.ClientUser;
import ec.utb.user.User;
import ec.utb.user.UserManager;

import java.sql.*;
import java.util.UUID;

public class UserDatabaseSaver implements UserManager {

    @Override
    public void addUser(User user) {
        String insertUserSQL = "INSERT INTO users (user_id, username, email, password_hash) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertUserSQL)) {

            statement.setObject(1, user.getUserId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPasswordHash());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(user.getRole() + " user added successfully!");
            }

        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
    }

    @Override
    public User getUserByUsername(String username) {
        String selectUserSQL = "SELECT * FROM users WHERE username = ?";
        User user = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectUserSQL)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                UUID userId = (UUID) resultSet.getObject("user_id");
                String email = resultSet.getString("email");
                String passwordHash = resultSet.getString("password_hash");
                String role = resultSet.getString("role");  // Assuming you added a "role" field to the users table

                if (role.equals("Admin")) {
                    user = new AdminUser(userId, username, email, passwordHash);
                } else {
                    user = new ClientUser(userId, username, email, passwordHash);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        String selectUserSQL = "SELECT * FROM users WHERE email = ?";
        User user = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectUserSQL)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                UUID userId = (UUID) resultSet.getObject("user_id");
                String username = resultSet.getString("username");
                String passwordHash = resultSet.getString("password_hash");
                String role = resultSet.getString("role");

                if (role.equals("Admin")) {
                    user = new AdminUser(userId, username, email, passwordHash);
                } else {
                    user = new ClientUser(userId, username, email, passwordHash);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }

        return user;
    }

    @Override
    public void deleteUser(UUID userId) {
        String deleteUserSQL = "DELETE FROM users WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteUserSQL)) {

            statement.setObject(1, userId);
            statement.executeUpdate();
            System.out.println("User with ID " + userId + " deleted successfully.");

        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }
}