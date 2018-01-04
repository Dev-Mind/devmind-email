package com.devmind.email.service.impl;

import com.devmind.email.service.EmailMessage;
import com.devmind.email.service.EmailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 04/01/18.
 */
@Component
public class GmailSender implements EmailSender {

    private JavaMailSender javaMailSender;

    public GmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(EmailMessage email) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setFrom("guillaume@dev-mind.fr");
            message.setContent(email.getContent(), "text/html");
            javaMailSender.send(message);
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
