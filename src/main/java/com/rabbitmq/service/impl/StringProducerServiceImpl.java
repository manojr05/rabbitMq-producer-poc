package com.rabbitmq.service.impl;

import com.rabbitmq.service.StringProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StringProducerServiceImpl implements StringProducerService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendStringMessage(String exchange, String message, String routingKey) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
