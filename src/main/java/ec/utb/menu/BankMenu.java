package ec.utb.menu;
import ec.utb.transaction.Bank;
import ec.utb.command.*;
import ec.utb.transaction.TransactionManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankMenu implements CommandManager {

    private Bank bank;
    private TransactionManager transactionManager;
    private List<Command> commandList;

    public BankMenu(Bank bank, TransactionManager transactionManager) {
        this.bank = bank;
        this.transactionManager = transactionManager;
        this.commandList = new ArrayList<>();
        registerCommands();
    }

    @Override
    public void registerCommand(Command command) {
        commandList.add(command);
    }

    @Override
    public void tryExecuteCommand(String input) {
        String[] inputParts = input.split(" ", 2);
        String commandName = inputParts[0].toUpperCase();
        String[] args = (inputParts.length > 1) ? inputParts[1].split(" ") : new String[0];

        for (Command command : commandList) {
            if (command.getName().equalsIgnoreCase(commandName)) {
                command.executeCommand(args);
                return;
            }
        }

        System.out.println("Unknown command: " + commandName);
    }

    @Override
    public List<Command> getCommands() {
        return commandList;
    }

    public void printAvailableCommands() {
        System.out.println("List of available commands:");
        for (Command command : commandList) {
            System.out.println(command.getName() + " - " + command.getDescription());
        }
    }

    private void registerCommands() {
        registerCommand(new DepositCommand(bank, transactionManager));
        registerCommand(new WithdrawCommand(bank, transactionManager));
        registerCommand(new ShowCommand(bank, transactionManager));
        registerCommand(new HelpCommand(this));
        registerCommand(new BalanceCommand(bank));
        registerCommand(new ExitCommand(bank));
    }

    public void startMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n------ Bank Menu ------");
            printAvailableCommands();
            System.out.print("Enter your command: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("end")) {
                System.out.println("Exiting application...");
                running = false;
            } else {
                tryExecuteCommand(input);
            }
        }
        scanner.close();
    }
}