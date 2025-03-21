package Accounts;

/**
 * Class representing a bank transaction with type and description.
 */
public class Transaction {

    public enum Transactions {
        Deposit,
        Withdraw,
        FundTransfer,
        Payment,
        Recompense
    }

    public String accountNumber;
    public Transactions transactionType;
    public String description;

    public Transaction(String accountNumber, Transactions transactionType, String description) {
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.description = description;
    }
}
