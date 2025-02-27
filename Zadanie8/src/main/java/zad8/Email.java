package zad8;

import java.util.Objects;

public class Email {

    private String sender;
    private String recipient;
    private String subject;
    private String content;

    public Email(String sender, String recipient, String subject, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return sender.equals(email.sender) && recipient.equals(email.recipient) && subject.equals(email.subject) && content.equals(email.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, recipient, subject, content);
    }
}
