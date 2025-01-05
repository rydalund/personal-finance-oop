package ec.utb.user;

import ec.utb.data.UserDatabaseSaver;
import java.util.UUID;

public class AuthenticationManager {

    private final UserDatabaseSaver userDatabaseSaver;
    private User loggedInUser;

    public AuthenticationManager(UserDatabaseSaver userDatabaseSaver) {
        this.userDatabaseSaver = userDatabaseSaver;
    }

    public User authenticate(String username, String password) {
        User user = userDatabaseSaver.getUserByUsername(username);
        if (user != null && PasswordUtils.checkPassword(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public boolean registerClientUser(String username, String email, String password) {
        if (userDatabaseSaver.getUserByUsername(username) != null || userDatabaseSaver.getUserByEmail(email) != null) {
            return false;
        }
        String hashedPassword = PasswordUtils.hashPassword(password);
        System.out.println("Hashed password for client user: " + hashedPassword);
        User newUser = new ClientUser(UUID.randomUUID(), username, email, hashedPassword);
        userDatabaseSaver.addUser(newUser);
        return true;
    }

    public boolean upgradeClientToAdmin(String username, String email) {
        User existingUser = null;
        if (userDatabaseSaver.getUserByUsername(username) != null) {
            existingUser = userDatabaseSaver.getUserByUsername(username);
        } else if (userDatabaseSaver.getUserByEmail(email) != null) {
            existingUser = userDatabaseSaver.getUserByEmail(email);
        }
        if (existingUser == null) {
            System.out.println("User not found.");
            return false;
        }
        if (existingUser instanceof ClientUser) {
            AdminUser upgradedAdminUser = new AdminUser(
                    existingUser.getUserId(),
                    existingUser.getUsername(),
                    existingUser.getEmail(),
                    existingUser.getPasswordHash()
            );
            try {
                userDatabaseSaver.updateUserRole(upgradedAdminUser);
                System.out.println("User role upgraded to Admin.");
                return true;
            } catch (Exception e) {
                System.out.println("Error upgrading user role: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("User is already an Admin.");
            return false;
        }
    }

    public void logout() {
        if (loggedInUser != null) {
            System.out.println("Logging out " + loggedInUser.getUsername() + "...");
            loggedInUser = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }
}