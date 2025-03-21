package Main;

import Bank.*;
import Accounts.*;
import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);

    public static Field<Integer, Integer> option = new Field<Integer, Integer>("Option",
            Integer.class, -1, new Field.IntegerFieldValidator());

    public static void main(String[] args) {
        while (true) {
            showMenuHeader("Main Menu");
            showMenu(1);
            setOption();

            int selectedOption = getOption();

            if (selectedOption == 1) {
                showMenuHeader("Account Login Menu");
                showMenu(2, 1);
                setOption();

                if (getOption() == 1) {
                    AccountLauncher.accountLogin();
                } else if (getOption() == 2) {
                    System.out.println("Exiting Account Login Menu");
                }

            } else if (selectedOption == 2) {
                showMenuHeader("Bank Login Menu");
                showMenu(3, 1);
                setOption();

                if (getOption() == 1) {
                    BankLauncher.bankLogin();
                } else if (getOption() == 2) {
                    System.out.println("Exiting Bank Login Menu");
                }

            } else if (selectedOption == 3) {
                BankLauncher.createNewBank();

            } else if (selectedOption == 4) { // New: Create New Account
                showMenuHeader("Create New Account");

                if (!BankLauncher.isLogged()) { // Ensure a bank is selected first
                    System.out.println("Please select a bank before creating an account.");
                    BankLauncher.bankLogin(); // Prompt bank login
                }

                BankLauncher.newAccounts(); // Call the account creation function

            } else if (selectedOption == 5) {
                System.out.println("Exiting. Thank you for banking!");
                break;
            } else {
                System.out.println("Invalid option!");
            }
        }
    }

    public static void showMenu(int menuIdx) {
        showMenu(menuIdx, 1);
    }

    public static void showMenu(int menuIdx, int inlineTexts) {
        String[] menu = Menu.getMenuOptions(menuIdx);
        if (menu == null) {
            System.out.println("Invalid menu index given!");
        } else {
            String format = "[" + "%d" + "] %-20s";
            int count = 0;
            for (int i = 0; i < menu.length; i++) {
                count++;
                System.out.printf(format, count, menu[i]);
                if (count % inlineTexts == 0) {
                    System.out.println();
                }
            }
        }
    }

    public static String prompt(String phrase, boolean inlineInput) {
        System.out.print(phrase);
        if (inlineInput) {
            String value = input.next();
            input.nextLine();
            return value;
        }
        return input.nextLine();
    }

    public static void setOption() throws NumberFormatException {
        option.setFieldValue("Select an option: ");
    }

    public static int getOption() {
        return option.getFieldValue();
    }

    public static void showMenuHeader(String menuTitle) {
        System.out.println("<---- " + menuTitle + " ----->");
    }
}
