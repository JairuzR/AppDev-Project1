package Main;

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
    public boolean isLoggedIn() {
        return loggedBank != null;
    }
    
    /**
     * Handles the bank login process
     * @return boolean indicating if login was successful
     */
    public boolean bankLogin() {
        // Implementation would prompt for bank credentials
        // For now, returning false as placeholder
        return false;
    }
    
    /**
     * Logs out the current bank session
     * @return void
     */
    public void logout() {
        if (isLoggedIn()) {
            System.out.println("Logging out from bank: " + loggedBank.getName());
            loggedBank = null;
        } else {
            System.out.println("No bank is currently logged in.");
        }
    }
    
    /**
     * Shows all registered banks in the system
     * @return void
     */
    public void showAccounts() {
        if (BANKS.isEmpty()) {
            System.out.println("No banks are registered in the system.");
            return;
        }
        
        System.out.println("Registered Banks:");
        for (Bank bank : BANKS) {
            System.out.println(bank.toString());
        }
    }
    
    /**
     * Creates a new bank in the system
     * @return Bank the newly created bank
     */
    public Bank createNewBank() {
        // Implementation would prompt for bank details
        // For now, returning null as placeholder
        return null;
    }
    
    /**
     * Sets the logged bank
     * @param bank The bank to set as logged in
     * @return void
     */
    public void setLoggedBank(Bank bank) {
        if (bank != null) {
            loggedBank = bank;
            System.out.println("Now logged in as: " + bank.getName());
        }
    }
    
    /**
     * Finds an account across all banks in the system
     * @param accountNum The account number to search for
     * @param String The account information if found
     * @return Account The found account
     */
    public Account findAccount(String accountNum, String pin) {
        for (Bank bank : BANKS) {
            for (Account account : bank.getBANKACCOUNTS()) {
                if (account.getAccountNumber().equals(accountNum)) {
                    // Assuming Account has a validatePIN method
                    if (account.validatePIN(pin)) {
                        return account;
                    }
                    break;
                }
            }
        }
        return null;
    }
    
    /**
     * Adds a bank to the system
     * @param bank The bank to add
     * @return void
     */
    public void addBank(Bank bank) {
        if (bank != null && !BANKS.contains(bank)) {
            BANKS.add(bank);
            System.out.println("Bank " + bank.getName() + " added to the system.");
        }
    }
    
    /**
     * Shows the bank menu for a logged in bank
     * @return void
     */
    public void showBankMenu() {
        if (!isLoggedIn()) {
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
     * Gets the bank by ID
     * @param id The ID of the bank to find
     * @return Bank The found bank or null
     */
    public Bank getBank(int id) {
        for (Bank bank : BANKS) {
            if (bank.getID() == id) {
                return bank;
            }
        }
        return null;
    }
    
    /**
     * Compares two banks using a comparator
     * @param bank1 First bank to compare
     * @param bank2 Second bank to compare 
     * @param bank3 Third bank to compare
     * @return int The comparison result
     */
    public int getBank(Comparable<Bank> comparator, Bank bank1, Bank bank2, Bank bank3) {
        // Assuming this is some kind of three-way comparison
        // Implementation would depend on the comparator logic
        return 0;
    }
    
    /**
     * Finds an account by account number and string in all banks
     * @param accountNum Account number to search for
     * @param string Additional search parameter
     * @return Account The found account or null
     */
    public Account findAccount(String accountNum, String string, Account account) {
        return null;
    }
    
    /**
     * Gets the bank size
     * @return int The number of banks
     */
    public int bankSize() {
        return BANKS.size();
    }
}
