package zad8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationServiceTest {

    private MailService mailService;
    private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        mailService = mock(MailService.class);
        notificationService = new NotificationService(mailService);
    }

    @Test
    void testSendNotificationToMultipleRecipients() {
        Sender sender1 = new Sender("Jan", "Kowalski", "jan.kowalski@example.com");

        performTestWithRecipients(2, sender1);
        performTestWithRecipients(10, sender1);
        performTestWithRecipients(17, sender1);
    }


    private void performTestWithRecipients(int numberOfRecipients, Sender sender) {
        try {
            String subject = "Przypomnienie o spotkaniu";
            String content = "Proszę pamiętać o spotkaniu w przyszły piątek.";

            List<String> recipients = new ArrayList<>();
            for (int i = 1; i <= numberOfRecipients; i++) {
                recipients.add(i + "@example.com");
            }

            sendNotification(sender, recipients);

            ArgumentCaptor<Email> emailCaptor = ArgumentCaptor.forClass(Email.class);
            verify(mailService, times(numberOfRecipients)).sendEmail(emailCaptor.capture());

            List<Email> capturedEmails = emailCaptor.getAllValues();

            for (int i = 0; i < numberOfRecipients; i++) {
                Email email = capturedEmails.get(i);
                assertEquals(sender.getEmail(), email.getSender());
                assertEquals(recipients.get(i), email.getRecipient());
                assertEquals("Powiadomienie: " + subject, email.getSubject());
                assertEquals("Powiadomienie od Jan Kowalski\n" + content, email.getContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reset(mailService);
        }
    }

    @Test
    public void testIntegrationWithNotify() {
        performTest(3, "target/test-classes/test1.txt");
        performTest(4, "target/test-classes/test2.txt");
        performTest(2, "target/test-classes/test3.txt");

        List<String> emails = new ArrayList<>();

        emails.add("1@gmail.com");
        performFailedEmailTest(1,"target/test-classes/failedSendTest4.txt", emails);
        emails.add("2@gmail.com");
        performFailedEmailTest(2,"target/test-classes/failedSendTest5.txt", emails);
        emails.add("3@gmail.com");
        emails.add("4@gmail.com");
        performFailedEmailTest(4,"target/test-classes/failedSendTest6.txt", emails);

        emails.clear();
        emails.add("1@gmail.com");
        emails.add("3@gmail.com");

        performFailedEmailTest(4, "target/test-classes/failedSendTest6.txt", emails);

        emails.clear();
        emails.add("1@gmail.com");
        performFailedEmailAndFailedSendToSenderTest("target/test-classes/failedSendTestAndSendToSender.txt", emails);

        emails.add("2@gmail.com");
        performFailedEmailAndFailedSendToSenderTest("target/test-classes/failedSendTestAndSendToSender2.txt", emails);
    }

    private void performTest(int numberOfRecipients, String filePath) {
        try {
            FileLoader fileLoader = new FileLoader();
            Sender sender = fileLoader.loadSender(filePath);
            List<String> recipients = fileLoader.loadRecipients(filePath);

            String subject = "Przypomnienie o spotkaniu";
            String content = "Proszę pamiętać o spotkaniu w przyszły piątek.";
            sendNotification(sender, recipients);

            ArgumentCaptor<Email> emailCaptor = ArgumentCaptor.forClass(Email.class);
            verify(mailService, times(numberOfRecipients)).sendEmail(emailCaptor.capture());

            List<Email> capturedEmails = emailCaptor.getAllValues();

            for (int i = 0; i < numberOfRecipients; i++) {
                Email email = capturedEmails.get(i);
                assertEquals(sender.getEmail(), email.getSender());
                assertEquals(recipients.get(i), email.getRecipient());
                assertEquals("Powiadomienie: " + subject, email.getSubject());
                assertEquals("Powiadomienie od " + sender.getFirstName() + " " + sender.getLastName() + "\n" + content, email.getContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reset(mailService);
        }
    }

    private void performFailedEmailTest(int numberOfRecipients, String filePath, List<String> failedEmail) {
        try {
            FileLoader fileLoader = new FileLoader();
            Sender sender = fileLoader.loadSender(filePath);
            List<String> recipients = fileLoader.loadRecipients(filePath);

            setupFailedEmails(sender, failedEmail);
            sendNotification(sender, recipients);

            verifyEmails(sender, recipients, numberOfRecipients, failedEmail);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reset(mailService);
        }
    }

    private void setupFailedEmails(Sender sender, List<String> failedEmail) throws Exception {
        String subject = "Przypomnienie o spotkaniu";
        String content = "Proszę pamiętać o spotkaniu w przyszły piątek.";

        for (String stringEmail : failedEmail) {
            Email email = new Email(sender.getEmail(), stringEmail, "Powiadomienie: " + subject, "Powiadomienie od Jan Kowalski\n" + content);
            doThrow(new Exception("Test Exception")).when(mailService).sendEmail(email);
        }
    }

    private void sendNotification(Sender sender, List<String> recipients) throws EmailNotSentException {
        String subject = "Przypomnienie o spotkaniu";
        String content = "Proszę pamiętać o spotkaniu w przyszły piątek.";

        notificationService.sendNotification(sender, recipients, subject, content);
    }

    private void verifyEmails(Sender sender, List<String> recipients, int numberOfRecipients, List<String> failedEmail) throws Exception {
        ArgumentCaptor<Email> emailCaptor = ArgumentCaptor.forClass(Email.class);
        verify(mailService, times(numberOfRecipients + 1)).sendEmail(emailCaptor.capture());

        List<Email> capturedEmails = emailCaptor.getAllValues();
        String subject = "Przypomnienie o spotkaniu";
        String content = "Proszę pamiętać o spotkaniu w przyszły piątek.";

        for (int i = 0; i < numberOfRecipients; i++) {
            Email email = capturedEmails.get(i);
            assertEquals(sender.getEmail(), email.getSender());
            assertEquals(recipients.get(i), email.getRecipient());
            assertEquals("Powiadomienie: " + subject, email.getSubject());
            assertEquals("Powiadomienie od " + sender.getFirstName() + " " + sender.getLastName() + "\n" + content, email.getContent());
        }

        Email email = capturedEmails.get(capturedEmails.size() - 1);
        assertEquals(sender.getEmail(), email.getSender());
        assertEquals(sender.getEmail(), email.getRecipient());
        assertEquals("Nieudane wysłanie wiadomości do:", email.getSubject());
        assertEquals(failedEmail.toString(), email.getContent());
    }

    private void performFailedEmailAndFailedSendToSenderTest(String filePath, List<String> failedEmail) {
        try {
            FileLoader fileLoader = new FileLoader();
            Sender sender = fileLoader.loadSender(filePath);
            List<String> recipients = fileLoader.loadRecipients(filePath);

            setupFailedEmails(sender, failedEmail);

            Email email = new Email(sender.getEmail(), sender.getEmail(), "Nieudane wysłanie wiadomości do:", failedEmail.toString());
            doThrow(new Exception("Test Exception")).when(mailService).sendEmail(email);
            EmailNotSentException emailNotSentException = assertThrows(EmailNotSentException.class, () -> notificationService.sendNotification(sender, recipients, "Przypomnienie o spotkaniu", "Proszę pamiętać o spotkaniu w przyszły piątek."));
            assertEquals(emailNotSentException.getMessage(), "Could not send an email notifying sender");
            assertEquals(emailNotSentException.getRecipientsEmails().toString(), failedEmail.toString());

            ArgumentCaptor<Email> emailCaptor = ArgumentCaptor.forClass(Email.class);
            verify(mailService, times(failedEmail.size() + 1)).sendEmail(emailCaptor.capture());

            List<Email> capturedEmails = emailCaptor.getAllValues();

            email = capturedEmails.get(capturedEmails.size() - 1);
            assertEquals(sender.getEmail(), email.getSender());
            assertEquals(sender.getEmail(), email.getRecipient());
            assertEquals("Nieudane wysłanie wiadomości do:", email.getSubject());
            assertEquals(failedEmail.toString(), email.getContent());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reset(mailService);
        }
    }
}
