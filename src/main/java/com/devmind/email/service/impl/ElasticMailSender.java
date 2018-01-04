package com.devmind.email.service.impl;

import com.devmind.email.EmailProperties;
import com.devmind.email.service.EmailMessage;
import com.devmind.email.service.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 04/01/18.
 */
@Component
public class ElasticMailSender implements EmailSender {

    @Autowired
    private EmailProperties properties;
    private WebClient webClient;

    public ElasticMailSender() {
        webClient = WebClient.create(properties.getElasticmail().getHost());
    }

    public ElasticMailSender(EmailProperties properties, WebClient webClient) {
        this.properties = properties;
        this.webClient = webClient;
    }

    @Override
    public void send(EmailMessage email) {
        ElasticEmailResponseDTO response = webClient.post()
                                                    .uri(String.format("/%s/email/send", properties.getElasticmail().getVersion()))
                                                    .body(BodyInserters
                                                            .fromFormData("apikey", properties.getElasticmail().getApikey())
                                                            .with("from", "guillaume@dev-mind.fr")
                                                            .with("fromName", "DEv-Mind")
                                                            .with("to", email.getTo())
                                                            .with("subject", email.getSubject())
                                                            .with("isTransactional", "true")
                                                            .with("body", email.getContent())
                                                    )
                                                    .accept(MediaType.APPLICATION_JSON)
                                                    .retrieve()
                                                    .bodyToMono(ElasticEmailResponseDTO.class)
                                                    .block();

        if (response.getSuccess() == false) {
            throw new RuntimeException(response.getError());
        }
    }
}
