package Accounts;

import Bank.Bank;
import java.util.ArrayList;

public abstract  class Account {

    private Bank bank;

    private String ACCOUNTNUMBER;

    private String OWNERFNAME, OWNERLNAME, OWNEREMAIL;

    private String pin;

    private ArrayList<Transaction> Transactions;



    public Account(bank bank, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin) {
        
    }

    public String getOwnerFullName() {

    }

    publis void addNewTransaction(String accountNum, Transaction.Transactions type, String description) {

    }

    public String getTransactions() {
        
    }

    public String toString() {
        
    }
    
}
