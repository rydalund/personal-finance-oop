package ec.utb;
import ec.utb.data.DatabaseConnection;
import ec.utb.data.TransactionDatabaseSaver;
import ec.utb.data.UserDatabaseSaver;
import ec.utb.menu.BankMenu;
import ec.utb.menu.Menu;
import ec.utb.transaction.*;
import ec.utb.user.AuthenticationManager;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) {
        try {
            DatabaseConnection.initializeDatabase();
            System.out.println("Database and tables are ready!");
        } catch (SQLException e) {
            System.err.println("An error occurred while initializing the database: " + e.getMessage());
        }
        UserDatabaseSaver userDatabaseSaver = new UserDatabaseSaver();
        AuthenticationManager authenticationManager = new AuthenticationManager(userDatabaseSaver);
        TransactionManager transactionManager = new TransactionDatabaseSaver();
        Bank bank = new Bank(transactionManager, authenticationManager);
        Menu bankMenu = new BankMenu(bank);

        //Skapar användare + lägger till admin-behörighet för test av funktionalitet
        if (registerAndUpgradeUserForTest(authenticationManager))
            System.out.println("Admin registered and upgraded for test purpose...");
        bankMenu.startMenu();
    }
    private static boolean registerAndUpgradeUserForTest(AuthenticationManager authenticationManager) {
        boolean isRegistered = authenticationManager.registerClientUser("admin", "admin@admin.com", "password");
        if (isRegistered) {
            return authenticationManager.upgradeClientToAdmin("admin", "admin@admin.com");
        }
        return false;
    }
}