package com.devmind.email.service.impl;

import com.devmind.email.EmailProperties;
import com.devmind.email.EmailProperties.ExternalApi;
import com.devmind.email.service.EmailMessage;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 04/01/18.
 */
public class ElasticMailSenderTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private EmailProperties properties;

    private MockWebServer server;
    private WebClient webClient;
    private ElasticMailSender elasticMailSender;

    @Before
    public void setUp(){
        ExternalApi api = new ExternalApi();
        api.setApikey("mykey");
        given(properties.getElasticmail()).willReturn(api);

        this.server = new MockWebServer();
        this.webClient = Mockito.spy(WebClient.create(this.server.url("/").toString()));
        elasticMailSender = new ElasticMailSender(properties, webClient);
    }

    @Test
    public void send() {
        prepareResponse(response -> response
                .setHeader("Content-Type", "application/json")
                .setBody("{ \"success\" : true }"));

        elasticMailSender.send(new EmailMessage(
                "gui.ehret@gmail.com",
                "Email test",
                "<h1>Hi Guillaume</h1><p>Waow... you are able to send an email</p>")
        );

        verify(webClient, atLeastOnce()).post();
    }

    @Test
    public void sendWithError() {
        prepareResponse(response -> response
                .setHeader("Content-Type", "application/json")
                .setBody("{ \"success\" : false, \"error\" : \"error expected\" }"));

        assertThatThrownBy(() -> elasticMailSender.send(new EmailMessage(
                "gui.ehret@gmail.com",
                "Email test",
                "<h1>Hi Guillaume</h1><p>Waow... you are able to send an email</p>")))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("error expected");
    }

    private void prepareResponse(Consumer<MockResponse> consumer) {
        MockResponse response = new MockResponse();
        consumer.accept(response);
        this.server.enqueue(response);
    }
}