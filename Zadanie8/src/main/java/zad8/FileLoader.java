package zad8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {

    public Sender loadSender(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String firstLine = br.readLine();
            if (firstLine != null) {
                String[] parts = firstLine.split("\\s+");
                if (parts.length == 3) {
                    return new Sender(parts[0].trim(), parts[1].trim(), parts[2].trim());
                } else {
                    throw new IllegalArgumentException("Invalid format for sender information.");
                }
            } else {
                throw new IllegalArgumentException("File is empty or sender information is missing.");
            }
        }
    }

    public List<String> loadRecipients(String filePath) throws IOException {
        List<String> recipients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                recipients.add(line.trim());
            }
        }
        return recipients;
    }
}
