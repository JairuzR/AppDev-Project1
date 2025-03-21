package Accounts;

import Main.*;
import Bank.BankLauncher;

public class CreditAccountLauncher extends AccountLauncher {

    public static void creditAccountInit() {
        boolean isRunning = true;

        while (isRunning) {
            Main.showMenuHeader("Credit Account Main Menu");
            Main.showMenu(41);
            Main.setOption();

            int selectedOption = Main.getOption();

            if (selectedOption == 1) {
                System.out.println(getLoggedAccount().getLoanStatement());
            } else if (selectedOption == 2) {
                handlePayment();
            } else if (selectedOption == 3) {
                handleRecompense();
            } else if (selectedOption == 4) {
                System.out.println("<-----Transactions----->");
                System.out.println(getLoggedAccount().getTransactionsInfo());
            } else if (selectedOption == 5) {
                isRunning = false;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void handlePayment() {
        Field<String, String> accountField = new Field<String, String>("Target Account", String.class, " ", new Field.StringFieldValidator());
        accountField.setFieldValue("Enter target account number: ");
        String inputAccountNumber = accountField.getFieldValue();

        Field<Double, Double> amountField = new Field<Double, Double>("Amount", Double.class, 0.0, new Field.DoubleFieldValidator());
        amountField.setFieldValue("Enter payment amount: ");
        double paymentAmount = amountField.getFieldValue();

        Account recipient = BankLauncher.findAccount(inputAccountNumber);

        if (recipient != null) {
            try {
                boolean success = getLoggedAccount().pay(recipient, paymentAmount);
                if (success) {
                    System.out.println("Payment of $" + paymentAmount + " processed successfully.");
                }
            } catch (IllegalAccountType error) {
                System.out.println("Error: Illegal account type encountered while processing payment.");
            }
        } else {
            System.out.println("This account doesn't exist.");
        }
    }

    private static void handleRecompense() {
        Field<Double, Double> recompenseField = new Field<Double, Double>("Amount", Double.class, 0.0, new Field.DoubleFieldValidator());
        recompenseField.setFieldValue("Enter amount: ");
        double amount = recompenseField.getFieldValue();

        boolean success = getLoggedAccount().recompense(amount);
        if (success) {
            System.out.println("Credit recompense of $" + amount + " processed successfully.");
        }
    }

    protected static CreditAccount getLoggedAccount() {
        return (CreditAccount) AccountLauncher.getLoggedAccount();
    }
}
