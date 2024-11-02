package ec.utb.transaction;

import ec.utb.Application;

public class WithdrawTransaction extends Transaction {
    public WithdrawTransaction(double entry, String transactionName, Application application) {
        super(entry, transactionName, application);
    }

    @Override
    protected void updateBankBalance(String[] commandArgs) {

    }
}
