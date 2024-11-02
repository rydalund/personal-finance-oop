package ec.utb.transaction;

import ec.utb.Application;

public class DepositTransaction extends Transaction {

    public DepositTransaction(double entry, String transactionName, Application application) {
        super(entry, transactionName, application);
    }

    @Override
    protected void updateBankBalance(String[] commandArgs) {

    }
}
