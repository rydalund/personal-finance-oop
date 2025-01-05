package ec.utb.data;
import ec.utb.user.AdminUser;
import ec.utb.user.ClientUser;
import ec.utb.user.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDatabaseSaver {

    public void addUser(User user) {
        String insertUserSQL = "INSERT INTO users (user_id, username, email, password_hash, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertUserSQL)) {
            statement.setObject(1, user.getUserId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPasswordHash());
            statement.setString(5, user.getRole());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(user.getRole() + " user added successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        String selectAllUsersSQL = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectAllUsersSQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                UUID userId = (UUID) resultSet.getObject("user_id");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String passwordHash = resultSet.getString("password_hash");
                String role = resultSet.getString("role");
                User user;
                if (role.equals("Admin")) {
                    user = new AdminUser(userId, username, email, passwordHash);
                } else {
                    user = new ClientUser(userId, username, email, passwordHash);
                }
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
        }
        return users;
    }

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

    public void updateUserRole(AdminUser adminUser) {
        String updateUserSQL = "UPDATE users SET role = ? WHERE user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateUserSQL)) {
            statement.setString(1, "Admin");
            statement.setObject(2, adminUser.getUserId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User role updated to Admin.");
            } else {
                System.out.println("Failed to update user role.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating user role: " + e.getMessage());
        }
    }
}