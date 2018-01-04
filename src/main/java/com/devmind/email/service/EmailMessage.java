package com.devmind.email.service;

/**
 * DTO used to send an email to a consignee {@link #to}.
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 */
public class EmailMessage {
    private final String to;
    private final String subject;
    private final String content;

    public EmailMessage(String to, String subject, String content) {
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }
}
