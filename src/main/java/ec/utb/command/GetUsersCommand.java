package ec.utb.command;

import ec.utb.Bank;
import ec.utb.data.UserDatabaseSaver;
import ec.utb.user.AdminUser;
import ec.utb.user.User;
import java.util.List;

public class GetUsersCommand extends Command {

    private final UserDatabaseSaver userDatabaseSaver;

    public GetUsersCommand(Bank bank) {
        super("ALL-USERS", "Fetches and displays all users \u001B[31m- only for Admins\u001B[0m", bank);
        this.userDatabaseSaver = new UserDatabaseSaver();
    }

    @Override
    public void executeCommand() {
        if (getBank().getLoggedInUser() instanceof AdminUser) {
            List<User> users = userDatabaseSaver.getAllUsers();
            if (users.isEmpty()) {
                System.out.println("No users found.");
            } else {
                System.out.println("All users:");
                for (User user : users) {
                    System.out.println("ID: " + user.getUserId() + ", Username: " + user.getUsername() + ", Role: " + user.getRole());
                }
            }
        } else {
            System.out.println("Only admins can view all users.");
        }
    }
}