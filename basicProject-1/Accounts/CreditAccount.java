package Accounts;

import Bank.Bank;

public class CreditAccount extends Account implements Payment, Recompense {

    private double loan;

    public CreditAccount(Bank bank, String accountNumber, String pin, String ownerFirstName, String ownerLastName, String ownerEmail) {
        super(bank, accountNumber, pin, ownerFirstName, ownerLastName, ownerEmail);
        loan = 0.0;
    }

    public double getLoan() {
        return loan;
    }

    public String getLoanStatement() {
        return "Current Loan Amount: " + loan;
    }

    private boolean canCredit(double amountAdjustment) {
        double newLoanValue = loan + amountAdjustment;
        if (newLoanValue > getBank().getCreditLimit()) {
            return false;
        }
        if (newLoanValue < 0.0) {
            return false;
        }
        return true;
    }

    private void adjustLoanAmount(double amountAdjustment) {
        if (canCredit(amountAdjustment)) {
            loan = loan + amountAdjustment;
        } else {
            System.out.println("The amount cannot be credited due to exceeding the credit limit or being negative.");
        }
    }

    public boolean pay(Account account, double amount) throws IllegalAccountType {
        if (account instanceof CreditAccount) {
            throw new IllegalAccountType("Credit account cannot pay other credit accounts.");
        }

        SavingsAccount payerAccount = (SavingsAccount) account;
        boolean paymentSuccessful = payerAccount.cashDeposit(amount);

        if (paymentSuccessful) {
            adjustLoanAmount(amount);

            String description = "Paid $" + amount + " ";
            addNewTransaction(getAccountNumber(), Transaction.Transactions.Payment, description);
        }

        return paymentSuccessful;
    }

    public boolean recompense(double amount) {
        if (amount > loan) {
            System.out.println("The amount cannot be recompensed due to exceeding the loan amount.");
            return false;
        }

        if (amount <= 0.0) {
            System.out.println("The amount cannot be recompensed due to being none or negative.");
            return false;
        }

        adjustLoanAmount(-amount);

        String description = "Recompensed $" + amount;
        addNewTransaction(getAccountNumber(), Transaction.Transactions.Recompense, description);

        return true;
    }

    public String toString() {
        return super.toString() + "\n\t\t" + getLoanStatement();
    }
}
