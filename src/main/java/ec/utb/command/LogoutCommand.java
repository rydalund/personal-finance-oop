package ec.utb.command;

import ec.utb.Bank;
import ec.utb.user.User;

public class LogoutCommand extends Command {

    private final Bank bank;

    public LogoutCommand(Bank bank) {
        super("LOGOUT", "Log out of your account", null);
        this.bank = bank;
    }

    @Override
    public void executeCommand() {
        User loggedInUser = bank.getLoggedInUser();

        if (loggedInUser != null) {
            bank.getAuthenticationManager().logout();
            System.out.println("Logout successful.");
        } else {
            System.out.println("No user is currently logged in.");
        }
    }
}