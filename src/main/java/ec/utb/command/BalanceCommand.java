package ec.utb.command;
import ec.utb.Bank;
import ec.utb.user.User;

public class BalanceCommand extends Command {

    public BalanceCommand(Bank bank) {
        super("balance", "Shows the current balance for the logged-in user", bank);
    }

    @Override
    public void executeCommand() {
        Bank bank = getBank();
        User loggedInUser = bank.getLoggedInUser();

        if (loggedInUser != null) {
            double balance = bank.getUserBalance(loggedInUser.getUserId());
            System.out.println("Your current balance is: " + balance);
        } else {
            System.out.println("You need to log in first to check your balance.");
        }
    }
}