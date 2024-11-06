package ec.utb.command;

public interface CommandManager {
    void registerCommand(Command command);
    void tryExecuteCommand(String commandInput);
}
