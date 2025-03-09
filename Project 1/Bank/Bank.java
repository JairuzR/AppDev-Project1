package Bank;

public class Bank {

    private int ID;

    private String name, passcode;

    private double DEPOSITLIMIT, WITHDRAWLIMIT, CREDITLIMIT;

    private double processingFee;

    private ArrayList<Account> BANKACCOUNTS;

    

    public Bank(int ID, String name, String passcode) {

    }

    public Bank(int ID, String name, String passcode, double DEPOSITLIMIT, double WITHDRAWLIMIT, double CREDITLIMIT, double processingFee) {

    }

    public void showAccounts(Class accountType) {

    }

    public void getBankAccount(Bank bank, String accountNum) {

    }

    public ArrayList<String> createNewAccount() {

    }

    public CreditAccount createNewCreditAccount() {

    }

    public SavingsAccount createNewSavingsAccount() {

    }

    public void addNewAccount(Account account) {

    }
    
    public static boolean accountExists(Bank bank, String accountNum) {
        
    }

    public String toString() {

    }

}
