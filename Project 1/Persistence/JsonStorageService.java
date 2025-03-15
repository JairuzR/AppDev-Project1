package Persistence;

import Bank.Bank;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of StorageService using JSON file-based persistence.
 * Uses the Jackson library to serialize and deserialize Bank objects.
 */
public class JsonStorageService implements StorageService {

    private final ObjectMapper objectMapper;
    private final File file;

    /**
     * Constructs a JsonStorageService with the specified filename.
     *
     * @param filename The JSON file to use for storing Bank data.
     */
    public JsonStorageService(String filename) {
        this.objectMapper = new ObjectMapper();
        this.file = new File(filename);
    }

    /**
     * Saves the list of Bank objects to a JSON file.
     *
     * @param banks The list of Bank objects to save.
     * @throws IOException if an I/O error occurs during writing.
     */
    @Override
    public void saveBanks(List<Bank> banks) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, banks);
    }

    /**
     * Loads the list of Bank objects from a JSON file.
     *
     * @return The list of Bank objects, or an empty list if the file does not exist.
     * @throws IOException if an I/O error occurs during reading.
     */
    @Override
    public List<Bank> loadBanks() throws IOException {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Bank.class);
        return objectMapper.readValue(file, listType);
    }
}
