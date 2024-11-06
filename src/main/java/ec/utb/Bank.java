package ec.utb;

import java.io.Serializable;

public class Bank implements Serializable {
    double balance;

    public Bank () {
        balance = 0.00;
    }

    public void setBalance (double balance){
        this.balance = balance;
    }

    public double getBalance (){
        return balance;
    }
   /* public static void main(String [] args) {
        Bank bank = new Bank();
        TransactionManager transactionManager = new TransactionManager();
        BankFileHandler.initializeBankBalanceFromFile(bank);
        transactionManager.initializeTransactionPrompt(bank);
    }*/
}