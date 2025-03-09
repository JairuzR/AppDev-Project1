package Accounts;

import Bank.Bank;

public class CreditAccount extends Account implements Payment, Recompense {

    private double loan;



    public Account(bank bank, String accountNum, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin) {
        
    }

    public String getLoanStatement() {

    }

    private boolean canCredit(double amountAdjustment) {
        
    }

    private void adjustLoanAmount(double amountAdjustment) {

    }

    public String toString() {

    }
    
}
