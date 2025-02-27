package zad8;

import java.util.Objects;

public class Sender {
    private String firstName;
    private String lastName;
    private String email;

    public Sender(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sender sender = (Sender) o;
        return Objects.equals(firstName, sender.firstName) && Objects.equals(lastName, sender.lastName) && Objects.equals(email, sender.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }
}
