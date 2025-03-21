package Bank;

import Accounts.*;
import Main.Field;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;

public class Bank {

    private int ID;
    private String name;
    private String passcode;
    private double depositLimit;
    private double withdrawLimit;
    private double creditLimit;
    private double processingFee;
    private ArrayList<Account> bankAccounts;

    public Bank(int ID, String name, String passcode) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.depositLimit = 50000.0;
        this.withdrawLimit = 50000.0;
        this.creditLimit = 100000.0;
        this.processingFee = 10.0;
        this.bankAccounts = new ArrayList<Account>();
    }

    public Bank(int ID, String name, String passcode, double depositLimit, double withdrawLimit, double creditLimit, double processingFee) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.depositLimit = depositLimit;
        this.withdrawLimit = withdrawLimit;
        this.creditLimit = creditLimit;
        this.processingFee = processingFee;
        this.bankAccounts = new ArrayList<Account>();
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPasscode() {
        return passcode;
    }

    public double getDepositLimit() {
        return depositLimit;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public void showAccounts(Class<? extends Account> accountType) {
        for (int i = 0; i < bankAccounts.size(); i++) {
            if (accountType.isInstance(bankAccounts.get(i))) {
                System.out.println("[" + (i + 1) + "]. " + bankAccounts.get(i));
            }
        }
    }

    public Account getBankAccount(Bank bank, String accountNumber) {
        for (int i = 0; i < bank.bankAccounts.size(); i++) {
            Account acc = bank.bankAccounts.get(i);
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        return null;
    }

    public ArrayList<Field<String, String>> createNewAccount() {
        Field<String, String> accountNumber = new Field<String, String>("Account Number", String.class, " ", new Field.StringFieldValidator());
        Field<String, String> pin = new Field<String, String>("PIN", String.class, " ", new Field.StringFieldValidator());
        Field<String, String> firstName = new Field<String, String>("First Name", String.class, " ", new Field.StringFieldValidator());
        Field<String, String> lastName = new Field<String, String>("Last Name", String.class, " ", new Field.StringFieldValidator());
        Field<String, String> email = new Field<String, String>("Email", String.class, " ", new Field.StringFieldValidator());

        accountNumber.setFieldValue("Enter account number: ");
        pin.setFieldValue("Enter PIN: ", false);
        firstName.setFieldValue("Enter owner firstname: ", false);
        lastName.setFieldValue("Enter owner lastname: ", false);
        email.setFieldValue("Enter owner email: ", false);

        return new ArrayList<Field<String, String>>(Arrays.asList(accountNumber, pin, firstName, lastName, email));
    }

    public CreditAccount createNewCreditAccount() {
        ArrayList<Field<String, String>> accountData = createNewAccount();
        String accountNumber = accountData.get(0).getFieldValue();
        String pin = accountData.get(1).getFieldValue();
        String firstName = accountData.get(2).getFieldValue();
        String lastName = accountData.get(3).getFieldValue();
        String email = accountData.get(4).getFieldValue();

        if (accountExists(this, accountNumber)) {
            System.out.println("Account already exists!");
            return null;
        }

        CreditAccount newAccount = new CreditAccount(this, accountNumber, pin, firstName, lastName, email);
        addNewAccount(newAccount);
        return newAccount;
    }

    public SavingsAccount createNewSavingsAccount() {
        ArrayList<Field<String, String>> accountData = createNewAccount();
        String accountNumber = accountData.get(0).getFieldValue();
        String pin = accountData.get(1).getFieldValue();
        String firstName = accountData.get(2).getFieldValue();
        String lastName = accountData.get(3).getFieldValue();
        String email = accountData.get(4).getFieldValue();

        Field<Double, Double> balanceField = new Field<Double, Double>("Balance", Double.class, 0.0, new Field.DoubleFieldValidator());
        balanceField.setFieldValue("Enter account balance: ");
        double balance = balanceField.getFieldValue();

        if (accountExists(this, accountNumber)) {
            System.out.println("Account already exists!");
            return null;
        }

        SavingsAccount newAccount = new SavingsAccount(this, accountNumber, pin, firstName, lastName, email, balance);
        addNewAccount(newAccount);
        return newAccount;
    }

    public void addNewAccount(Account account) {
        if (!accountExists(this, account.getAccountNumber())) {
            bankAccounts.add(account);
        }
    }

    public static boolean accountExists(Bank bank, String accountNumber) {
        Account acc = bank.getBankAccount(bank, accountNumber);
        return acc != null;
    }

    public String toString() {
        return "Name: " + name + "\n";
    }

    public static class BankComparator implements Comparator<Bank> {
        public int compare(Bank b1, Bank b2) {
            return b1.equals(b2) ? 0 : 1;
        }
    }

    public static class BankCredentialsComparator implements Comparator<Bank> {
        public int compare(Bank b1, Bank b2) {
            if (b1.getID() == b2.getID() && b1.getPasscode().equals(b2.getPasscode())) {
                return 0;
            }
            return 1;
        }
    }

    public static class BankIdComparator implements Comparator<Bank> {
        public int compare(Bank b1, Bank b2) {
            return b1.getID() == b2.getID() ? 0 : 1;
        }
    }
}