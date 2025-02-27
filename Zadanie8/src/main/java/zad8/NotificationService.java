package zad8;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    private final MailService mailService;

    public NotificationService(MailService mailService) {
        this.mailService = mailService;
    }

    public void sendNotification(Sender sender, List<String> recipients, String subject, String content) throws EmailNotSentException {

        List<String> errorRecipients = new ArrayList<>();

        for (String recipient : recipients) {
            Email email = new Email(sender.getEmail(), recipient, "Powiadomienie: " + subject, "Powiadomienie od " + sender.getFirstName() + " " + sender.getLastName() + "\n" + content);

            try {
                mailService.sendEmail(email);
            } catch (Exception e) {
                errorRecipients.add(recipient);
            }
        }

        if(!errorRecipients.isEmpty()) {
            Email email = new Email(sender.getEmail(), sender.getEmail(), "Nieudane wysłanie wiadomości do:", errorRecipients.toString());

            try {
                mailService.sendEmail(email);
            } catch (Exception e) {
                throw new EmailNotSentException("Could not send an email notifying sender", errorRecipients);
            }
        }
    }
}

