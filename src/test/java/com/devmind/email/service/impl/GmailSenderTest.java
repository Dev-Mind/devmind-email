package com.devmind.email.service.impl;

import com.devmind.email.service.EmailMessage;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 */
public class GmailSenderTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private GmailSender gmailSender;

    @Test
    public void send() {
        given(javaMailSender.createMimeMessage()).willReturn(mimeMessage);

        gmailSender.send(new EmailMessage(
                "gui.ehret@gmail.com",
                "Email test",
                "<h1>Hi Guillaume</h1><p>Waow... you are able to send an email</p>")
        );

        verify(javaMailSender, Mockito.atLeastOnce()).send(mimeMessage);
    }
}