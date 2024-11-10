package ec.utb;
import ec.utb.menu.BankMenu;
import ec.utb.menu.Menu;
import ec.utb.transaction.*;


public class Application {

    public static void main(String[] args) {
        String filePath = "transactions.csv";
        TransactionFileSaver transactionFileSaver = new TransactionFileSaver(filePath);
        Bank bank = new Bank(transactionFileSaver);
        Menu bankMenu = new BankMenu(bank, transactionFileSaver);
        bankMenu.startMenu();
    }
}