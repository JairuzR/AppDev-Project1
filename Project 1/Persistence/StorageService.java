package Persistence;

import Bank.Bank;
import java.io.IOException;
import java.util.List;

/**
 * A storage service interface to handle persistence operations.
 * This interface abstracts the JSON file operations so that
 * your core application can work with a generic storage service.
 */
public interface StorageService {
    
    /**
     * Saves the list of Bank objects to persistent storage.
     * 
     * @param banks The list of banks to save.
     * @throws IOException if an I/O error occurs during saving.
     */
    void saveBanks(List<Bank> banks) throws IOException;

    /**
     * Loads the list of Bank objects from persistent storage.
     * 
     * @return The list of banks.
     * @throws IOException if an I/O error occurs during loading.
     */
    List<Bank> loadBanks() throws IOException;
}
