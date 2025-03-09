package Main;

import Bank.Bank;

public class CreditAccountLauncher extends AccountLauncher {

    public static void creditAccountInit() {

    }

    private static void creditPaymentProcess() {

    }

    private static void creditRecompenseProcess() {

    }

    protected static CreditAccount getLoggedAccount() {
        return (CreditAccount) AccountLauncher.getLoggedAccount();
    }
    
}
