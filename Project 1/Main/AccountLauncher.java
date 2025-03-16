package Main;

import Bank.Bank;
import Accounts.*;

public class AccountLauncher {
    
    private static Account loggedAccount;
    private static Bank assocBank;



    private static boolean isLoggedIn() {
        return loggedAccount != null;
    }

    public static void accountLogin() {
        assocBank = selectBank();

        if (assocBank != null) {
            Account account = checkCredentials();
            if (account != null) {
                setLogSession(account, assocBank);
                System.out.println("Logged in.");
            } else {
                System.out.println("invalid."); // Invalid message. Replace or moodify with a better one
            }
        } else {
            System.out.println("No bank selected.");
        }
    }

    private static Bank selectBank() {
        System.out.println("Select a bank: ");
        Bank[] banks = BankLauncher.getAvailableBanks();
        
        for (int i = 0; i < banks.length; i++) {
            System.out.println((i + 1) + ". " + banks[i].getName());
        }
        Main.setOption();
        int choice = Main.getOption();
        if (choice > 0 && choice <= banks.length) {
            return banks[choice -1];
        } else {
            System.out.println("Invalid.");
            return null;
        }
    }

    private static void setLogSession(Account account, Bank bank) {
        if (account != null && bank != null) {
            loggedAccount = account;
            assocBank = bank;
            System.out.println("Session established: " + loggedAccount);
        } else {
            System.out.println("Failed to establish session.");
        }
    }
    
    private static void destroyLogSession() {
        loggedAccount = null;
        assocBank = null;
        System.out.println("Session logged out.");
    }

    public static Account checkCredentials() {
        System.out.println("Enter username: ");
        Main.setOption();
        String username = String.valueOf(Main.getOption());

        System.out.println("Enter password: ");
        Main.setOption();
        String password = String.valueOf(Main.getOption());

        return assocBank.verifyAccount(username, password);
    }

    protected static Account getLoggedAccount() {
        return loggedAccount;
    }

}
