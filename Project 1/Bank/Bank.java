package Bank;

import Accounts.*;
import java.util.ArrayList;

public class Bank {

    private int ID;

    private String name, passcode;

    private double DEPOSITLIMIT, WITHDRAWLIMIT, CREDITLIMIT;

    private double processingFee;

    private ArrayList<Account> BANKACCOUNTS;

    

    public Bank(int ID, String name, String passcode) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
//        TODO: add stuff here!
//        this.DEPOSITLIMIT = // add limit
//        this.WITHDRAWLIMIT = // add limit
//        this.CREDITLIMIT = // add limit
//        this.processingFee = // add processingfee
        BANKACCOUNTS = new ArrayList<>();

    }

    public Bank(int ID, String name, String passcode, double DEPOSITLIMIT, double WITHDRAWLIMIT, double CREDITLIMIT, double processingFee) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.DEPOSITLIMIT = DEPOSITLIMIT;
        this.WITHDRAWLIMIT = WITHDRAWLIMIT;
        this.CREDITLIMIT = CREDITLIMIT;
        this.processingFee = processingFee;
        BANKACCOUNTS = new ArrayList<>();
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
        return String.format("Bank ID %s: %s", ID, name);
    }

}
