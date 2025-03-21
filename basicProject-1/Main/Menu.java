package Main;

/**
 * Enumeration of menu options for the banking system.
 */
public enum Menu {

    MainMenu(new String[]{"Accounts Login", "Bank Login", "Create New Bank", "Create New Account", "Exit"}, 1),

    AccountLogin(new String[]{"Accounts Login", "Go Back"}, 2),

    BankLogin(new String[]{"Login", "Go Back"}, 3),

    BankMenu(new String[]{"Show Accounts", "New Accounts", "Log Out"}, 31),

    ShowAccounts(new String[]{"Credit Accounts", "Savings Accounts", "All Accounts", "Go Back"}, 32),

    AccountTypeSelection(new String[]{"Credit Account", "Savings Account"}, 33),

    CreditAccountMenu(new String[]{"Show Credits", "Pay", "Recompense", "Show Transactions", "Logout"}, 41),

    SavingsAccountMenu(new String[]{"Show Balance", "Deposit", "Withdraw", "Fund Transfer", "Show Transactions", "Logout"}, 51);

    public final String[] menuOptions;
    public final int menuIdx;

    private Menu(String[] options, int index) {
        this.menuOptions = options;
        this.menuIdx = index;
    }

    public static String[] getMenuOptions(int index) {
        for (Menu menu : Menu.values()) {
            if (menu.menuIdx == index) {
                return menu.menuOptions;
            }
        }
        return null;
    }
}
