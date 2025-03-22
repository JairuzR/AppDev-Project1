package Main;

import java.util.Comparator;
import java.util.ArrayList;
import Bank.Bank;
import Accounts.*;

public class BankLauncher {
    public static final ArrayList<Bank> banks = new ArrayList<>(); //originally private pero akong gi public para ma access sa uban class
    private static Bank loggedBank = null; // Initially no bank is logged in
    
    /**
     * Checks if a bank is currently logged in
     * @return boolean indicating login status
     */
    public static boolean isLogged() {
        return loggedBank != null;
    }
    
    /**
     * Initializes the bank system
     * @return void
     */
    public static void bankInit() {
        // Implementation to initialize the bank system

        while(isLogged()){
            Main.showMenuHeader("Bank Menu");
            Main.showMenu(31);
            Main.setOption();

            if (Main.getOption() == 1) {
                showAccounts();
            } else if (Main.getOption() == 2) {
                newAccounts();
            } else if (Main.getOption() == 3) {
                logout();
            } else {
                System.out.println("Invalid Option. Please try again.");
            }
        }
    }

    private static void showAccounts() {
        if(isLogged()){
            Main.showMenu(32);
            Main.setOption();
            switch(Main.getOption()){
                case 1:
                    loggedBank.showAccounts(CreditAccount.class);
                    break;

                case 2:
                    //savings account
                    loggedBank.showAccounts(SavingsAccount.class);
                    break;

                case 3:
                    //all accounts
                    loggedBank.showAccounts(Account.class);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }

    }

    
    /**
     * Creates new accounts in the system
     * @return void
     */
    private static void newAccounts() {
        if (!isLogged()) {
            System.out.println("No bank is logged in. Please log in first.");
            return;
        }
        
        Main.showMenu(33);
        Main.setOption();

        switch(Main.getOption()){
            case 1:
                //new credit account
                CreditAccount newCreditAccount = loggedBank.createNewCreditAccount();
                if (newCreditAccount != null) {
                    System.out.println("Credit account with account number " + newCreditAccount.getAccountNumber() + " is created successfully!");
                }
                break;
            case 2:
                //new savings account
                SavingsAccount newSavingsAccount = loggedBank.createNewSavingsAccount();
                if (newSavingsAccount != null) {
                    System.out.println("Savings account with account number " + newSavingsAccount.getAccountNumber() + " is created successfully!");
                }
                break;
            default:
                System.out.println("Invalid input. Please try again.");
        }

    }
    
    /**
     * Handles the bank login process
     * @return void
     */
    public static void bankLogin() {
        // Implementation would prompt for bank credentials
        System.out.println("Bank login process initiated.");
    }
    
    /**
     * Sets the logged bank session
     * @param b The bank to set as logged in
     * @return void
     */
    public static void setLogSession(Bank b) {
        if (b != null) {
            loggedBank = b;
            System.out.println("Now logged in as: " + b.getName());
        }
    }
    
    /**
     * Logs out the current bank session
     * @return void
     */
    public static void logout() {
        if (isLogged()) {
            System.out.println("Logging out from bank: " + loggedBank.getName());
            loggedBank = null;
        } else {
            System.out.println("No bank is currently logged in.");
        }
    }
    
    /**
     * Creates a new bank in the system
     * @return void
     */
    public static void createNewBank() {
        // Implementation would prompt for bank details
        System.out.println("Creating a new bank in the system.");
    }
    
    /**
     * Shows the bank menu for a logged in bank
     * @return void
     */
    
    public static void showBanksMenu() {
        if (!isLogged()) {
            System.out.println("No bank is logged in. Please log in first.");
            return;
        }
        
        // Implementation would display bank-specific options
        System.out.println("Bank Menu for " + loggedBank.getName() + ":");
        System.out.println("1. Show All Accounts");
        System.out.println("2. Create New Account");
        System.out.println("3. Find Account");
        System.out.println("4. Bank Settings");
        System.out.println("5. Logout");
    }
    
    /**
     * Adds a bank to the system
     * @param b The bank to add
     * @return void
     */
    public static void addBank(Bank b) {
        if (b != null && !banks.contains(b)) {
            banks.add(b);
            System.out.println("Bank " + b.getName() + " added to the system.");
        }
    }
    
    /**
     * Gets the bank by comparator and two bank parameters
     * @param comparator Comparator for banks
     * @param bank Bank to compare
     * @return Bank The resulting bank based on comparison
     */
    public static Bank getBank(Comparator<Bank> comparator, Bank bank) {
        // Loop to find bank if it exists using some comparator

        for (int i = 0; i < bankSize(); i++) {
            if (comparator.compare(bank, banks.get(i)) == 0) {
                return banks.get(i);
            }
        }
        return null; // Bank not found
    }
    
    /**
     * Finds an account by account number
     * @param accountNum Account number to search for
     * @return Account The found account or null
     */
    public static Account findAccount(String accountNum) {
        for (Bank bank : banks) {
            for (Account account : bank.getAccount_of_Bank()) {
                if (account.getAccountNumber().equals(accountNum)) {
                    return account;
                }
            }
        }
        return null;
    }
    
    /**
     * Gets the bank size (number of banks in the system)
     * @return int The number of banks
     */
    public static int bankSize() {
        return banks.size();
    }
}
