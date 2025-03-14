package Bank;

import java.util.ArrayList;
import java.util.Random;

import Accounts.Account;

public class Bank {

    private int ID;
    private String name, passcode;
    private double DEPOSITLIMIT, WITHDRAWLIMIT, CREDITLIMIT;
    private double processingFee;
    private ArrayList<Account> BANKACCOUNTS;

    public Bank(int ID, String name, String passcode) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.DEPOSITLIMIT = 10000.0; // Default deposit limit
        this.WITHDRAWLIMIT = 5000.0; // Default withdraw limit
        this.CREDITLIMIT = 2000.0;   // Default credit limit
        this.processingFee = 5.0;    // Default processing fee
        this.BANKACCOUNTS = new ArrayList<Account>();
    }

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

    // Display all accounts of a specific type
    public void showAccounts(Class<? extends Account> accountType) {
        System.out.println("All " + accountType.getSimpleName() + " accounts at " + this.name + ":");
        boolean found = false;
        for (Account account : BANKACCOUNTS) {
            if (accountType.isInstance(account)) {
                System.out.println(account.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No " + accountType.getSimpleName() + " accounts found.");
        }
    }

    // Get and display details of a specific account
    public void getBankAccount(Bank bank, String accountNum) {
        for (Account account : bank.BANKACCOUNTS) {
            if (account.getAccountNumber().equals(accountNum)) {
                System.out.println("Account found:");
                System.out.println(account.toString());
                return;
            }
        }
        System.out.println("Account with number " + accountNum + " not found.");
    }

    // Create a new general account and return its details
    public ArrayList<String> createNewAccount() {
        ArrayList<String> accountDetails = new ArrayList<>();
        String accountNumber = generateAccountNumber();
        
        accountDetails.add("Account Number: " + accountNumber);
        accountDetails.add("Bank: " + this.name);
        accountDetails.add("Bank ID: " + this.ID);
        accountDetails.add("Deposit Limit: $" + this.DEPOSITLIMIT);
        accountDetails.add("Withdrawal Limit: $" + this.WITHDRAWLIMIT);
        accountDetails.add("Processing Fee: $" + this.processingFee);
        accountDetails.add("Creation Date: " + java.time.LocalDate.now());
        
        return accountDetails;
    }

    // Create a new credit account
    public CreditAccount createNewCreditAccount() {
        String accountNumber = generateAccountNumber();
        double interestRate = 0.1599; // Default credit card interest rate (15.99%)
        CreditAccount creditAccount = new CreditAccount(accountNumber, 0.0, this.CREDITLIMIT, interestRate);
        System.out.println("Created new credit account with number: " + accountNumber);
        System.out.println("Credit limit: $" + this.CREDITLIMIT);
        return creditAccount;
    }

    // Create a new savings account
    public SavingsAccount createNewSavingsAccount() {
        String accountNumber = generateAccountNumber();
        double interestRate = 0.035; // Default savings interest rate (3.5%)
        double minimumBalance = 100.0; // Default minimum balance
        SavingsAccount savingsAccount = new SavingsAccount(accountNumber, 0.0, interestRate, minimumBalance);
        System.out.println("Created new savings account with number: " + accountNumber);
        System.out.println("Interest rate: " + (interestRate * 100) + "%");
        System.out.println("Minimum balance: $" + minimumBalance);
        return savingsAccount;
    }

    // Add a new account to the bank
    public void addNewAccount(Account account) {
        if (!accountExists(this, account.getAccountNumber())) {
            BANKACCOUNTS.add(account);
            System.out.println("Account " + account.getAccountNumber() + " successfully added to " + this.name);
            System.out.println("Total accounts in bank: " + BANKACCOUNTS.size());
        } else {
            System.out.println("Error: Account with number " + account.getAccountNumber() + " already exists!");
        }
    }
    
    // Check if an account exists in the bank
    public static boolean accountExists(Bank bank, String accountNum) {
        for (Account account : bank.BANKACCOUNTS) {
            if (account.getAccountNumber().equals(accountNum)) {
                return true;
            }
        }
        return false;
    }

    // Process a deposit with bank limits and fees
    public boolean processDeposit(String accountNum, double amount) {
        if (amount > DEPOSITLIMIT) {
            System.out.println("Error: Deposit amount exceeds bank limit of $" + DEPOSITLIMIT);
            return false;
        }
        
        for (Account account : BANKACCOUNTS) {
            if (account.getAccountNumber().equals(accountNum)) {
                double depositAmount = amount - processingFee;
                boolean success = account.deposit(depositAmount);
                
                if (success) {
                    System.out.println("Deposit processed successfully.");
                    System.out.println("Amount: $" + amount);
                    System.out.println("Processing fee: $" + processingFee);
                    System.out.println("Net deposit: $" + depositAmount);
                    return true;
                } else {
                    System.out.println("Failed to process deposit.");
                    return false;
                }
            }
        }
        
        System.out.println("Account not found.");
        return false;
    }
    
    // Process a withdrawal with bank limits and fees
    public boolean processWithdrawal(String accountNum, double amount) {
        if (amount > WITHDRAWLIMIT) {
            System.out.println("Error: Withdrawal amount exceeds bank limit of $" + WITHDRAWLIMIT);
            return false;
        }
        
        for (Account account : BANKACCOUNTS) {
            if (account.getAccountNumber().equals(accountNum)) {
                double withdrawalAmount = amount + processingFee;
                boolean success = account.withdraw(withdrawalAmount);
                
                if (success) {
                    System.out.println("Withdrawal processed successfully.");
                    System.out.println("Amount: $" + amount);
                    System.out.println("Processing fee: $" + processingFee);
                    System.out.println("Total deducted: $" + withdrawalAmount);
                    return true;
                } else {
                    System.out.println("Failed to process withdrawal. Insufficient funds or account restrictions.");
                    return false;
                }
            }
        }
        
        System.out.println("Account not found.");
        return false;
    }

    // Transfer money between accounts in this bank
    public boolean transferFunds(String fromAccount, String toAccount, double amount) {
        if (!accountExists(this, fromAccount) || !accountExists(this, toAccount)) {
            System.out.println("Error: One or both accounts not found.");
            return false;
        }
        
        Account sourceAccount = null;
        Account destinationAccount = null;
        
        for (Account account : BANKACCOUNTS) {
            if (account.getAccountNumber().equals(fromAccount)) {
                sourceAccount = account;
            }
            if (account.getAccountNumber().equals(toAccount)) {
                destinationAccount = account;
            }
        }
        
        // Apply processing fee to source account
        double totalDeduction = amount + processingFee;
        
        if (sourceAccount.getBalance() >= totalDeduction) {
            sourceAccount.withdraw(totalDeduction);
            destinationAccount.deposit(amount);
            
            System.out.println("Transfer successful:");
            System.out.println("From: " + fromAccount);
            System.out.println("To: " + toAccount);
            System.out.println("Amount: $" + amount);
            System.out.println("Processing fee: $" + processingFee);
            System.out.println("Total deducted: $" + totalDeduction);
            
            return true;
        } else {
            System.out.println("Error: Insufficient funds for transfer.");
            return false;
        }
    }

    // Helper method to generate unique account numbers
    private String generateAccountNumber() {
        Random random = new Random();
        // Format: BANKID-RANDOMNUMBER-TIMESTAMP
        return String.format("%03d-%04d-%04d", 
                            this.ID, 
                            random.nextInt(10000),
                            (int)(System.currentTimeMillis() % 10000));
    }

    // Authenticate the bank access using passcode
    public boolean authenticate(String enteredPasscode) {
        return this.passcode.equals(enteredPasscode);
    }

    // Getters and setters
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public double getDEPOSITLIMIT() {
        return DEPOSITLIMIT;
    }
    
    public void setDEPOSITLIMIT(double DEPOSITLIMIT) {
        this.DEPOSITLIMIT = DEPOSITLIMIT;
    }

    public double getWITHDRAWLIMIT() {
        return WITHDRAWLIMIT;
    }
    
    public void setWITHDRAWLIMIT(double WITHDRAWLIMIT) {
        this.WITHDRAWLIMIT = WITHDRAWLIMIT;
    }

    public double getCREDITLIMIT() {
        return CREDITLIMIT;
    }
    
    public void setCREDITLIMIT(double CREDITLIMIT) {
        this.CREDITLIMIT = CREDITLIMIT;
    }

    public double getProcessingFee() {
        return processingFee;
    }
    
    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }

    public ArrayList<Account> getBANKACCOUNTS() {
        return BANKACCOUNTS;
    }

    // Return string representation of the bank
    public String toString() {
        return "Bank Information:" +
               "\n===================" +
               "\nID: " + this.ID + 
               "\nName: " + this.name + 
               "\nNumber of Accounts: " + this.BANKACCOUNTS.size() +
               "\nDeposit Limit: $" + String.format("%.2f", this.DEPOSITLIMIT) +
               "\nWithdrawal Limit: $" + String.format("%.2f", this.WITHDRAWLIMIT) +
               "\nCredit Limit: $" + String.format("%.2f", this.CREDITLIMIT) +
               "\nProcessing Fee per Transaction: $" + String.format("%.2f", this.processingFee);
    }
}
