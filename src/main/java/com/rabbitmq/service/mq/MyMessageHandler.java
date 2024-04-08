package com.rabbitmq.service.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyMessageHandler {
    public void handleMessage(String message) {
        log.info("Received message: " + message);
    }
}
