package ec.utb.command;
import ec.utb.transaction.Bank;

public abstract class Command {

    private final String name;
    private final String description;
    protected Bank bank;

    public Command(String name, String description, Bank bank) {
        this.name = name;
        this.description = description;
        this.bank = bank;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Bank getBank() {
        return bank;
    }

    public abstract void executeCommand(String[] splitString);
}