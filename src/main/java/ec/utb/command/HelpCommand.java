package ec.utb.command;

public class HelpCommand extends Command {

    private final CommandManager commandManager;
    public HelpCommand(CommandManager commandManager) {
        super("HELP", "Displays a list of available commands", null);
        this.commandManager = commandManager;
    }

    @Override
    public void executeCommand() {
        System.out.println("-----------------------");
        System.out.println("List of available commands:");
        for (Command command : commandManager.getCommands()) {
            if (!command.getName().equalsIgnoreCase("HELP")) {
                System.out.println("# " + command.getName() + " - " + command.getDescription());
            }
        }
    }
}