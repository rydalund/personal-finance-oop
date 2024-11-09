package ec.utb.command;

import ec.utb.transaction.Bank;

public class ExitCommand extends Command {

    public ExitCommand(Bank bank) {
        super("END", "Exit the application", bank);
    }

    @Override
    public void executeCommand(String[] splitString) {
        System.out.println("Exiting application. Goodbye!");
        System.exit(0);
    }
}
