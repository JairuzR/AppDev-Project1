package Bank;

import java.util.ArrayList;
import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;

public class Bank {
    private int ID;
    private String name, passcode;
    private double DEPOSITLIMIT, WITHDRAWLIMIT, CREDITLIMIT;
    private double processingFee;
    private ArrayList<Account> BANKACCOUNTS;

    // Constructor with basic parameters
    public Bank(int ID, String name, String passcode) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.BANKACCOUNTS = new ArrayList<Account>();
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

    //Getters
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

    public ArrayList<Account> getAccount_of_Bank() {
        return BANKACCOUNTS;
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
        System.out.println("Account with number " + accountNum + " not found.");
    }
    // Method to create a new general account
    public Account createNewAccount(String accountType, String accountNumber, String ownerFName, String ownerLName, String ownerEmail, String pin, double initialBalance) {
        // Check if the account number already exists
        if (accountExists(this, accountNumber)) {
            throw new IllegalArgumentException("Account number " + accountNumber + " already exists.");
        }
    
        switch (accountType.toLowerCase()) {
            case "savings":
                return new SavingsAccount(this, accountNumber, ownerFName, ownerLName, ownerEmail, pin, initialBalance);
            case "credit":
                return new CreditAccount(this, accountNumber, ownerFName, ownerLName, ownerEmail, pin);
            default:
                throw new IllegalArgumentException("Invalid account type: " + accountType);
        }
    }

    // Method to create a new credit account
    public CreditAccount createNewCreditAccount(String accountNumber, String ownerFName, String ownerLName, String ownerEmail, String pin) {
        // Check if the account number already exists
        if (accountExists(this, accountNumber)) {
            throw new IllegalArgumentException("Account number " + accountNumber + " already exists.");
        }
        // Create and return a new CreditAccount
        return new CreditAccount(this, accountNumber, ownerFName, ownerLName, ownerEmail, pin);
    }

    // Method to create a new savings account
    public SavingsAccount createNewSavingsAccount(String accountNumber, String ownerFName, String ownerLName, String ownerEmail, String pin, double initialBalance) {
        // Check if the account number already exists
        if (accountExists(this, accountNumber)) {
            throw new IllegalArgumentException("Account number " + accountNumber + " already exists.");
        }
        // Create and return a new SavingsAccount
        return new SavingsAccount(this, accountNumber, ownerFName, ownerLName, ownerEmail, pin, initialBalance);
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
