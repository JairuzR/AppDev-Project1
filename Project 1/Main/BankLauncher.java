package Main;

import java.util.Comparator;
import java.util.ArrayList;
import Bank.Bank;
import Accounts.Account;

public class BankLauncher {
    private static final ArrayList<Bank> BANKS = new ArrayList<>();
    private static Bank loggedBank = null; // Initially no bank is logged in
    
    /**
     * Checks if a bank is currently logged in
     * @return boolean indicating login status
     */
    public boolean isLogged() {
        return loggedBank != null;
    }
    
    /**
     * Initializes the bank system
     * @return void
     */
    public void bankInit() {
        // Implementation to initialize the bank system
        System.out.println("Bank system initialized.");
    }
    
    /**
     * Creates new accounts in the system
     * @return void
     */
    public void newAccounts() {
        if (!isLogged()) {
            System.out.println("No bank is logged in. Please log in first.");
            return;
        }
        
        System.out.println("Creating new accounts for bank: " + loggedBank.getName());
        // Implementation for creating new accounts
    }
    
    /**
     * Handles the bank login process
     * @return void
     */
    public void bankLogin() {
        // Implementation would prompt for bank credentials
        System.out.println("Bank login process initiated.");
    }
    
    /**
     * Sets the logged bank session
     * @param b The bank to set as logged in
     * @return void
     */
    public void setLogSession(Bank b) {
        if (b != null) {
            loggedBank = b;
            System.out.println("Now logged in as: " + b.getName());
        }
    }
    
    /**
     * Logs out the current bank session
     * @return void
     */
    public void logout() {
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
    public void createNewBank() {
        // Implementation would prompt for bank details
        System.out.println("Creating a new bank in the system.");
    }
    
    /**
     * Shows the bank menu for a logged in bank
     * @return void
     */
    public void showBanksMenu() {
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
    public void addBank(Bank b) {
        if (b != null && !BANKS.contains(b)) {
            BANKS.add(b);
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
            if (comparator.compare(bank, BANKS.get(i)) == 0) {
                return BANKS.get(i);
            }
        }
        return null; // Bank not found
    }
    
    /**
     * Finds an account by account number
     * @param accountNum Account number to search for
     * @return Account The found account or null
     */
    public Account findAccount(String accountNum) {
        for (Bank bank : BANKS) {
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
        return BANKS.size();
    }
}
