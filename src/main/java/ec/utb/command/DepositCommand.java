package ec.utb.command;

import ec.utb.Bank;
import ec.utb.transaction.DepositTransaction;
import ec.utb.transaction.Transaction;
import ec.utb.transaction.TransactionType;

import java.util.Scanner;

public class DepositCommand extends Command {

    public DepositCommand(Bank bank) {
        super("DEPOSIT", bank);  // "DEPOSIT" är namnet på kommandot, ingen beskrivning behövs längre
    }

    @Override
    public void executeCommand(String[] splitString) {

    }
}