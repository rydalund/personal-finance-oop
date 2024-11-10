package ec.utb.menu;
import ec.utb.Bank;
import ec.utb.command.*;
import ec.utb.transaction.TransactionManager;
import java.util.Scanner;

public class BankMenu extends Menu {

    private final Bank bank;
    private final TransactionManager transactionManager;
    private boolean running;

    public BankMenu(Bank bank, TransactionManager transactionManager) {
        this.bank = bank;
        this.transactionManager = transactionManager;
        registerCommands();
    }

    @Override
    protected void registerCommands() {
        registerCommand(new DepositCommand(bank, transactionManager));
        registerCommand(new WithdrawCommand(bank, transactionManager));
        registerCommand(new ShowCommand(bank, transactionManager));
        registerCommand(new HelpCommand(this));
        registerCommand(new BalanceCommand(bank));
        registerCommand(new DeleteCommand(bank, transactionManager));
        registerCommand(new ExitCommand(this));
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
            tryExecuteCommand(input);
        }
        scanner.close();
    }
    @Override
    public void stopMenu() {
        running = false;
    }
}