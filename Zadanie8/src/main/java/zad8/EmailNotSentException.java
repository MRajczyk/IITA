package zad8;

import java.util.List;

public class EmailNotSentException extends Exception {
    private final List<String> recipientsEmails;
    private final String message;

    public EmailNotSentException(String message, List<String> recipients) {
        super(message);
        this.recipientsEmails = recipients;
        this.message = message;
    }

    public List<String> getRecipientsEmails() {
        return recipientsEmails;
    }

    public String getMessage() {
        return message;
    }
}