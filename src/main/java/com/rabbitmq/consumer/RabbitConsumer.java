package com.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitConsumer {

    @RabbitListener(queues = "MAC001.acknowledgeQueue")
    public void stringConsumer(String message){
        log.info("Received message: {}", message);
    }

}
