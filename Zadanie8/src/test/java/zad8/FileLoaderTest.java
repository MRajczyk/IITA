package zad8;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileLoaderTest {

    @Test
    void testLoadSender() throws IOException {
        assertSender("target/test-classes/test1.txt", "Jan", "Kowalski", "jan.kowalski@gmail.com");
        assertSender("target/test-classes/test2.txt", "Adam", "Nowak", "adam.nowak@gmail.com");
        assertSender("target/test-classes/test3.txt", "Robert", "Lewandowski", "robercik@gmail.com");
    }

    @Test
    void testLoadRecipients() throws IOException {
        assertRecipients("target/test-classes/test1.txt", List.of("1@gmail.com", "2@gmail.com", "3@gmail.com"));
        assertRecipients("target/test-classes/test2.txt", List.of("a@gmail.com", "b@gmail.com", "c@gmail.com", "d@gmail.com"));
        assertRecipients("target/test-classes/test3.txt", List.of("1@gmail.com", "2@gmail.com"));
    }

    private void assertSender(String filePath, String expectedFirstName, String expectedLastName, String expectedEmail) throws IOException {
        FileLoader fileLoader = new FileLoader();
        Sender sender = fileLoader.loadSender(filePath);

        assertEquals(expectedFirstName, sender.getFirstName());
        assertEquals(expectedLastName, sender.getLastName());
        assertEquals(expectedEmail, sender.getEmail());
    }

    private void assertRecipients(String filePath, List<String> expectedRecipients) throws IOException {
        FileLoader fileLoader = new FileLoader();
        List<String> recipients = fileLoader.loadRecipients(filePath);

        assertEquals(expectedRecipients.size(), recipients.size());
        for (int i = 0; i < expectedRecipients.size(); i++) {
            assertEquals(expectedRecipients.get(i), recipients.get(i));
        }
    }
}
