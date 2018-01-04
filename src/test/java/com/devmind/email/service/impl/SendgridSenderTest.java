package com.devmind.email.service.impl;

import com.devmind.email.service.EmailMessage;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 04/01/18.
 */
public class SendgridSenderTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private SendGrid sendGrid;

    @InjectMocks
    private SendgridSender sendgridSender;

    @Test
    public void send() throws Exception{

        sendgridSender.send(new EmailMessage(
                "gui.ehret@gmail.com",
                "Email test",
                "<h1>Hi Guillaume</h1><p>Waow... you are able to send an email</p>")
        );

        verify(sendGrid, Mockito.atLeastOnce()).api(any(Request.class));
    }
}