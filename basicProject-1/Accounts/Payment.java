package Accounts;

public interface Payment {

    /**
     * Pay a certain amount of money into a given account object.
     * This is different from Fund Transfer as paying does not have any sort of
     * processing fee.
     *
     * @param account Target account to pay money into.
     * @param amount Amount to be paid.
     * @return true if payment is successful, false otherwise.
     * @throws IllegalAccountType if the account type is not allowed for payment.
     */
    boolean pay(Account account, double amount) throws IllegalAccountType;

}
