package Accounts;

public interface Deposit {

    /**
     * Deposit an amount of money to some given account.
     * @param amount Amount to be deposited.
     * @return true if the transaction is successful, false otherwise.
     */
    boolean cashDeposit(double amount);

}
