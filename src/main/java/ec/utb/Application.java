package ec.utb;

import ec.utb.data.TransactionDatabaseSaver;
import ec.utb.menu.BankMenu;
import ec.utb.menu.Menu;
import ec.utb.transaction.*;

public class Application {

    public static void main(String[] args) {
        // Skapa en instans av TransactionDatabaseSaver som hanterar databasen
        TransactionManager transactionManager = new TransactionDatabaseSaver();

        // Skapa en instans av Bank och BankMenu som använder den nya databashanteraren
        Bank bank = new Bank(transactionManager);
        Menu bankMenu = new BankMenu(bank, transactionManager);

        // Starta menyhanteraren
        bankMenu.startMenu();
    }
}