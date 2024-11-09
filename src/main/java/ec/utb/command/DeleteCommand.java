package ec.utb.command;
import ec.utb.transaction.Bank;

public class DeleteCommand extends Command {

    //Avvakta test
    public DeleteCommand(String name, String description, Bank bank) {
        super(name, description, bank);
    }

    public void executeCommand() {
        executeCommand(null);
    }

    @Override
    public void executeCommand(String[] splitString) {

    }
}