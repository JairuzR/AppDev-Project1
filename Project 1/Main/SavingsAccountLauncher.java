package Main;

import Accounts.SavingsAccount;
import Bank.Bank;

public class SavingsAccountLauncher extends AccountLauncher {

    public static void SavingsAccountInit() {
        System.out.println("Initializing savings account...");
    }

    private static void depositProcess() {
        System.out.println("Processing deposit...");
    }

    private static void withdrawProcess() {
        System.out.println("Processing withdrawal...");
    }

    private static void fundTransferProcess() {
        System.out.println("Processing fund transfer...");
    }

    protected static SavingsAccount getLoggedAccount() {
        return (SavingsAccount) AccountLauncher.getLoggedAccount();
    }
    
}
