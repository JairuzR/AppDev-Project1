package Accounts;

public interface Withdrawal {

    /**
     * Withdraws an amount of money using a given medium.
     * @param amount Amount of money to be withdrawn.
     * @return true if the withdrawal is successful, false otherwise.
     */
    boolean withdrawal(double amount);

}
