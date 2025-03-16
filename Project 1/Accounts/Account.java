package Accounts;

import Bank.Bank;
import java.util.ArrayList;

public abstract class Account {

    private Bank bank;
    private String ACCOUNTNUMBER;
    private String OWNERFNAME, OWNERLNAME, OWNEREMAIL;
    private String pin;
    private ArrayList<Transaction> Transactions;

    // Getters
    public Bank getBank() {
        return bank;
    }

    public String getACCOUNTNUMBER() {
        return ACCOUNTNUMBER;
    }

    public String getOWNERFNAME() {
        return OWNERFNAME;
    }

    public String getOWNERLNAME() {
        return OWNERLNAME;
    }

    public String getOWNEREMAIL() {
        return OWNEREMAIL;
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
     * @param ACCOUNTNUMBER
     * @param OWNERFNAME
     * @param OWNERLNAME
     * @param OWNEREMAIL
     * @param pin
     */
    public Account(Bank bank, String ACCOUNTNUMBER,String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin) {
        this.bank = bank;
        this.ACCOUNTNUMBER = ACCOUNTNUMBER;
        this.OWNERFNAME = OWNERFNAME;
        this.OWNERLNAME = OWNERLNAME;
        this.OWNEREMAIL = OWNEREMAIL;
        this.pin = pin;
        this.Transactions = new ArrayList<>();
    }

    /**
     * Gets the full name of the account owner.
     *
     * @return Full name in "First Last" format.
     */
    public String getOwnerFullName() {
        return OWNERFNAME + " " + OWNERLNAME;
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

    public String toString() { return String.format("Account number: %s, Owner: %s", this.ACCOUNTNUMBER, getOwnerFullName()); }

}
