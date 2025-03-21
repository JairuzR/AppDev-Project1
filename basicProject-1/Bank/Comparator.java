package Bank;

public interface Comparator {

    /**
     * Compares two Bank objects and returns 0 if they match based on criteria,
     * or 1 otherwise.
     *
     * @param b1 First bank to compare
     * @param b2 Second bank to compare
     * @return 0 if the banks are considered equal, 1 otherwise
     */
    int compare(Bank b1, Bank b2);

}
