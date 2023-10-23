package net.thumbtack.school.concurrent.fourteen;

import com.google.common.base.Objects;

public class Message {
    private String emailAddress;
    private String sender;
    private String subject;
    private String body;

    public Message(String emailAddress, String sender, String subject, String body) {
        this.emailAddress = emailAddress;
        this.sender = sender;
        this.subject = subject;
        this.body = body;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equal(getEmailAddress(), message.getEmailAddress()) && Objects.equal(getSender(), message.getSender()) && Objects.equal(getSubject(), message.getSubject()) && Objects.equal(getBody(), message.getBody());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEmailAddress(), getSender(), getSubject(), getBody());
    }

    @Override
    public String toString() {
        return "Message{" +
                "emailAddress='" + emailAddress + '\'' +
                ", sender='" + sender + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
