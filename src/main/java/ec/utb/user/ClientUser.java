package ec.utb.user;

import java.util.UUID;

public class ClientUser extends User {

    public ClientUser(UUID userId, String username, String email, String passwordHash) {
        super(userId, username, email, passwordHash);
    }

    @Override
    public String getRole() {
        return "Client";
    }
}