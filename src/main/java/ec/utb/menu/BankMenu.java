package ec.utb.menu;

import ec.utb.Bank;

public class BankMenu extends Menu {

    public BankMenu(Bank bank) {
        super(bank);
        registerCommand(new CreateTaskCommand(bank));
        registerCommand(new RemoveTaskCommand(bank));
        registerCommand(new CompleteTaskCommand(bank));
    }

    @Override
    public void show() {
        System.out.println("What do you want to do? (type 'help' to show commands)");
    }
}
