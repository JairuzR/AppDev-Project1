package Persistence;

import Bank.Bank;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple JSON storage service that manually serializes and deserializes a list of Bank objects.
 * This implementation assumes a fixed structure for Bank objects and does not handle nested objects.
 */
public class JsonStorageService implements StorageService {

    private final File file;

    /**
     * Constructs a ManualJsonStorageService using the given filename.
     * @param filename The name (and path) of the JSON file.
     */
    public JsonStorageService(String filename) {
        this.file = new File(filename);
    }

    /**
     * Saves a list of Bank objects to the JSON file.
     * @param banks The list of Bank objects to save.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void saveBanks(List<Bank> banks) throws IOException {
        String json = buildJsonFromBanks(banks);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(json);
        }
    }

    /**
     * Loads a list of Bank objects from the JSON file.
     * @return The list of Bank objects; returns an empty list if file doesn't exist.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public List<Bank> loadBanks() throws IOException {
        List<Bank> banks = new ArrayList<>();
        if (!file.exists()) {
            return banks;
        }
        
        // Read entire file into a string.
        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        }
        
        String json = jsonBuilder.toString().trim();
        
        // Validate that the string is a JSON array.
        if (!json.startsWith("[") || !json.endsWith("]")) {
            throw new IOException("Invalid JSON format");
        }
        // Remove the outer square brackets.
        json = json.substring(1, json.length() - 1).trim();
        if (json.isEmpty()) {
            return banks;
        }
        
        // Split the JSON string into individual Bank JSON objects.
        String[] bankEntries = json.split("},");
        for (String entry : bankEntries) {
            entry = entry.trim();
            if (!entry.endsWith("}")) {
                entry = entry + "}";
            }
            Bank bank = parseBankFromJson(entry);
            banks.add(bank);
        }
        return banks;
    }
    
    /**
     * Builds a JSON-formatted string representing the list of Bank objects.
     * @param banks List of Bank objects.
     * @return A JSON-formatted string.
     */
    private String buildJsonFromBanks(List<Bank> banks) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < banks.size(); i++) {
            Bank bank = banks.get(i);
            sb.append("  {\n");
            sb.append("    \"ID\": ").append(bank.getID()).append(",\n");
            sb.append("    \"name\": \"").append(escapeJson(bank.getBankName())).append("\",\n");
            sb.append("    \"passcode\": \"").append(escapeJson(bank.getPasscode())).append("\",\n");
            sb.append("    \"depositLimit\": ").append(bank.getDepositLimit()).append(",\n");
            sb.append("    \"withdrawLimit\": ").append(bank.getWithdrawLimit()).append(",\n");
            sb.append("    \"creditLimit\": ").append(bank.getCreditLimit()).append(",\n");
            sb.append("    \"processingFee\": ").append(bank.getProcessingFee()).append("\n");
            sb.append("  }");
            if (i < banks.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * Parses a single JSON object string into a Bank object.
     * @param jsonEntry A JSON string representing one Bank.
     * @return A Bank object created from the JSON data.
     */
    private Bank parseBankFromJson(String jsonEntry) {
        int id = extractInt(jsonEntry, "\"ID\":", ",");
        String name = extractString(jsonEntry, "\"name\":", ",");
        String passcode = extractString(jsonEntry, "\"passcode\":", ",");
        double depositLimit = extractDouble(jsonEntry, "\"depositLimit\":", ",");
        double withdrawLimit = extractDouble(jsonEntry, "\"withdrawLimit\":", ",");
        double creditLimit = extractDouble(jsonEntry, "\"creditLimit\":", ",");
        double processingFee = extractDouble(jsonEntry, "\"processingFee\":", null);
        
        return new Bank(id, name, passcode, depositLimit, withdrawLimit, creditLimit, processingFee);
    }
    
    /**
     * Extracts an integer value from a JSON snippet.
     * @param entry JSON snippet.
     * @param key The key to search for.
     * @param delimiter The delimiter marking the end of the value (optional).
     * @return The extracted integer.
     */
    private int extractInt(String entry, String key, String delimiter) {
        int idx = entry.indexOf(key);
        if (idx < 0) return 0;
        idx += key.length();
        int end = (delimiter != null) ? entry.indexOf(delimiter, idx) : entry.length();
        String value = entry.substring(idx, end).trim();
        return Integer.parseInt(value);
    }
    
    /**
     * Extracts a double value from a JSON snippet.
     * @param entry JSON snippet.
     * @param key The key to search for.
     * @param delimiter The delimiter marking the end of the value (optional).
     * @return The extracted double.
     */
    private double extractDouble(String entry, String key, String delimiter) {
        int idx = entry.indexOf(key);
        if (idx < 0) return 0.0;
        idx += key.length();
        int end = (delimiter != null) ? entry.indexOf(delimiter, idx) : entry.length();
        String value = entry.substring(idx, end).trim();
        return Double.parseDouble(value);
    }
    
    /**
     * Extracts a String value from a JSON snippet.
     * @param entry JSON snippet.
     * @param key The key to search for.
     * @param delimiter The delimiter marking the end of the value (optional).
     * @return The extracted String.
     */
    private String extractString(String entry, String key, String delimiter) {
        int idx = entry.indexOf(key);
        if (idx < 0) return "";
        idx += key.length();
        int startQuote = entry.indexOf("\"", idx);
        int endQuote = entry.indexOf("\"", startQuote + 1);
        if (startQuote < 0 || endQuote < 0) return "";
        return entry.substring(startQuote + 1, endQuote);
    }
    
    /**
     * Escapes special JSON characters in a string.
     * @param text The original text.
     * @return The text with special characters escaped.
     */
    private String escapeJson(String text) {
        return text.replace("\"", "\\\"");
    }
}
