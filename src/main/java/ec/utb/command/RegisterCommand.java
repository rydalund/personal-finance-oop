package ec.utb.command;

import ec.utb.Bank;
import ec.utb.user.AuthenticationManager;

import java.util.Scanner;

public class RegisterCommand extends Command {

    private final Bank bank;

    public RegisterCommand(Bank bank) {
        super("REGISTER", "Register a new account", bank);
        this.bank = bank;
    }

    @Override
    public void executeCommand() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your desired username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();
        AuthenticationManager authenticationManager = bank.getAuthenticationManager();
        boolean success = authenticationManager.registerClientUser(username, email, password);
        if (success) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Username or email may already be in use.");
        }
    }
}