package com.rabbitmq.service.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@Slf4j
public class MyMessageHandler {

    public void handleMessage(String message) {
        log.info("Received message: " + message);
    }

    public void fileConsumer(String encryptedFile){
        byte[] fileContent = Base64.getDecoder().decode(encryptedFile);
        log.info("Received file: {}", fileContent);
    }
}
