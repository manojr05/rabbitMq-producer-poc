package com.rabbitmq.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StringProducerService {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.string.routingkey}")
    private String stringRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public String sendStringMessage(String message, String applicationName){
        rabbitTemplate.convertAndSend(exchange, stringRoutingKey, message, m -> {
            m.getMessageProperties().getHeaders().put(applicationName, applicationName);
            return m;
        });
        log.info("Message sent: {}", message);
        return message;
    }

}
