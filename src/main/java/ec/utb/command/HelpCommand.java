package ec.utb.command;

public class HelpCommand extends Command {

    private CommandManager commandManager;
    public HelpCommand(CommandManager commandManager) {
        super("HELP", "Displays a list of available commands", null);
        this.commandManager = commandManager;
    }

    @Override
    public void executeCommand(String[] splitString) {
        printAvailableCommands();
    }

    private void printAvailableCommands() {
        System.out.println("List of available commands:");
        for (Command command : commandManager.getCommands()) {
            System.out.println(command.getName() + " - " + command.getDescription());
        }
    }
}