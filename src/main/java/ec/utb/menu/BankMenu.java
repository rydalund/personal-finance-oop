package ec.utb.menu;
import ec.utb.Bank;
import ec.utb.command.*;
import java.util.Scanner;

public class BankMenu extends Menu {

    private final Bank bank;
    private boolean running;

    public BankMenu(Bank bank) {
        this.bank = bank;
        registerCommands();
    }

    @Override
    protected void registerCommands() {
        registerCommand(new RegisterCommand(bank));
        registerCommand(new LoginCommand(bank));
        registerCommand(new DepositCommand(bank));
        registerCommand(new WithdrawCommand(bank));
        registerCommand(new BalanceCommand(bank));
        registerCommand(new ShowCommand(bank));
        registerCommand(new DeleteCommand(bank));
        registerCommand(new LogoutCommand(bank));
        registerCommand(new ExitCommand(this));
        registerCommand(new HelpCommand(this));
        registerCommand(new GetUsersCommand(bank));
        registerCommand(new DeleteUserCommand(bank));
    }

    @Override
    public void printAvailableCommands() {
        System.out.println("List of available commands:");
        for (Command command : commandList) {
            if (!command.getName().equalsIgnoreCase("HELP")) {
                System.out.println("# " + command.getName() + " - " + command.getDescription());
            }
        }
    }

    @Override
    public void startMenu() {
        running = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n------ Bank Menu ------");
        printAvailableCommands();

        while (running) {
            System.out.print("Enter your <command> (or type 'help' to show commands): ");
            String input = scanner.nextLine().trim();
            if (bank.getLoggedInUser() == null && !input.equalsIgnoreCase("login") && !input.equalsIgnoreCase("register")) {
                if (input.equalsIgnoreCase("exit")) {
                    tryExecuteCommand("EXIT");
                    break;
                }
                System.out.println("You must be logged in to access bank features. Please login first.");
                continue;
            }
            tryExecuteCommand(input);
        }
        scanner.close();
    }

    @Override
    public void stopMenu() {
        running = false;
    }
}