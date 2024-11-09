package ec.utb.command;
import java.util.List;

public interface CommandManager {
    void registerCommand(Command command);
    void tryExecuteCommand(String input);
    List<Command> getCommands();
}