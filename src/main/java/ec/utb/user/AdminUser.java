package ec.utb.user;

import java.util.UUID;

public class AdminUser extends User {

    public AdminUser(UUID userId, String username, String email, String passwordHash) {
        super(userId, username, email, passwordHash);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}