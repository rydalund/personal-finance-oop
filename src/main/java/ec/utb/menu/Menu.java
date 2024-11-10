package ec.utb.menu;
import ec.utb.command.Command;
import ec.utb.command.CommandManager;
import java.util.ArrayList;
import java.util.List;

public abstract class Menu implements CommandManager {

    protected final List<Command> commandList;

    public Menu() {
        this.commandList = new ArrayList<>();
    }

    @Override
    public void registerCommand(Command command) {
        commandList.add(command);
    }

    @Override
    public void tryExecuteCommand(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("No input provided!");
        }
        String commandName = input.trim().toUpperCase();
        for (Command command : commandList) {
            if (command.getName().equalsIgnoreCase(commandName)) {
                command.executeCommand();
                return;
            }
        }
        System.out.println("Command not found: " + commandName);
    }

    @Override
    public List<Command> getCommands() {
        return commandList;
    }

    protected abstract void registerCommands();
    public abstract void printAvailableCommands();
    public abstract void startMenu();
    public abstract void stopMenu();
}