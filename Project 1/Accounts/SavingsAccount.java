package Accounts;

import Bank.Bank;

public class SavingsAccount extends Account implements Withdrawal, Deposit, FundTransfer {

    private double balance;

    /**
     *
     * @param bank
     * @param ACCOUNTNUMBER
     * @param OWNERFNAME
     * @param OWNERLNAME
     * @param OWNEREMAIL
     * @param pin
     * @param balance
     */
    public SavingsAccount(Bank bank, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin, double balance) {
        super(bank, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.balance = balance;
    }

    public String getAccountBalanceStatement() {
//        TODO: Add a getBalance here
    }

    private boolean hasEnoughBalance(double amount) {
        return getBalance() >= amount; // TODO: Same as above
    }

    private void insufficientBalance() {
        System.out.printf("Transaction failed due to insufficient balance. Current Balance: %s", getAccountBalanceStatement());
    }

    private void adjustAccountBalance(double amount) {

    }

    public String toString() {

    }
    
}
