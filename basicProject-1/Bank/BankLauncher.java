package Bank;

import Accounts.*;
import Main.*;
import java.util.ArrayList;
import java.util.Comparator;

public class BankLauncher {

    public static ArrayList<Bank> banks = new ArrayList<>();
    private static Bank loggedBank;

    public static boolean isLogged() {
        return loggedBank != null;
    }

    public static void bankInit() {
        while (isLogged()) {
            Main.showMenuHeader("Bank Menu");
            Main.showMenu(31);
            Main.setOption();

            int option = Main.getOption();
            if (option == 1) {
                showAccounts();
            } else if (option == 2) {
                newAccounts();
            } else if (option == 3) {
                logout();
            } else {
                System.out.println("Invalid Option. Please try again.");
            }
        }
    }

    private static void showAccounts() {
        if (isLogged()) {
            Main.showMenu(32);
            Main.setOption();

            int option = Main.getOption();
            if (option == 1) {
                loggedBank.showAccounts(CreditAccount.class);
            } else if (option == 2) {
                loggedBank.showAccounts(SavingsAccount.class);
            } else if (option == 3) {
                loggedBank.showAccounts(Account.class);
            } else if (option == 4) {
                return;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public static void newAccounts() {
        if (!isLogged()) {  // Ensure a bank is selected before creating an account
            System.out.println("No bank is currently logged in.");
            showBanksMenu();
            System.out.println("Please select a bank to create an account.");
            bankLogin();  // Prompt user to log in to a bank first
        }

        Main.showMenu(33); // Account Type Selection
        Main.setOption();

        int option = Main.getOption();
        if (option == 1) {  // Create Credit Account
            CreditAccount creditAccount = loggedBank.createNewCreditAccount();
            if (creditAccount != null) {
                System.out.println("Credit account with account number " + creditAccount.getAccountNumber() + " was created successfully!");
            }
        } else if (option == 2) {  // Create Savings Account
            SavingsAccount savingsAccount = loggedBank.createNewSavingsAccount();
            if (savingsAccount != null) {
                System.out.println("Savings account with account number " + savingsAccount.getAccountNumber() + " was created successfully!");
            }
        } else {
            System.out.println("Invalid input. Please try again.");
        }
    }

    public static void bankLogin() {
        Field<String, String> nameField = new Field<>("Bank Name", String.class, " ", new Field.StringFieldValidator());
        Field<String, String> passcodeField = new Field<>("Bank Passcode", String.class, " ", new Field.StringFieldValidator());

        nameField.setFieldValue("Enter bank name: ", false);
        passcodeField.setFieldValue("Enter bank passcode: ");

        String name = nameField.getFieldValue();
        String passcode = passcodeField.getFieldValue();

        Bank matchedBank = null;
        int i = 0;
        while (matchedBank == null && i < bankSize()) {
            matchedBank = getBank(new Bank.BankCredentialsComparator(), new Bank(i, name, passcode));
            i++;
        }

        if (matchedBank != null) {
            setLogSession(matchedBank);
            bankInit();
        } else {
            System.out.println("Bank not found. Either invalid bank ID or passcode. Please try again.");
        }
    }

    private static void setLogSession(Bank bank) {
        loggedBank = bank;
    }

    private static void logout() {
        loggedBank = null;
    }

    public static void createNewBank() {
        Field<String, String> nameField = new Field<>("Name", String.class, "", new Field.StringFieldValidator());
        Field<String, Integer> passcodeField = new Field<>("Passcode", String.class, 5, new Field.StringFieldLengthValidator());
        Field<Double, Double> depositLimitField = new Field<>("Deposit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<Double, Double> withdrawLimitField = new Field<>("Withdraw Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<Double, Double> creditLimitField = new Field<>("Credit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        Field<Double, Double> processingFeeField = new Field<>("Processing Fee", Double.class, 0.0, new Field.DoubleFieldValidator());

        try {
            nameField.setFieldValue("Bank Name: ", false);
            passcodeField.setFieldValue("Bank Passcode: ");
            depositLimitField.setFieldValue("Set Deposit Limit by default? (Press 0 for default): ");
            withdrawLimitField.setFieldValue("Set Withdraw Limit by default? (Press 0 for default): ");
            creditLimitField.setFieldValue("Set Credit Limit by default? (Press 0 for default): ");
            processingFeeField.setFieldValue("Set Processing Fee by default? (Press 0 for default): ");
        } catch (NumberFormatException e) {
            System.out.println("Invalid! Please enter a valid number.");
            return;
        }

        String name = nameField.getFieldValue();
        String passcode = passcodeField.getFieldValue();
        double depositLimit = depositLimitField.getFieldValue();
        double withdrawLimit = withdrawLimitField.getFieldValue();
        double creditLimit = creditLimitField.getFieldValue();
        double processingFee = processingFeeField.getFieldValue();

        Bank newBank;
        if (depositLimit == 0.0 && withdrawLimit == 0.0 && creditLimit == 0.0 && processingFee == 0.0) {
            newBank = new Bank(bankSize(), name, passcode);
        } else {
            newBank = new Bank(bankSize(), name, passcode, depositLimit, withdrawLimit, creditLimit, processingFee);
        }

        addBank(newBank);
    }

    public static void showBanksMenu() {
        System.out.println("List of Registered Banks: ");
        for (int i = 0; i < bankSize(); i++) {
            Bank bank = banks.get(i);
            System.out.println("[" + (i + 1) + "]. Bank ID No." + bank.getID() + ": " + bank.getName());
        }
    }

    private static void addBank(Bank bank) {
        if (getBank(new Bank.BankIdComparator(), bank) != null) {
            System.out.println("A bank with this ID already exists.");
            return;
        }
        banks.add(bank);
        System.out.println("Bank added successfully.");
    }

    public static Bank getBank(Comparator<Bank> comparator, Bank bank) {
        for (Bank current : banks) {
            if (comparator.compare(bank, current) == 0) {
                return current;
            }
        }
        return null;
    }

    public static Account findAccount(String accountNumber) {
        for (Bank bank : banks) {
            if (Bank.accountExists(bank, accountNumber)) {
                return bank.getBankAccount(bank, accountNumber);
            }
        }
        return null;
    }

    public static int bankSize() {
        return banks.size();
    }
}
