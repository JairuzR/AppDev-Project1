package Persistence;

import Bank.Bank;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple JSON storage service that manually serializes and deserializes Bank objects.
 * This implementation assumes a fixed structure for Bank objects and does not handle nested objects like accounts.
 */
public class JsonStorageService implements StorageService {

    private final File file;

    public JsonStorageService(String filename) {
        this.file = new File(filename);
    }

    @Override
    public void saveBanks(List<Bank> banks) throws IOException {
        // Manually build a JSON array string for the list of banks.
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
        
        // Write JSON string to file.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(sb.toString());
        }
    }

    @Override
    public List<Bank> loadBanks() throws IOException {
        List<Bank> banks = new ArrayList<>();
        if (!file.exists()) {
            return banks;
        }
        
        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        }
        String json = jsonBuilder.toString().trim();
        
        // Basic check for a JSON array.
        if (!json.startsWith("[") || !json.endsWith("]")) {
            throw new IOException("Invalid JSON format");
        }
        json = json.substring(1, json.length() - 1).trim(); // Remove surrounding brackets
        
        if (json.isEmpty()) {
            return banks;
        }
        
        // Split entries. This approach assumes each bank entry ends with a "}" followed by an optional comma.
        String[] bankEntries = json.split("},");
        for (String entry : bankEntries) {
            entry = entry.trim();
            if (!entry.endsWith("}")) {
                entry = entry + "}";
            }
            int id = extractInt(entry, "\"ID\":", ",");
            String name = extractString(entry, "\"name\":", ",");
            String passcode = extractString(entry, "\"passcode\":", ",");
            double depositLimit = extractDouble(entry, "\"depositLimit\":", ",");
            double withdrawLimit = extractDouble(entry, "\"withdrawLimit\":", ",");
            double creditLimit = extractDouble(entry, "\"creditLimit\":", ",");
            double processingFee = extractDouble(entry, "\"processingFee\":", null);
            
            // Create a new Bank object using a matching constructor.
            Bank bank = new Bank(id, name, passcode, depositLimit, withdrawLimit, creditLimit, processingFee);
            banks.add(bank);
        }
        return banks;
    }
    
    // Helper method to extract an int from a JSON snippet.
    private int extractInt(String entry, String key, String delimiter) {
        int idx = entry.indexOf(key);
        if (idx < 0) return 0;
        idx += key.length();
        int end = (delimiter != null) ? entry.indexOf(delimiter, idx) : entry.length();
        String value = entry.substring(idx, end).trim();
        return Integer.parseInt(value);
    }
    
    // Helper method to extract a double from a JSON snippet.
    private double extractDouble(String entry, String key, String delimiter) {
        int idx = entry.indexOf(key);
        if (idx < 0) return 0.0;
        idx += key.length();
        int end = (delimiter != null) ? entry.indexOf(delimiter, idx) : entry.length();
        String value = entry.substring(idx, end).trim();
        return Double.parseDouble(value);
    }
    
    // Helper method to extract a String from a JSON snippet.
    private String extractString(String entry, String key, String delimiter) {
        int idx = entry.indexOf(key);
        if (idx < 0) return "";
        idx += key.length();
        // Find first quote after key.
        int startQuote = entry.indexOf("\"", idx);
        int endQuote = entry.indexOf("\"", startQuote + 1);
        if (startQuote < 0 || endQuote < 0) return "";
        return entry.substring(startQuote + 1, endQuote);
    }
    
    // Minimal method to escape special JSON characters (currently only escapes double quotes).
    private String escapeJson(String text) {
        return text.replace("\"", "\\\"");
    }
}
