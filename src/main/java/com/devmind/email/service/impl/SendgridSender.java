package com.devmind.email.service.impl;

import com.devmind.email.EmailProperties;
import com.devmind.email.service.EmailMessage;
import com.devmind.email.service.EmailSender;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 04/01/18.
 */
@Component
public class SendgridSender implements EmailSender {

    private SendGrid sendGrid;

    public SendgridSender(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    @Override
    public void send(EmailMessage email) {
        Mail mail = new Mail(
                new Email("guillaume@dev-mind.fr", "Dev-Mind"),
                email.getSubject(),
                new Email(email.getTo()),
                new Content("text/html", email.getContent()));

        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
