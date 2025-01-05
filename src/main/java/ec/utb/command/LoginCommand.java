package ec.utb.command;

import ec.utb.Bank;
import ec.utb.user.User;
import java.util.Scanner;

public class LoginCommand extends Command {

    private final Bank bank;

    public LoginCommand(Bank bank) {
        super("LOGIN", "Log in to your account", null);
        this.bank = bank;
    }

    @Override
    public void executeCommand() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();
        User user = bank.getAuthenticationManager().authenticate(username, password);
        if (user != null) {
            bank.getAuthenticationManager().setLoggedInUser(user);
            System.out.println("Logged in successfully as: " + user.getRole());
        } else {
            System.out.println("Invalid username or password.");
        }
    }
}