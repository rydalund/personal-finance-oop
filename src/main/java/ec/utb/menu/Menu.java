package ec.utb.menu;
import ec.utb.transaction.Bank;
import ec.utb.command.Command;
import ec.utb.command.CommandManager;
import java.util.ArrayList;
import java.util.List;

public abstract class Menu implements CommandManager {
    private final List<Command> commandList = new ArrayList<>();
    protected Bank bank;

    public Menu(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void registerCommand(Command command) {
        this.commandList.add(command);
    }

    @Override
    public void tryExecuteCommand(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("No input!");
        }

        String[] splitString = input.trim().toUpperCase().split(" ");
        String commandName = splitString[0];

        for (Command command : commandList) {
            if (command.getName().equalsIgnoreCase(commandName)) {
                command.executeCommand(splitString);
                return;
            }
        }
        System.out.println("Command not found!");
    }

    public abstract void show();
}