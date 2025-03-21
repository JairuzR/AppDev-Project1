package Accounts;

import Bank.Bank;

public class SavingsAccount extends Account implements FundTransfer, Deposit, Withdrawal {

    private double balance;

    public SavingsAccount(Bank bank, String accountNumber, String pin, String ownerFirstName, String ownerLastName, String ownerEmail, double initialBalance) {
        super(bank, accountNumber, pin, ownerFirstName, ownerLastName, ownerEmail);
        cashDeposit(initialBalance);
    }

    public double getAccountBalance() {
        return balance;
    }

    private boolean hasEnoughBalance(double amount) {
        return balance >= amount;
    }

    public String getAccountBalanceStatement() {
        return String.valueOf(balance);
    }

    private void insufficientBalance() {
        System.out.println("Current Balance: " + getAccountBalanceStatement() + ". Transaction failed due to insufficient funds.");
    }

    public boolean transfer(Bank bank, Account account, double amount) throws IllegalAccountType {
        if (!(account instanceof SavingsAccount)) {
            throw new IllegalAccountType("You cannot transfer to a non-savings account.");
        }

        SavingsAccount recipient = (SavingsAccount) bank.getBankAccount(bank, account.getAccountNumber());

        if (recipient == null) {
            System.out.println("The recipient account does not exist in " + bank.getName());
        } else if (amount > getBank().getWithdrawLimit()) {
            System.out.println("The amount you are trying to transfer is too large for the bank.");
        } else if (!hasEnoughBalance(amount)) {
            insufficientBalance();
        } else {
            recipient.adjustBalance(amount);
            adjustBalance(-amount - getBank().getProcessingFee());

            String description = "Transfer $" + amount + " from Account No." + getAccountNumber() + " to " + bank.getName() + " Account No." + recipient.getAccountNumber();
            addNewTransaction(getAccountNumber(), Transaction.Transactions.FundTransfer, description);

            String recipientDescription = "Transfer from " + getAccountNumber();
            recipient.addNewTransaction(getAccountNumber(), Transaction.Transactions.Deposit, recipientDescription);

            return true;
        }
        return false;
    }

    public boolean transfer(Account account, double amount) throws IllegalAccountType {
        if (!(account instanceof SavingsAccount)) {
            throw new IllegalAccountType("You cannot transfer to a non-savings account.");
        }

        SavingsAccount recipient = (SavingsAccount) getBank().getBankAccount(getBank(), account.getAccountNumber());

        if (recipient == null) {
            System.out.println("The recipient account does not exist in this bank.");
        } else if (amount > getBank().getWithdrawLimit()) {
            System.out.println("The amount you are trying to transfer is too large for the bank.");
        } else if (!hasEnoughBalance(amount)) {
            insufficientBalance();
        } else {
            recipient.adjustBalance(amount);
            adjustBalance(-amount);

            String description = "Transfer $" + amount + " from Account No." + getAccountNumber() + " to Account No." + recipient.getAccountNumber();
            addNewTransaction(getAccountNumber(), Transaction.Transactions.FundTransfer, description);
            recipient.addNewTransaction(getAccountNumber(), Transaction.Transactions.Deposit, description);

            return true;
        }
        return false;
    }

    public boolean cashDeposit(double amount) {
        if (amount > getBank().getDepositLimit()) {
            System.out.println("The amount you are trying to deposit is too large for the bank.");
        } else {
            adjustBalance(amount);

            String description = "Deposit $" + amount;
            addNewTransaction(getAccountNumber(), Transaction.Transactions.Deposit, description);
            return true;
        }
        return false;
    }

    public boolean withdrawal(double amount) {
        if (amount > getBank().getWithdrawLimit()) {
            System.out.println("The amount you are trying to withdraw is too large for the bank.");
        } else if (!hasEnoughBalance(amount)) {
            insufficientBalance();
        } else {
            adjustBalance(-amount);

            String description = "Withdraw $" + amount;
            addNewTransaction(getAccountNumber(), Transaction.Transactions.Withdraw, description);
            return true;
        }
        return false;
    }

    private void adjustBalance(double amount) {
        balance = balance + amount;
        if (balance < 0.0) {
            balance = 0.0;
        }
    }

    public String toString() {
        return super.toString() + "\n\t\tCurrent Balance Amount: " + getAccountBalanceStatement();
    }
} 