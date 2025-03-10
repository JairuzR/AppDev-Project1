package Accounts;

import Bank.Bank;
/**
 * Represents a Credit Account with loan functionalities.
 */
public class CreditAccount extends Account implements Payment, Recompense {

    private double loan;
    
    /**
     * Constructs a Credit Account.
     * 
     * @param bank         The bank managing the account.
     * @param accountNum   Unique account number.
     * @param OWNERFNAME   First name of the account owner.
     * @param OWNERLNAME   Last name of the account owner.
     * @param OWNEREMAIL   Email of the account owner.
     * @param pin          Security PIN.
     */
    public CreditAccount(Bank bank, String accountNum, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin) {
        super(bank, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.loan = 0.0;
    }
    
    /**
     * Retrieves the loan statement.
     * @return Loan details as a formatted string.
     */
    public String getLoanStatement() {
        return "Outstanding Loan Balance: $" + String.format("%.2f", loan);
    }
    
    /**
     * Checks if credit adjustment is possible.
     * @param amountAdjustment The amount to adjust.
     * @return True if the adjustment is valid.
     */
    private boolean canCredit(double amountAdjustment) {
        return loan - amountAdjustment >= 0;
    }
    
    /**
     * Adjusts the loan amount (ex: repayments).
     *
     * @param amountAdjustment Amount to be adjusted.
     */
    private void adjustLoanAmount(double amountAdjustment) {
        if (canCredit(amountAdjustment)) {
            loan -= amountAdjustment;
        } else {
            throw new IllegalStateException("Cannot adjust loan beyond current debt.");
        }
    }

    @Override
    public boolean recompense(double amount) {
        if (amount > 0 && canCredit(amount)) {
            adjustLoanAmount(amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean pay(Account account, double amount) throws IllegalAccountType {
        if (amount <= 0) return false;
        // Assume valid account type checks happen here.
        return true;
    }
        


   @Override
    public String toString() {
        return super.toString() + ", Loan: $" + String.format("%.2f", loan);
    }
    
}
