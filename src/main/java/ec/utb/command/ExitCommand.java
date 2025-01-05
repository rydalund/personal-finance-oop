package ec.utb.command;
import ec.utb.menu.BankMenu;

public class ExitCommand extends Command {
    private final BankMenu bankMenu;
    public ExitCommand(BankMenu bankMenu) {
        super("EXIT", "Exit the application", null);
        this.bankMenu = bankMenu;
    }

    @Override
    public void executeCommand() {
        System.out.println("Exiting application...");
        bankMenu.stopMenu();
    }
}