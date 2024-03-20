package com.rabbitmq.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public String sendStringMessage(String message){
        rabbitTemplate.convertAndSend(exchange, stringRoutingKey, message);
        log.info("Message sent: {}", message);
        return message;
    }

}
