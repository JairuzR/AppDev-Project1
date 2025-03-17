package Accounts;

/**
 * Class containing Transaction enums.
 */
public class Transaction {

    public enum Transactions {
        Deposit,
        Withdraw,
        FundTransfer,
        Payment,
        Recompense
    }

    /**
     * Account number that triggered this transaction.
     */
    public String accountNumber;
    /**
     * Type of transcation that was triggered.
     */
    public Transactions transactionType;
    /**
     * Description of the transaction.
     */
    public String description;
    
    /**
     * Constructs a transaction record.
     *
     * @param accountNumber    The account involved in the transaction.
     * @param transactionType  The type of transaction performed.
     * @param description      A brief description of the transaction.
     */
    public Transaction(String accountNumber, Transactions transactionType, String description) {
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.description = description;
    }

    /**
     * Retrieves transaction details as a formatted string.
     *
     * @return A string representation of the transaction.
     */
    @Override
    public String toString() {
        return "Transaction: " + transactionType + " | Account: " + accountNumber + " | " + description;
    }
}