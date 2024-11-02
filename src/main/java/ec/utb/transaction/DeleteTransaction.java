package ec.utb.transaction;

import ec.utb.Application;

public class DeleteTransaction extends Transaction {
    public DeleteTransaction(double entry, String transactionName, Application application) {
        super(entry, transactionName, application);
    }

    @Override
    protected void updateBankBalance(String[] commandArgs) {

    }
}
