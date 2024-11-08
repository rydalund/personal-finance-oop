package ec.utb;

import ec.utb.menu.BankMenu;
import ec.utb.transaction.DepositTransaction;
import ec.utb.transaction.Transaction;
import ec.utb.transaction.TransactionFileSaver;
import ec.utb.transaction.TransactionSaver;

import java.util.List;


public class Application {

    public static void main(String[] args) {
        // Skapa en instans av Bank
        Bank bank = new Bank();

        // Skapa en instans av TransactionSaver (den som ansvarar för att spara och ta bort transaktioner)
        TransactionSaver transactionSaver = new TransactionFileSaver(); // Eller använd någon annan implementering av TransactionSaver

        // Skapa och visa BankMenu, som hanterar användarens kommandon
        BankMenu bankMenu = new BankMenu(bank, transactionSaver);
        bankMenu.show();
    }
}