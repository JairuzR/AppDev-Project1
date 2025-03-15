package Bank;

import java.util.ArrayList;
import Accounts.*;

public class Bank {
    // Fields as shown in the UML
    private int ID;
    private String name, passcode;
    private double DEPOSITLIMIT, WITHDRAWLIMIT, CREDITLIMIT;
    private double processingFee;
    private ArrayList<Account> BANKACCOUNTS;

    // Getters    
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPasscode() {
        return passcode;
    }

    public double getDEPOSITLIMIT() {
        return DEPOSITLIMIT;
    }

    public double getWITHDRAWLIMIT() {
        return WITHDRAWLIMIT;
    }

    public double getCREDITLIMIT() {
        return CREDITLIMIT;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public ArrayList<Account> getBANKACCOUNTS() {
        return BANKACCOUNTS;
    }


    // Constructor with basic parameters
    public Bank(int ID, String name, String passcode) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.BANKACCOUNTS = new ArrayList<>(); // Suggest ng IDE na <> ra daw, dili <Account>
    }

    // Constructor with all parameters
    public Bank(int ID, String name, String passcode, double DEPOSITLIMIT, double WITHDRAWLIMIT, double CREDITLIMIT, double processingFee) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.DEPOSITLIMIT = DEPOSITLIMIT;
        this.WITHDRAWLIMIT = WITHDRAWLIMIT;
        this.CREDITLIMIT = CREDITLIMIT;
        this.processingFee = processingFee;
        this.BANKACCOUNTS = new ArrayList<>();
    }

    // Method to display accounts of a specific type
    public void showAccounts(Class<? extends Account> accountType) {
        for (Account account : BANKACCOUNTS) {
            if (accountType.isInstance(account)) {
                System.out.println(account.toString());
            }
        }
    }

    // Method to retrieve a specific account by account number
    public void getBankAccount(Bank bank, String accountNum) {
        for (Account account : bank.BANKACCOUNTS) {
            if (account.getACCOUNTNUMBER().equals(accountNum)) {
                System.out.println(account.toString());
                return;
            }
        }
    }

    // Method to create a new general account
    public ArrayList<String> createNewAccount() {
        ArrayList<String> accountDetails = new ArrayList<>();
        // Implementation as per UML
        String accountNumber = generateAccountNumber();
        accountDetails.add(accountNumber);
        return accountDetails;
    }

    // Method to create a new credit account
    public CreditAccount createNewCreditAccount() {
        String accountNumber = generateAccountNumber();
        return new CreditAccount(accountNumber, 0.0, this.CREDITLIMIT);
    }

    // Method to create a new savings account
    public SavingsAccount createNewSavingsAccount() {
        String accountNumber = generateAccountNumber();
        return new SavingsAccount(accountNumber, 0.0);
    }

    // Method to add a new account to the bank
    public void addNewAccount(Account account) {
        if (!accountExists(this, account.getACCOUNTNUMBER())) {
            BANKACCOUNTS.add(account);
        }
    }
    
    // Static method to check if an account exists
    public static boolean accountExists(Bank bank, String accountNum) {
        for (Account account : bank.BANKACCOUNTS) {
            if (account.getACCOUNTNUMBER().equals(accountNum)) {
                return true;
            }
        }
        return false;
    }

    // Additional methods from UML
    
    // Method to get bank info as a string (format: ID, String, String)
    public String getBankInfo() {
        return ID + ", " + name + ", " + passcode;
    }
    
    // Method to show accounts of a bank
    public void showAccounts() {
        for (Account account : BANKACCOUNTS) {
            System.out.println(account.toString());
        }
    }
    
    // Method to create a new account as a list of fields
    public ArrayList<String> createNewAccount(String[] fields) {
        ArrayList<String> accountDetails = new ArrayList<>();
        // Implementation that uses the fields array for account creation
        String accountNumber = generateAccountNumber();
        accountDetails.add(accountNumber);
        // Add other details from fields
        for (String field : fields) {
            accountDetails.add(field);
        }
        return accountDetails;
    }
    
    // Method to check if an account exists in this bank
    public boolean accountExists(String accountNum) {
        return accountExists(this, accountNum);
    }
    
    // Helper method to generate account numbers
    private String generateAccountNumber() {
        return String.valueOf(this.ID) + System.currentTimeMillis() % 10000;
    }

    // toString method
    public String toString() {
        return "Bank ID: " + this.ID + 
               ", Name: " + this.name + 
               ", Accounts: " + this.BANKACCOUNTS.size() +
               ", Deposit Limit: $" + this.DEPOSITLIMIT +
               ", Withdrawal Limit: $" + this.WITHDRAWLIMIT +
               ", Credit Limit: $" + this.CREDITLIMIT;
    }
}
