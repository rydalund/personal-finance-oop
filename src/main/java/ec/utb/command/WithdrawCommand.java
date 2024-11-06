package ec.utb.command;

import ec.utb.Bank;

public class WithdrawCommand extends Command {

    public WithdrawCommand(Bank bank) {
        super("WITHDRAW", bank);
    }


    @Override
    public void executeCommand(String[] splitString) {

    }
}
