package Persistence;

import Bank.Bank;
import java.io.IOException;
import java.util.List;

/**
 * An interface for persisting Bank data.
 * It defines methods to save and load a list of Bank objects.
 * Implementations of this interface can use any storage mechanism,
 * such as JSON files, databases, etc.
 */
public interface StorageService {

    /**
     * Saves the list of Bank objects to persistent storage.
     *
     * @param banks the list of banks to save
     * @throws IOException if an I/O error occurs during saving
     */
    void saveBanks(List<Bank> banks) throws IOException;

    /**
     * Loads the list of Bank objects from persistent storage.
     *
     * @return the list of banks loaded from storage
     * @throws IOException if an I/O error occurs during loading
     */
    List<Bank> loadBanks() throws IOException;
}
