package Bank;

import java.util.Comparator;

public class BankCompactor implements Comparator<Bank> {
    @Override
    public int compare(Bank b1, Bank b2) {
        return b1.equals(b2) ? 0 : 1;
    }
    
}
