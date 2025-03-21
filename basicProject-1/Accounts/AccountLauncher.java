package Accounts;

import Bank.*;
import Main.*;

public class AccountLauncher {

    private static Account loggedAccount;
    private static Bank associatedBank;

    private static boolean isLoggedIn() {
        return loggedAccount != null;
    }

    public static void accountLogin() {
        destroyLogSession();
        associatedBank = null;

        int attempts = 3;
        while (associatedBank == null) {
            associatedBank = selectBank();
            if (associatedBank == null && attempts != 0) {
                System.out.println("Invalid bank selection. Please try again. Attempts: " + attempts);
                attempts--;
            } else {
                System.out.println("Too many attempts! Exiting Account Login...");
                return;
            }
        }

        Main.showMenuHeader("Account Login");
        String enteredAccountNumber = Main.prompt("Enter account number: ", true);
        String enteredPin = Main.prompt("Enter  PIN: ", true);

        loggedAccount = checkCredentials(enteredAccountNumber, enteredPin);

        if (loggedAccount != null) {
            System.out.println("Login successful.");
            setLogSession(loggedAccount);

            if (loggedAccount instanceof SavingsAccount) {
                SavingsAccountLauncher.savingsAccountInit();
            } else if (loggedAccount instanceof CreditAccount) {
                CreditAccountLauncher.creditAccountInit();
            }
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private static Bank selectBank() {
        BankLauncher.showBanksMenu();

        Field<String, String> bankNameField = new Field<String, String>("Bank Name", String.class, "", new Field.StringFieldValidator());
        bankNameField.setFieldValue("Enter bank name: ");
        String enteredName = bankNameField.getFieldValue();

        for (int i = 0; i < BankLauncher.banks.size(); i++) {
            Bank bank = BankLauncher.banks.get(i);
            if (bank.getName().equals(enteredName)) {
                System.out.println("Bank selected: " + enteredName);
                return bank;
            }
        }

        return null;
    }

    private static void setLogSession(Account account) {
        loggedAccount = account;
        System.out.println("Logged-in account set: " + account.getOwnerFullName());
    }

    private static void destroyLogSession() {
        if (isLoggedIn()) {
            loggedAccount = null;
        }
    }

    public static Account checkCredentials(String accountNumber, String pin) {
        Account account = BankLauncher.findAccount(accountNumber);
        if (account != null) {
            if (account.getPin().equals(pin)) {
                return account;
            }
        }
        return null;
    }

    protected static Account getLoggedAccount() {
        return loggedAccount;
    }
}
