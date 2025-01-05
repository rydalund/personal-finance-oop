package ec.utb.command;
import ec.utb.Bank;
import ec.utb.data.TransactionDatabaseSaver;
import ec.utb.data.UserDatabaseSaver;
import ec.utb.user.AdminUser;
import ec.utb.user.User;
import java.util.Scanner;
import java.util.List;
import java.util.UUID;

public class DeleteUserCommand extends Command {
    private final TransactionDatabaseSaver transactionDatabaseSaver;
    private final UserDatabaseSaver userDatabaseSaver;

    public DeleteUserCommand(Bank bank) {
        super("DELETE-USER", "Deletes a user and their transactions \u001B[31m- only for Admins\u001B[0m", bank);
        this.transactionDatabaseSaver = new TransactionDatabaseSaver();
        this.userDatabaseSaver = new UserDatabaseSaver();
    }

    @Override
    public void executeCommand() {
        if (getBank().getLoggedInUser() instanceof AdminUser) {
            Scanner scanner = new Scanner(System.in);
            List<User> allUsers = getBank().getAllUsers();
            if (allUsers.isEmpty()) {
                System.out.println("No users found.");
                return;
            }
            System.out.println("List of users:");
            allUsers.forEach(user -> System.out.println("ID: " + user.getUserId() + ", Username: " + user.getUsername()));
            System.out.print("Enter the ID of the user to delete: ");
            String userIdStr = scanner.nextLine().trim();
            try {
                UUID userIdToDelete = UUID.fromString(userIdStr);
                User userToDelete = allUsers.stream()
                        .filter(user -> user.getUserId().equals(userIdToDelete))
                        .findFirst()
                        .orElse(null);
                if (userToDelete != null) {
                    transactionDatabaseSaver.deleteUserTransactions(userToDelete.getUserId());
                    userDatabaseSaver.deleteUser(userToDelete.getUserId());
                    System.out.println("User " + userToDelete.getUsername() + " has been deleted along with their transactions.");
                } else {
                    System.out.println("User not found.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid user ID format.");
            }
            System.out.println("Remaining users:");
            getBank().getAllUsers().forEach(user ->
                    System.out.println("ID: " + user.getUserId() + ", Username: " + user.getUsername()));
        } else {
            System.out.println("Only admins can delete users.");
        }
    }
}