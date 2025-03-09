package Accounts;

import Bank.Bank;

public class SavingsAccount extends Account implements Withdrawal, Deposit, FundTransfer {

    private double balance;



    public SavingsAccount(bank bank, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin, double balance) {
        
    }

    public String getAccountBalanceStatement() {

    }

    private boolean hasEnoughBalance(double amount) {

    }

    private void insufficientBalande() {

    }

    private void adjustACcountBalance(double amount) {

    }

    public String toString() {
        
    }
    
}
