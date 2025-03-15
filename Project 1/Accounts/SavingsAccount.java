package Accounts;

import Bank.Bank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.ReentrantLock;

public class SavingsAccount extends Account implements Deposit, Withdrawal, FundTransfer {

    private double balance; // Account balance

    public double getBalance() {
        return balance;
    }

    private final ReentrantLock lock = new ReentrantLock();
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    
    private static class LockGuard implements AutoCloseable {
        private final ReentrantLock lock;
        public LockGuard(ReentrantLock lock) {
            this.lock = lock;
            this.lock.lock();
        }
        @Override
        public void close() {
            lock.unlock();
        }
    }

    /**
     * Constructs a SavingsAccount.
     * 
     * @param bank         The associated bank.
     * @param ACCOUNTNUMBER The unique account number.
     * @param OWNERFNAME   The owner's first name.
     * @param OWNERLNAME   The owner's last name.
     * @param OWNEREMAIL   The owner's email.
     * @param pin          The account pin.
     * @param balance      The initial balance.
     * @throws IllegalArgumentException if balance is negative or exceeds the bank's deposit limit.
     */
    public SavingsAccount(Bank bank, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin, double balance) {
        super(bank, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        if (balance < 0 || balance > bank.getDepositLimit()) {
            throw new IllegalArgumentException("Initial balance must be non-negative and not exceed the bank's deposit limit of $" + bank.getDepositLimit());
        }
        this.balance = balance;
        logTransaction(Transaction.Transactions.Deposit, "Account created with initial balance: $" + balance);
    }

    /**
     * Deposits cash into the account.
     * @param amount Amount to deposit.
     * @return True if deposit is successful.
     * @throws IllegalArgumentException if amount is non-positive or exceeds the bank's deposit limit.
     */
    @Override
    public boolean cashDeposit(double amount) {
        if (amount <= 0 || amount > getBank().getDepositLimit()) {
            throw new IllegalArgumentException("Deposit must be > 0 and not exceed the bank's deposit limit of $" + getBank().getDepositLimit());
        }
        try (LockGuard guard = new LockGuard(lock)) {
            balance += amount;
            logTransaction(Transaction.Transactions.Deposit, "Deposited: $" + amount);
            System.out.println("Deposit successful! New Balance: $" + balance);
            return true;
        }
    }

    /**
     * Withdraws cash from the account.
     * @param amount Amount to withdraw.
     * @return True if withdrawal is successful.
     * @throws IllegalArgumentException if amount is non-positive or exceeds the bank's withdrawal limit.
     */
    @Override
    public boolean withdrawal(double amount) {
        if (amount <= 0 || amount > getBank().getWithdrawLimit()) {
            throw new IllegalArgumentException("Withdrawal must be > 0 and not exceed the bank's withdrawal limit of $" + getBank().getWithdrawLimit());
        }
        try (LockGuard guard = new LockGuard(lock)) {
            if (amount > balance) {
                insufficientBalance();
                return false;
            }
            balance -= amount;
            if (balance < 0.0) { 
                balance = 0.0;
            }
            logTransaction(Transaction.Transactions.Withdraw, "Withdrawn: $" + amount);
            System.out.println("Withdrawal successful! New Balance: $" + balance);
            return true;
        }
    }

    /**
     * Transfers funds to another savings account within the same bank.
     * @param recipient The recipient account.
     * @param amount Amount to transfer.
     * @return True if transfer is successful.
     * @throws IllegalArgumentException if amount is non-positive or exceeds the bank's withdrawal limit.
     * @throws IllegalAccountType if recipient is not a SavingsAccount.
     */
    @Override
    public boolean transfer(Account recipient, double amount) throws IllegalAccountType {
        if (amount <= 0 || amount > getBank().getWithdrawLimit()) {
            throw new IllegalArgumentException("Transfer must be > 0 and not exceed the bank's withdrawal limit of $" + getBank().getWithdrawLimit());
        }
        if (!(recipient instanceof SavingsAccount)) {
            throw new IllegalAccountType("Transfers can only be made between savings accounts.");
        }
        SavingsAccount recipientAccount = (SavingsAccount) recipient;
        ReentrantLock firstLock = (this.getACCOUNTNUMBER().compareTo(recipient.getACCOUNTNUMBER()) < 0) ? this.lock : recipientAccount.lock;
        ReentrantLock secondLock = (firstLock == this.lock) ? recipientAccount.lock : this.lock;
        try (LockGuard guard1 = new LockGuard(firstLock); LockGuard guard2 = new LockGuard(secondLock)) {
            if (amount > balance) {
                insufficientBalance();
                return false;
            }
            balance -= amount;
            recipientAccount.adjustAccountBalance(amount);
            logTransaction(Transaction.Transactions.FundTransfer, "Transferred $" + amount + " to Account: " + recipient.getACCOUNTNUMBER());
            System.out.println("Transfer successful! New Balance: $" + balance);
            return true;
        }
    }

    /**
     * Transfers funds to an account in a different bank, applying the processing fee.
     * @param recipientBank The recipient bank.
     * @param recipient The recipient account.
     * @param amount Amount to transfer.
     * @return True if transfer is successful.
     * @throws IllegalArgumentException if amount is non-positive or exceeds the bank's withdrawal limit.
     * @throws IllegalAccountType if recipient is not a SavingsAccount.
     */
    @Override
    public boolean transfer(Bank recipientBank, Account recipient, double amount) throws IllegalAccountType {
        if (amount <= 0 || amount > getBank().getWithdrawLimit()) {
            throw new IllegalArgumentException("Transfer must be > 0 and not exceed the bank's withdrawal limit of $" + getBank().getWithdrawLimit());
        }
        if (!(recipient instanceof SavingsAccount)) {
            throw new IllegalAccountType("Transfers can only be made between savings accounts.");
        }
        if (recipientBank == null) {
            throw new IllegalArgumentException("Recipient bank cannot be null.");
        }
        double processingFee = getBank().getProcessingFee();
        try (LockGuard guard = new LockGuard(lock)) {
            if (amount + processingFee > balance) {
                insufficientBalance();
                return false;
            }
            balance -= (amount + processingFee);
            SavingsAccount recipientAccount = (SavingsAccount) recipient;
            recipientAccount.adjustAccountBalance(amount);
            logTransaction(Transaction.Transactions.FundTransfer, "Transferred $" + amount + " to Account: " 
                + recipient.getACCOUNTNUMBER() + " in Bank: " + recipientBank.getBankName() 
                + " (Processing Fee: $" + processingFee + ")");
            System.out.println("Cross-bank transfer successful! New Balance: $" + balance);
            return true;
        }
    }

    public String getAccountBalanceStatement() {
        return "Account: " + getACCOUNTNUMBER() + " | Balance: $" + balance;
    }

    private boolean hasEnoughBalance(double amount) {
        return balance >= amount;
    }

    private void insufficientBalance() {
        System.out.printf("Transaction failed due to insufficient balance. Current Balance: %s\n", getAccountBalanceStatement());
    }

    private void adjustAccountBalance(double amount) {
        try (LockGuard guard = new LockGuard(lock)) {
            balance += amount;
            if (balance < 0.0) {
                balance = 0.0;
            }
        }
    }

    private void logTransaction(Transaction.Transactions type, String description) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        addNewTransaction(getAccountNumber(), type, description + " [" + timestamp + "]");
    }

    @Override
    public String toString() {
        return "SavingsAccount{AccountNumber='" + getAccountNumber() + "', Balance=$" + balance + "}";
    }
}
