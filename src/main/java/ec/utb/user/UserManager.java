package ec.utb.user;

import java.util.UUID;

public interface UserManager {

    void addUser(User user);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    void deleteUser(UUID userId);
}