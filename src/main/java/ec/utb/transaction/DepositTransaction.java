package ec.utb.transaction;

import ec.utb.Application;
import ec.utb.Bank;

import java.time.LocalDate;
import java.util.UUID;

public class DepositTransaction extends Transaction {

    public DepositTransaction(double entry,String transactionName, LocalDate transactionDate, TransactionType type, Bank bank) {
        super(entry, transactionName);
        transactionDate = LocalDate.now();
        TransactionType type1 = TransactionType.DEPOSIT;
        double balance = bank.getBalance() + entry;
    }
}
