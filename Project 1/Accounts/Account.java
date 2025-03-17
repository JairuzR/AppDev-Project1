package Accounts;

import Bank.Bank;
import java.util.ArrayList;

public abstract class Account {

    private final Bank bank;
    private final String accountnumber;
    private final String ownerfname, ownerlname, owneremail;
    private String pin;
    private ArrayList<Transaction> Transactions;

    // Getters
    public Bank getBank() {
        return bank;
    }

    public String getAccountNumber() {
        return accountnumber;
    }

    public String getOwnerFName() {
        return ownerfname;
    }

    public String getOwnerLName() {
        return ownerlname;
    }

    public String getOwnerEmail() {
        return owneremail;
    }

    public String getPin() {
        return pin;
    }

    public ArrayList<Transaction> getTransactions() {
        return Transactions;
    }


    /**
     *
     * @param bank
     * @param AccountNumber
     * @param OwnerFName
     * @param OwnerLName
     * @param OwnerEmail
     * @param pin
     */
    public Account(Bank bank, String accountnumber,String ownerfname, String ownerlname, String owneremail, String pin) {
        this.bank = bank;
        this.accountnumber = accountnumber;
        this.ownerfname = ownerfname;
        this.ownerlname = ownerlname;
        this.owneremail = owneremail;
        this.pin = pin;
        this.Transactions = new ArrayList<>();
    }

    /**
     * Gets the full name of the account owner...
     *
     * @return Full name in "First Last" format.
     */
    public String getOwnerFullName() {
        return ownerfname + " " + ownerlname;
    }

    public void addNewTransaction(String accountNum, Transaction.Transactions type, String description) {
        Transactions.add(new Transaction(accountNum, type, description));
    }

    public String getTransactionInfo () {
        String result = "";
        for (Transaction i : Transactions) {
            result += i.toString() + "\n";
        }
        return result;
    }

    public String toString() { return String.format("Account number: %s, Owner: %s", this.accountnumber, getOwnerFullName()); }

}
