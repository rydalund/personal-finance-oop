package ec.utb.command;
import ec.utb.Bank;

public class BalanceCommand extends Command {

    public BalanceCommand(Bank bank) {
        super("BALANCE", "Displays the current balance of the bank", bank);
    }

    @Override
    public void executeCommand() {
        double balance = getBank().getBalance();
        System.out.println("Current Balance: " + balance);
    }
}