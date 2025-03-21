package Accounts;

import Bank.*;
import Main.*;

public class SavingsAccountLauncher extends AccountLauncher {

    public static void savingsAccountInit() {
        boolean isRunning = true;

        while (isRunning) {
            Main.showMenuHeader("Savings Account Main Menu");
            Main.showMenu(51);
            Main.setOption();

            int option = Main.getOption();

            if (option == 1) {
                double balance = getLoggedAccount().getAccountBalance();
                System.out.println("Current balance: $" + balance);
            } else if (option == 2) {
                handleDeposit();
            } else if (option == 3) {
                handleWithdrawal();
            } else if (option == 4) {
                handleFundTransfer();
            } else if (option == 5) {
                System.out.println("<-----Transactions----->");
                System.out.println(getLoggedAccount().getTransactionsInfo());
            } else if (option == 6) {
                isRunning = false;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void handleDeposit() {
        Field<Double, Double> amountField = new Field<Double, Double>("amount", Double.class, 0.0, new Field.DoubleFieldValidator());
        amountField.setFieldValue("Enter deposit amount: ");
        double amount = amountField.getFieldValue();

        boolean success = getLoggedAccount().cashDeposit(amount);
        if (success) {
            System.out.println("Deposit of $" + amount + " processed successfully.");
        } else {
            System.out.println("Deposit failed.");
        }
    }

    private static void handleWithdrawal() {
        Field<Double, Double> amountField = new Field<Double, Double>("amount", Double.class, 0.0, new Field.DoubleFieldValidator());
        amountField.setFieldValue("Enter withdrawal amount: ");
        double amount = amountField.getFieldValue();

        boolean success = getLoggedAccount().withdrawal(amount);
        if (success) {
            System.out.println("Withdrawal of $" + amount + " processed successfully.");
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    private static void handleFundTransfer() {
        Field<Integer, Integer> bankIdField = new Field<Integer, Integer>("Bank ID", Integer.class, -1, new Field.IntegerFieldValidator());
        Field<String, String> bankNameField = new Field<String, String>("Bank Name", String.class, "", new Field.StringFieldValidator());

        bankIdField.setFieldValue("Enter bank id: ");
        bankNameField.setFieldValue("Enter bank name: ");

        Bank selectedBank = null;
        for (int i = 0; i < BankLauncher.banks.size(); i++) {
            Bank bank = BankLauncher.banks.get(i);
            if (bank.getID() == bankIdField.getFieldValue() && bank.getName().equals(bankNameField.getFieldValue())) {
                selectedBank = bank;
                System.out.println("Bank selected: " + bank.getName());
            }
        }

        if (selectedBank == null) {
            System.out.println("Invalid bank id or name.");
            return;
        }

        Field<String, String> accountField = new Field<String, String>("Target Account Number", String.class, " ", new Field.StringFieldValidator());
        Field<Double, Double> amountField = new Field<Double, Double>("Amount", Double.class, 0.0, new Field.DoubleFieldValidator());

        accountField.setFieldValue("Enter target account number: ");
        amountField.setFieldValue("Enter transfer amount: ");

        Account recipient = selectedBank.getBankAccount(selectedBank, accountField.getFieldValue());

        if (recipient == getLoggedAccount()) {
            System.out.println("Fund transfer to the same account is not allowed.");
            return;
        }

        boolean success = false;
        try {
            if (getLoggedAccount().getBank().getID() == selectedBank.getID()) {
                success = getLoggedAccount().transfer(recipient, amountField.getFieldValue());
            } else {
                success = getLoggedAccount().transfer(selectedBank, recipient, amountField.getFieldValue());
            }
        } catch (IllegalAccountType e) {
            System.out.println("Transfer failed: Incompatible account types.");
        }

        if (success) {
            System.out.println("Fund transfer of $" + amountField.getFieldValue() + " processed successfully.");
        }
    }

    protected static SavingsAccount getLoggedAccount() {
        return (SavingsAccount) AccountLauncher.getLoggedAccount();
    }
}