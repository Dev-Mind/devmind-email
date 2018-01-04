package com.devmind.email.service;

/**
 * An email sender is able to send an HTML message via email to a consignee
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 */
public interface EmailSender {
    void send(EmailMessage email);
}
