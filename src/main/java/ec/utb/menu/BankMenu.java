package ec.utb.menu;
import ec.utb.Bank;
import ec.utb.command.*;
import ec.utb.transaction.TransactionManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankMenu implements CommandManager {

    private final Bank bank;
    private final TransactionManager transactionManager;
    private final List<Command> commandList;

    private boolean running;

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
        String commandName = input.trim().toUpperCase();

        for (Command command : commandList) {
            if (command.getName().equalsIgnoreCase(commandName)) {
                command.executeCommand();
                return;
            }
        }
        System.out.println("Oops " + commandName + " is an unknown command or too many arguments - please just type <command>!");
    }

    @Override
    public List<Command> getCommands() {
        return commandList;
    }

    public void printAvailableCommands() {
        System.out.println("List of available commands:");
        for (Command command : commandList) {
            if (!command.getName().equalsIgnoreCase("help")) {
                System.out.println("# " + command.getName() + " - " + command.getDescription());
            }
        }
    }

    private void registerCommands() {
        registerCommand(new DepositCommand(bank, transactionManager));
        registerCommand(new WithdrawCommand(bank, transactionManager));
        registerCommand(new ShowCommand(bank, transactionManager));
        registerCommand(new HelpCommand(this)); //registreras här men skrivs inte ut
        registerCommand(new BalanceCommand(bank));
        registerCommand(new DeleteCommand(bank, transactionManager));
        registerCommand(new ExitCommand(this));
    }

    public void startMenu() {
        running = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n------ Bank Menu ------");
        printAvailableCommands();

        while (running) {
            System.out.print("Enter your <command> (or type 'help' to show commands): ");
            String input = scanner.nextLine().trim();
            tryExecuteCommand(input);  // Kör kommandot
        }
        scanner.close();
    }
    public void stopMenu() {
        running = false;
    }
}