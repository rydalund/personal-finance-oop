package ec.utb.command;

import ec.utb.Bank;

import java.util.Scanner;

public abstract class Command {
    Scanner scanner = new Scanner(System.in);
    private String name;
    protected Bank bank;

    public Command (String name, String description, Bank bank) {
        this.name = name;
        this.bank = bank;
    }

    public abstract void executeCommand(String[] splitString);

    public String getName() {
        return name;
    }

    protected static double testInputLength(String[] splitString) {
       double inputDouble = 0.00;

        if (splitString.length == 2) {
            String inputDoubleString = splitString[1];
            try {
                inputDouble = Double.valueOf(inputDoubleString);
                return inputDouble;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                inputDouble = 0.00;
            }
            return inputDouble;
        } else {
            throw new IllegalArgumentException();
        }
    }
}