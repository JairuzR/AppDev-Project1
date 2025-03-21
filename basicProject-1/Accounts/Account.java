package Accounts;

import Bank.Bank;
import java.util.ArrayList;

public abstract class Account {

    private Bank bank;
    private String accountNumber;
    private String ownerFirstName;
    private String ownerLastName;
    private String ownerEmail;
    private String pin;

    private ArrayList<Transaction> transactions;

    public Account(Bank bank, String accountNumber, String pin, String ownerFirstName, String ownerLastName, String ownerEmail) {
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
        this.ownerEmail = ownerEmail;
        this.pin = pin;
        transactions = new ArrayList<Transaction>();
    }

    public Bank getBank() {
        return bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getOwnerFullName() {
        return ownerFirstName + " " + ownerLastName;
    }

    public void addNewTransaction(String accountNumber, Transaction.Transactions transactionType, String description) {
        Transaction transaction = new Transaction(accountNumber, transactionType, description);
        transactions.add(transaction);
    }

    public String getTransactionsInfo() {
        if (transactions.isEmpty()) {
            return "No transactions";
        }

        String result = "";
        int counter = 1;
        for (int i = 0; i < transactions.size(); i++) {
            String formatted = "[" + counter + "] " + transactions.get(i).description + "\n";
            result = result + formatted;
            counter++;
        }
        return result;
    }

    public String toString() {
        return "Account No." + getAccountNumber() + " Owner: " + getOwnerFullName();
    }
}
